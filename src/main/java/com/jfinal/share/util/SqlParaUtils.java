package com.jfinal.share.util;

/**
 * sql 工具类
 * 
 * @author 風佑兲(375910297@qq.com)
 * @date 2014年12月5日 上午10:44:18
 */
public class SqlParaUtils {

	/**
	 * 处理精确查找参数(equals)
	 * 
	 * @param para
	 * @return
	 */
	public static String eq(String para) {
		if (null == para || "".equals(para)) {
			return "";
		} else {
			return para;
		}
	}

	/**
	 * 处理模糊查找参数(like)
	 * 
	 * @param para
	 * @return
	 */
	public static String like(String para) {
		if (null == para || "".equals(para) || "null".equals(para)) {
			para = "%";
		} else {
			para = "%" + para + "%";
		}
		return para;
	}
	
	/**
	 * 处理参数为int的模糊查找(integerLike)
	 * 
	 * @param para
	 * @return
	 */
	public static String integerlike(Number para) {
		String paraToString = "";
		if (null == para || "".equals(para) || "null".equals(para)) {
			paraToString = "%";
		} else {
			paraToString = "%" + para + "%";
		}
		return paraToString;
	}
	
	/**
	 * 处理时间段
	 * 
	 * @param startTime 开始时间
	 * @param endTime  结束时间
	 * @return startTime 开始时间
	 */
	public static String startTime(String startTime, String endTime) {
		if(null != startTime && !"".equals(startTime)){
			startTime = (startTime + " 00:00:00").replace("/","-");
		}
		//如果开始时间为空，结束时间不为空，则将开始时间设为结束时间+" 00:00:00"
		if((null == startTime || "".equals(startTime)) && (null != endTime && !"".equals(endTime))){
			startTime = (endTime + " 00:00:00").replace("/","-");
		}
		return startTime;
	}
	
	/**
	 * 处理时间段
	 * 
	 * @param startTime 开始时间
	 * @param endTime  结束时间
	 * @return end_time 结束时间
	 */
	public static String endTime(String startTime, String endTime) {
		if(null != endTime && !"".equals(endTime)){
			endTime = (endTime + " 23:59:59").replace("/","-");
		}
		//如果结束时间为空，开始时间不为空，则将结束时间设为开始时间+" 23:59;59"
		if((null != startTime && !"".equals(startTime)) && (null == endTime || "".equals(endTime))){
			endTime = (startTime + " 23:59:59").replace("/","-");
		}
		return endTime;
	}
}
