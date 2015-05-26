package com.jfinal.share.controller.web;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.jfinal.aop.ClearInterceptor;
import com.jfinal.aop.ClearLayer;
import com.jfinal.ext.route.ControllerBind;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.share.controller.WebController;
import com.jfinal.share.model.Blog;
import com.jfinal.share.model.Dictionary;
import com.jfinal.share.service.WebService;
import com.jfinal.share.service.admin.BlogService;
import com.jfinal.share.service.admin.DictionaryService;

/**
 * 博客前台
 * 
 * @author 風佑兲(375910297@qq.com)
 * @date 2014年12月6日 下午10:24:42
 */
@ControllerBind(controllerKey = "/")
@ClearInterceptor(ClearLayer.ALL)
public class IndexController extends WebController {
	private WebService webService = new WebService();
	private BlogService blogService = new BlogService();
	private DictionaryService dictionaryService = new DictionaryService();
	
	/**
	 * 网站首页
	 */
	public void index() {
		// 获取热门博客 10篇
		Page<Blog> hotBlogs = blogService.getHotBlogPage(1, 10); // 无条件无关联分页获取热门博客
		Map<Integer, String> hotBlogTagName = new TreeMap<Integer, String>(); // 用于存储热门博客的标签名称
		Map<Integer, String> hotBlogTagId = new TreeMap<Integer, String>(); // 用于存储热门博客的标签的id
		webService.getTagNameAndId(hotBlogs.getList(), hotBlogTagName, hotBlogTagId); // 获得每一篇博客的标签名称和id
		setAttr("hotBlogs", hotBlogs.getList());
		setAttr("hotBlogTagName", hotBlogTagName);
		setAttr("hotBlogTagId", hotBlogTagId);
		
		// 获取最新博客 10篇
		Page<Blog> newBlogs = blogService.getBlogPage(1, 10); // 无条件无关联分页获取博客
		Map<Integer, String> newBlogTagName = new TreeMap<Integer, String>(); // 用于存储最新的博客的标签名称
		Map<Integer, String> newBlogTagId = new TreeMap<Integer, String>(); // 用于存储最新的博客的标签的id
		webService.getTagNameAndId(newBlogs.getList(), newBlogTagName, newBlogTagId); // 获得每一篇博客的标签名称和id
		setAttr("newBlogs", newBlogs.getList());
		setAttr("newBlogTagName", newBlogTagName);
		setAttr("newBlogTagId", newBlogTagId);
		
		// 获取标签
		List<Dictionary> tagList = dictionaryService.getDictionaryByCode("tag"); // 获取标签
		Map<Integer, Integer> tagBlogNumber = new TreeMap<Integer, Integer>();
		webService.getTagBlogNumber(tagList, tagBlogNumber); // 获取每一条标签的博客数量
		setAttr("tags", tagList);
		setAttr("tagBlogNumber", tagBlogNumber);
		
		render("web/index.html");
	}
	
}
