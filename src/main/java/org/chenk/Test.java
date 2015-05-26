package org.chenk;

import org.apache.http.client.HttpClient;
import org.json.JSONObject;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.SecureRandom;
import java.util.Random;

/**
 * Created by Administrator on 2015/5/14.
 */
public class Test {
    public static void main(String[] args) throws Exception{
//        Random random = new SecureRandom();
//        long r0 = random.nextLong();
//        long r1 = random.nextLong();
//        if (r0 < 0) r0 = -r0;
//        if (r1 < 0) r1 = -r1;
//
//        System.out.println("r0="+r0+",radix(36)="+Long.toString(r0,36));
//        System.out.println("r1="+r1+",radix(36)="+Long.toString(r1,36));
//
//        String s="http://www.步类集";
//        System.out.println(URLEncoder.encode(s, "utf-8"));
        JSONObject obj = HttpClientUtil.httpClientGet("http://www.fzbm.com/topic/index.php?g=201504&m=Interfaces&a=setVote&oid=910");
        System.out.println(obj.toString());
    }
}
