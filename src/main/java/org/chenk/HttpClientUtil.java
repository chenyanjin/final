package org.chenk;

import org.apache.http.*;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.GzipDecompressingEntity;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.params.HttpClientParams;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import javax.net.ssl.HttpsURLConnection;
import java.io.*;
import java.net.*;
import java.net.ProtocolException;
import java.util.Iterator;
import java.util.List;

/**
 * Created by chenk on 2014/4/12.
 */
public class HttpClientUtil {

    private static HttpClient httpClient = null;

    private static HttpClient getHttpClient() {
        if (httpClient == null) {
            final HttpParams httpParams = new BasicHttpParams();
            // timeout: get connections from connection pool
            ConnManagerParams.setTimeout(httpParams, 1000);
            // timeout: connect to the server
            HttpConnectionParams.setConnectionTimeout(httpParams, 10000);
            // timeout: transfer data from server
            HttpConnectionParams.setSoTimeout(httpParams, 40000);
            HttpProtocolParams.setUseExpectContinue(httpParams, true);
            HttpProtocolParams.setVersion(httpParams, HttpVersion.HTTP_1_1);
            HttpProtocolParams.setContentCharset(httpParams, HTTP.UTF_8);
            HttpClientParams.setRedirecting(httpParams, false);
            HttpConnectionParams.setTcpNoDelay(httpParams, true);
//            SchemeRegistry schemeRegistry = new SchemeRegistry();
//            schemeRegistry.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
//            schemeRegistry.register(new Scheme("https", SSLSocketFactory.getSocketFactory(), 443));
//            ClientConnectionManager manager = new ThreadSafeClientConnManager(httpParams, schemeRegistry);
            httpClient = new DefaultHttpClient( httpParams);
        }
        return httpClient;
    }

