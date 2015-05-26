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
import com.jfinal.share.model.BlogTag;
import com.jfinal.share.service.WebService;
import com.jfinal.share.service.admin.BlogService;

/**
 * 前台 single
 * 
 * @author 風佑兲(375910297@qq.com)
 * @date 2014年12月16日 下午6:33:19
 */
@ControllerBind(controllerKey = "/web/single")
@ClearInterceptor(ClearLayer.ALL)
public class SingleController extends WebController {
	private WebService webService = new WebService();
	private BlogService blogService = new BlogService();
	
	/**
	 * 单页内容
	 */
	public void index() {
		// 获取博客
		String id = getPara();
		Blog blog = blogService.getBlog(id);
		
		// 博客点击数+1
		blog.set(Blog.CLICK_COUNT, blog.getInt(Blog.CLICK_COUNT) + 1)
			   .update();
		
		// 获取博客书签
		Map<String, String> singleBlogTag = new TreeMap<String, String>(); // 用于存储赞最多的博客的标签名称和id
		List<BlogTag> blogTagList = webService.getTagNameAndIdByBlog(blog, singleBlogTag); // 获得一篇博客的标签名称和id
		setAttr("blog", blog);
		setAttr("singleBlogTag", singleBlogTag);
		setAttr("tags", blogTagList);
		
		// 获取赞最多的博客 10篇
		Page<Blog> supportBlogs = blogService.getSupportBlogPage(1, 10); // 无条件无关联分页获取赞最多的博客
		Map<Integer, String> supportBlogTagName = new TreeMap<Integer, String>(); // 用于存储赞最多的博客的标签名称
		Map<Integer, String> supportBlogTagId = new TreeMap<Integer, String>(); // 用于存储赞最多的博客的标签的id
		webService.getTagNameAndId(supportBlogs.getList(), supportBlogTagName, supportBlogTagId); // 获得每一篇博客的标签名称和id
		setAttr("supportBlogs", supportBlogs.getList());
		setAttr("supportBlogTagName", supportBlogTagName);
		setAttr("supportBlogTagId", supportBlogTagId);
		
		/*
		// 获取该博客的留言和回复
		List<Message> messages = messageService.getMessageByBlogId(id); // 获取博客留言
		Map<Integer, Message> replys = new TreeMap<Integer, Message>();
		webService.getFirstReplyByPid(messages, replys); // 根据pid 查找出所有博客回复信息
		setAttr("blogMessages", messages);
		setAttr("blogReplys", replys);
		
		// 获取最新回复
		Page<Message> newReplys = messageService.getNewReplys(1, 10);
		Map<Integer, Message> newMessages = new TreeMap<Integer, Message>();
		webService.getPMessageByReply(newReplys.getList(), newMessages);
		setAttr("newReplys", newReplys.getList());
		setAttr("newMessages", newMessages);
		
		// 获取博客的留言和回复数量
		setAttr("blogMessageNumber", messageService.getCountByBlogId(id));
		*/
		
		render("single.html");
	}
	
	/**
	 * 博客支持数+1（赞）
	 */
	public void support() {
		Integer id = getParaToInt();
		Blog blog = Blog.dao.findById(id);
		
		blog.set(Blog.SUPPORT, blog.getInt(Blog.SUPPORT) + 1)
			   .update();
		
		renderJson(1);;
	}
}
