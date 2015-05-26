package com.jfinal.share.service;

import java.util.List;
import java.util.Map;

import com.jfinal.share.model.Blog;
import com.jfinal.share.model.BlogTag;
import com.jfinal.share.model.Dictionary;
import com.jfinal.share.model.Message;
import com.jfinal.share.service.admin.BlogTagService;
import com.jfinal.share.service.admin.MessageService;

public class WebService {
	private BlogTagService blogTagService = new BlogTagService();
	private MessageService messageService = new MessageService();
	
	/**
	 * 查找出blogList中的blog并分离出标签名和id
	 * 
	 * @param blogList 博客
	 * @param blogTagName 博客的标签名称
	 * @param blogTagId 博客的标签id
	 */
	public void getTagNameAndId(List<Blog> blogList, Map<Integer, String> blogTagName, Map<Integer, String> blogTagId) {
		if (!blogList.isEmpty()) {
			for (Blog blog : blogList) {
				StringBuffer blogTagNameStr = new StringBuffer();
				StringBuffer blogTagIdStr = new StringBuffer();
				
				List<BlogTag> blogTagList = blogTagService.blogTagList(blog.getInt(Blog.ID));
				for (int i = 0; i < blogTagList.size(); i++) {
					blogTagNameStr.append(blogTagList.get(i).getStr("name"));
					blogTagIdStr.append(blogTagList.get(i).getInt(BlogTag.TAG_ID));
					if (i != (blogTagList.size() - 1)) {
						blogTagNameStr.append(" & ");
						blogTagIdStr.append("&");
					}
				}
				
				blogTagName.put(blog.getInt(Blog.ID), blogTagNameStr.toString());
				blogTagId.put(blog.getInt(Blog.ID), blogTagIdStr.toString());
			}
		}
	}
	
	/**
	 * 分离出blog的标签名和id
	 * 
	 * @param blog 博客
	 * @param singleBlogTag 存储标签名和id 
	 */
	public List<BlogTag> getTagNameAndIdByBlog(Blog blog, Map<String, String> singleBlogTag) {
		if (null != blog) {
			StringBuffer blogTagNameStr = new StringBuffer();
			StringBuffer blogTagIdStr = new StringBuffer();
			
			List<BlogTag> blogTagList = blogTagService.blogTagList(blog.getInt(Blog.ID));
			for (int i = 0; i < blogTagList.size(); i++) {
				blogTagNameStr.append(blogTagList.get(i).getStr("name"));
				blogTagIdStr.append(blogTagList.get(i).getInt(BlogTag.TAG_ID));
				if (i != (blogTagList.size() - 1)) {
					blogTagNameStr.append(" & ");
					blogTagIdStr.append("&");
				}
			}
			
			singleBlogTag.put("name", blogTagNameStr.toString());
			singleBlogTag.put("id", blogTagIdStr.toString());
			return blogTagList;
		}
		return null;
	}

	/**
	 * 查找出每一个标签的博客数量
	 * 
	 * @param tagList 标签
	 * @param tagBlogNumber 用于存储标签对于博客数量
	 */
	public void getTagBlogNumber(List<Dictionary> tagList, Map<Integer, Integer> tagBlogNumber) {
		if (!tagList.isEmpty()) {
			for (Dictionary dictionary : tagList) {
				tagBlogNumber.put(dictionary.getInt(Dictionary.ID), blogTagService.getBlogCountByTagId(dictionary.getInt(Dictionary.ID)));
			}
		}
	}

	/**
	 * 根据博客留言信息查找出相应的回复信息
	 * 
	 * @param messages 博客留言信息
	 * @param replys 用于存储博客留言的回复内容
	 */
	public void getFirstReplyByPid(List<Message> messages, Map<Integer, Message> replys) {
		if (!messages.isEmpty()) {
			for (Message message : messages) {
				replys.put(message.getInt(Message.ID), messageService.getFirstReplyByPid(message.getInt(Message.ID)));
			}
		}
	}

	/**
	 * 根据回复信息获取留言信息
	 * 
	 * @param newReplys 回复信息
	 * @param messageMap  用于存储留言信息
	 */
	public void getPMessageByReply(List<Message> newReplys, Map<Integer, Message> messageMap) {
		if (!newReplys.isEmpty()) {
			Integer pid = 0;
			Message message = null;
			for (Message m : newReplys) {
				// 有回复信息对应同一条留言是，避免重复获取留言信息
				if (pid != m.getInt(Message.PID)) {
					pid = m.getInt(Message.PID);
					message = messageService.getById(pid);
				}				messageMap.put(pid, message);
			}
		}
	}

	/**
	 * 根据博客获取博客的留言和回复总数
	 * 
	 * @param blogs 博客
	 * @param blogMessageCount 用于存储每条博客的留言和回复总数
	 */
	public void getBlogMessageCountByBlog(List<Blog> blogs, Map<Integer, Integer> blogMessageCount) {
		if (!blogs.isEmpty()) {
			for (Blog blog : blogs) {
				blogMessageCount.put(blog.getInt(Blog.ID), messageService.getCountByBlogId(blog.getInt(Blog.ID)));
			}
		}
	}

}