    public static JSONObject httpClientGet(String url) {
        HttpClient httpClient = getHttpClient();
        HttpGet httpGet = new HttpGet(url);
        String ip="171.8.1.13";
        httpGet.addHeader("CLIENT-IP",ip);
        httpGet.addHeader("X-FORWARDED-FOR",ip);
        httpGet.addHeader("HTTP_CLIENT_IP",ip);
        httpGet.addHeader("User-Agent","Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/42.0.2311.135 Safari/537.36");
        httpGet.addHeader("Pragma","no-cache");
        httpGet.addHeader("Accept-Encoding","gzip, deflate, sdch");
        httpGet.addHeader("Accept-Language","zh-CN,zh;q=0.8");
        httpGet.addHeader("Accept","application/json, text/javascript, */*; q=0.01");
        httpGet.addHeader("Referer","http://www.fzbm.com/qzydh/hqs.html");
        httpGet.addHeader("X-Requested-With","XMLHttpRequest");
        httpGet.addHeader("Connection","keep-alive");
        httpGet.addHeader("Cache-Control","no-cache");
        JSONObject responseJson = new JSONObject();
        HttpResponse httpResponse = null;
        try {
            for(Header h:httpGet.getAllHeaders()){
                System.out.println(h.toString());
            }
            httpResponse = httpClient.execute(httpGet);

            if (httpResponse.getStatusLine().getStatusCode() == 200) {
                responseJson.put("respCode","200");
                responseJson.put("respMsg","成功");
                HttpEntity en = httpResponse.getEntity();
                System.out.println("toString=" + EntityUtils.toString(new GzipDecompressingEntity(httpResponse.getEntity())));
                System.out.println("toByte="+ CodecUtils.hexString(EntityUtils.toByteArray(en)));
                responseJson.put("respData", EntityUtils.toString(en));
            }else{
                responseJson.put("respCode",httpResponse.getStatusLine().getStatusCode());
                responseJson.put("respMsg","应答码错误");
            }
            httpGet.abort();
        } catch (Exception e) {
            try {
                responseJson.put("respCode","501");
                responseJson.put("respMsg",e.getMessage());
            } catch (JSONException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
        }

        System.out.println("应答结果:" + responseJson.toString());
        return responseJson;
    }

    public static JSONObject httpClientPostJson(String url, JSONObject json){
        HttpClient httpClient = getHttpClient();
        HttpPost httpPost = new HttpPost(url);
        System.out.println("请求url:" + url);
        System.out.println("请求参数:" + json.toString());
        httpPost.addHeader("Content-Type","application/json");
        HttpResponse httpResponse = null;
        JSONObject responseJson = new JSONObject();
        try {
            httpPost.setEntity(new StringEntity(json.toString()));
            httpResponse = httpClient.execute(httpPost);
            if (httpResponse.getStatusLine().getStatusCode() == 200) {

                responseJson.put("respCode","200");
                responseJson.put("respMsg","成功");

                if(httpResponse.getEntity().isStreaming()){
                    FileOutputStream fos = new FileOutputStream(new File("/Users/chenkai/Downloads/a.dmg"));
                    fos.write(EntityUtils.toByteArray(httpResponse.getEntity()));
                    fos.flush();
                    fos.close();
                }else{
                    JSONObject jsonObject = new JSONObject(EntityUtils.toString(httpResponse.getEntity()));
//                System.out.println("base64:::"+jsonObject.getString("filebase64"));
//                System.out.println("hexstr:::"+jsonObject.getString("filehexstr"));
//                    byte[] b = Base64.decode(jsonObject.getString("filebase64"));
//                    FileOutputStream fos = new FileOutputStream(new File("/Users/chenkai/Downloads/a.dmg"));
//                    fos.write(b);
//                    fos.flush();
//                    fos.close();
                }


            }else{
                responseJson.put("respCode",httpResponse.getStatusLine().getStatusCode());
                responseJson.put("respMsg","应答码错误");
            }
            httpPost.abort();

        } catch (Exception e) {
            try {
                responseJson.put("respCode","501");
                responseJson.put("respMsg",e.getMessage());
            } catch (JSONException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
        }
        System.out.println("应答数据:" + responseJson.toString());
        return responseJson;
    }

    public static JSONObject httpClientPost(String url, List<NameValuePair> params){
        return httpClientPost(url,params,"");
    }

    public static JSONObject httpClientPost(String url, List<NameValuePair> params,String contentType) {
        HttpClient httpClient = getHttpClient();
        HttpPost httpPost = new HttpPost(url);
        System.out.println("请求url:" + url);
        if(contentType.equals(""))
            httpPost.addHeader("Content-Type","text/html");
        else if(contentType.equals("json")){
            httpPost.addHeader("Content-Type","application/json");
        }
        HttpResponse httpResponse = null;
        JSONObject responseJson = new JSONObject();
        try {
            if(contentType.equals("json")){
                httpPost.setEntity(new StringEntity(listToJSONStr(params)));
            }else{
                httpPost.setEntity(new UrlEncodedFormEntity(params));
            }
            httpResponse = httpClient.execute(httpPost);
            if (httpResponse.getStatusLine().getStatusCode() == 200) {
                responseJson.put("respCode","200");
                responseJson.put("respMsg","成功");

                if(httpResponse.getEntity().isStreaming()){
                    FileOutputStream fos = new FileOutputStream(new File("/Users/chenkai/Downloads/b.dmg"));
                    fos.write(EntityUtils.toByteArray(httpResponse.getEntity()));
                    fos.flush();
                    fos.close();
                }else{
                    responseJson.put("respData",EntityUtils.toString(httpResponse.getEntity()));
                }

            }else{
                responseJson.put("respCode",httpResponse.getStatusLine().getStatusCode());
                responseJson.put("respMsg","应答码错误");
            }
            httpPost.abort();

        } catch (Exception e) {
            try {
                responseJson.put("respCode","501");
                responseJson.put("respMsg",e.getMessage());
            } catch (JSONException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
        }
        System.out.println("应答数据:" + responseJson.toString());
        return responseJson;
    }


    private static String listToJSONStr(List<NameValuePair> params){
        JSONObject json = new JSONObject();
        try {
            for (NameValuePair param : params) {
                json.put(param.getName(), param.getValue());
            }
        }catch (Exception e){e.printStackTrace();}
        return json.toString();
    }

    //原生HttpURLConnection
    public static JSONObject request(JSONObject json) throws JSONException {
        String requestMethod = null;
        if (json.has("requestMethod"))
            requestMethod = json.getString("requestMethod");

        String requestUrl = null;
        if (json.has("requestUrl"))
            requestUrl = json.getString("requestUrl");

        JSONObject requestParams = null;
        if (json.has("requestParams"))
            requestParams = json.getJSONObject("requestParams");
        System.out.println(requestParams.toString());
        if (requestUrl == null || requestMethod == null) {
            throw new NullPointerException("requestUrl and requestMethod should not be null");
        }
        String paramString = null;

        HttpURLConnection connection = null;
        JSONObject rslt = new JSONObject();
        try {
            paramString = parserRequestParamsToJSON(requestParams);
            connection = (HttpURLConnection) new URL(requestUrl).openConnection();
            if (connection instanceof HttpsURLConnection) {
                //如果使用https连接,则需要设置
            }
            //开启读写
            connection.setDoInput(true);
            connection.setDoOutput(true);

            connection.setRequestMethod(requestMethod.toUpperCase());
            connection.setConnectTimeout(10000);//设置连接超时
            connection.setReadTimeout(40000); //超时一分种

            //参数设置
            if (paramString != null) {
                if (requestMethod.equalsIgnoreCase("get")) {
                    if (requestUrl.indexOf("?") >= 0) {
                        requestUrl += "&" + paramString;
                    } else {
                        requestUrl += "?" + paramString;
                    }
                } else {
                    connection.setRequestProperty("Content-type", "application/x-www-form-urlencoded");

                    OutputStream output = connection.getOutputStream();
                    try {
                        output.write(paramString.getBytes("utf-8"));
                        output.flush();
                    } finally {
                        if (output != null)
                            output.close();
                    }
                }
            }

            int responseCode = connection.getResponseCode();
            int read = 0;
            byte[] buffer = new byte[1024];
            InputStream input = null;
            try {
                input = connection.getInputStream();
                String encoding = connection.getContentEncoding();
                if (encoding == null)
                    encoding = "utf-8";

                String contentType = connection.getContentType();
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                while ((read = input.read(buffer)) > 0) {
                    bos.write(buffer, 0, read);
                }
                bos.flush();
                String responseRslt = new String(bos.toByteArray(), encoding);

                //rslt.put("contentType", contentType);
                rslt.put("httpStatus", responseCode);
                rslt.put("rslt", responseRslt);

            } finally {
                if (input != null)
                    input.close();
            }
        } catch (MalformedURLException e) {
            rslt.put("httpStatus", 501);
            rslt.put("rslt", "{\"Result\":\"501\",\"Message\":\"URL格式不对\",\"MessageEN\":\"URL format is wrong\"}");
            e.printStackTrace();
        } catch (ProtocolException e) {
            rslt.put("httpStatus", 501);
            rslt.put("rslt", "{\"Result\":\"501\",\"Message\":\"协议异常\",\"MessageEN\":\"protocol anomaly\"}");
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            rslt.put("httpStatus", 501);
            rslt.put("rslt", "{\"Result\":\"501\",\"Message\":\"不支持的编码格式\",\"MessageEN\":\"encoding is unsupported\"}");
            e.printStackTrace();
        } catch (IOException e) {
            rslt.put("httpStatus", 501);
            rslt.put("rslt", "{\"Result\":\"501\",\"Message\":\"无法连接订单平台\",\"MessageEN\":\"can't connect order platform\"}");
            e.printStackTrace();
        } finally {
            if (connection != null)
                connection.disconnect();
        }
        return rslt;
    }

    private static String parserRequestParamsToJSON(JSONObject requestParams) throws JSONException, UnsupportedEncodingException {
        if (requestParams == null)
            return null;

        StringBuilder sb = new StringBuilder();
        for (Iterator itor = requestParams.keys(); itor.hasNext(); ) {
            String key = (String) itor.next();
            String value = requestParams.getString(key);
            if (value == null || value.length() <= 0)
                continue;
            sb.append(key);
            sb.append("=");
            sb.append(URLEncoder.encode(value, "utf-8"));
            sb.append("&");
        }
        return sb.toString().substring(0, sb.length() - 1);
    }
}
