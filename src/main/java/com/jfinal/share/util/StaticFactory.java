package com.jfinal.share.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.jfinal.share.model.Dictionary;
import com.jfinal.share.model.Link;
import com.jfinal.share.model.WebConfig;

/**
 * 静态工厂
 * 
 * @author 風佑兲 (375910297@.qq.com)
 * @date 2014年7月25日 下午4:58:03
 */
public class StaticFactory {
	private static Map<String,String> webConfig = new TreeMap<String,String>();
	private static List<Link> myProject = new ArrayList<Link>();
	private static List<Link> friendlyLink = new ArrayList<Link>();
	
	/**
	 * 获取网页配置
	 * 
	 * @return Map<String,String>
	 */
	public synchronized static Map<String,String> getSystemConfigMap() {
		if (webConfig.isEmpty()) {
			setSystemConfigMap();
		}
		return webConfig;
	}

	/**
	 * 保存网页静态配置
	 */
	public synchronized static void setSystemConfigMap() {
		List<WebConfig> webConfigs = WebConfig.dao.searchByIsSystem(ModelEnum.YesOrNO.YES.ordinal());
		for (WebConfig wc : webConfigs) {
			webConfig.put(wc.getStr(WebConfig.K).toUpperCase(), wc.getStr(WebConfig.VAL));
		}
	}
	
	/**
	 * 清空WebConfig
	 * 适用于修改网页配置时调用
	 */
	public synchronized static void clearWebConfig() {
		webConfig.clear();
	}
	
	/**
	 * 获取我的项目
	 * 
	 * @return List<Link>
	 */
	public synchronized static List<Link> getMyProjectList() {
		if (myProject.isEmpty()) {
			setMyProjectList();
		}
		return myProject;
	}

	/**
	 * 保存我的项目静态配置
	 */
	public synchronized static void setMyProjectList() {
		Dictionary dictionary = Dictionary.dao.searchByCode("myProject");
		myProject = Link.dao.searchByType(dictionary.getInt(Dictionary.ID));
	}
	
	/**
	 * 清空MyProject
	 * 适用于修改添加我的项目时调用
	 */
	public synchronized static void clearMyProject() {
		myProject.clear();
	}
	
	/**
	 * 获取友情链接
	 * 
	 * @return List<Link>
	 */
	public synchronized static List<Link> getFriendlyLinkList() {
		if (friendlyLink.isEmpty()) {
			setFriendlyLinkList();
		}
		return friendlyLink;
	}

	/**
	 * 保存友情链接静态配置
	 */
	public synchronized static void setFriendlyLinkList() {
		Dictionary dictionary = Dictionary.dao.searchByCode("friendlyLink");
		friendlyLink = Link.dao.searchByType(dictionary.getInt(Dictionary.ID));
	}
	
	/**
	 * 清空friendlyLink
	 * 适用于修改添加我的项目时调用
	 */
	public synchronized static void clearFriendlyLink() {
		friendlyLink.clear();
	}
}
