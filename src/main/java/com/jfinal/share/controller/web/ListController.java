package com.jfinal.share.controller.web;

import java.util.Map;
import java.util.TreeMap;

import com.jfinal.aop.ClearInterceptor;
import com.jfinal.aop.ClearLayer;
import com.jfinal.ext.route.ControllerBind;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.share.controller.WebController;
import com.jfinal.share.model.Blog;
import com.jfinal.share.service.WebService;
import com.jfinal.share.service.admin.BlogService;

/**
 * 前台 list
 * 
 * @author 風佑兲(375910297@qq.com)
 * @date 2014年12月16日 下午6:21:54
 */
@ControllerBind(controllerKey = "/web/list")
@ClearInterceptor(ClearLayer.ALL)
public class ListController extends WebController {
	private WebService webService = new WebService();
	private BlogService blogService = new BlogService();
	
	/**
	 * 博客列表
	 */
	public void index() {
		int pageNumber = getParaToInt(0, 1);
		String tags = getPara(1, "fyt"); // 获取分页标识 pageFlag 默认fyt
		setAttr("pageFlag", tags);
		
		// 分页获取博客
		Page<Blog> blogs = null;
		// 标签为空则正常获取博客，不为空则按标签获取博客
		if ("fyt".equals(tags)) {
			blogs = blogService.getBlogPage(pageNumber, 10); // 无条件无关联分页获取博客
		} else {
			blogs = blogService.getBlogPageByTag(pageNumber, 10, tags.split("&")); // 按标签分页获取博客
		}
		Map<Integer, String> listBlogTagName = new TreeMap<Integer, String>(); // 用于存储博客的标签名称
		Map<Integer, String> listBlogTagId = new TreeMap<Integer, String>(); // 用于存储博客的标签的id
		webService.getTagNameAndId(blogs.getList(), listBlogTagName, listBlogTagId); // 获得每一篇博客的标签名称和id
		setAttr("page", blogs);
		setAttr("listBlogTagName", listBlogTagName);
		setAttr("listBlogTagId", listBlogTagId);
		
		// 获取赞最多的博客 10篇
		Page<Blog> supportBlogs = blogService.getSupportBlogPage(1, 10); // 无条件无关联分页获取赞最多的博客
		Map<Integer, String> supportBlogTagName = new TreeMap<Integer, String>(); // 用于存储赞最多的博客的标签名称
		Map<Integer, String> supportBlogTagId = new TreeMap<Integer, String>(); // 用于存储赞最多的博客的标签的id
		webService.getTagNameAndId(supportBlogs.getList(), supportBlogTagName, supportBlogTagId); // 获得每一篇博客的标签名称和id
		setAttr("supportBlogs", supportBlogs.getList());
		setAttr("supportBlogTagName", supportBlogTagName);
		setAttr("supportBlogTagId", supportBlogTagId);
		
		/*
		 // 获取最新回复
		Page<Message> newReplys = messageService.getNewReplys(1, 10);
		Map<Integer, Message> newMessages = new TreeMap<Integer, Message>();
		webService.getPMessageByReply(newReplys.getList(), newMessages);
		setAttr("newReplys", newReplys.getList());
		setAttr("newMessages", newMessages);
		
		// 获取博客的留言和回复总数
		Map<Integer, Integer> blogMessageCount = new TreeMap<Integer, Integer>(); // 用于存储博客的留言和回复总数
		webService.getBlogMessageCountByBlog(blogs.getList(), blogMessageCount); // 获得博客的留言和回复总数
		setAttr("blogMessageCount", blogMessageCount);
		*/
		
		setAttr("pageUrl", "web/list"); // 分页url
		
		render("list.html");
	}
}
