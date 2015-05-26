package com.jfinal.share.service.admin;

import java.util.List;

import com.jfinal.share.model.BlogTag;
import com.jfinal.share.model.Dictionary;

/**
 * blogTag 业务逻辑层
 * 
 * @author 風佑兲(375910297@qq.com)
 * @date 2014年12月7日 下午11:12:53
 */
public class BlogTagService {
	private String message;

	public String getMessage() {
		return message;
	}
	
	/**
	 * 根据blogId获取标签
	 * 
	 * @param blogId 博客id
	 * @return List<BlogTag>
	 */
	public List<BlogTag> blogTagList(int blogId) {
		// 构建 sql
		StringBuffer stringBuffer = new StringBuffer("SELECT ");
		stringBuffer.append("bt.").append(BlogTag.ID).append(", ")
						    .append("bt.").append(BlogTag.BLOG_ID).append(", ")
						    .append("bt.").append(BlogTag.TAG_ID).append(", ")
						    .append("d.").append(Dictionary.NAME).append(" ")
							.append("FROM ")
							.append(BlogTag.TABLE_NAME).append(" bt ")
							.append("LEFT JOIN ").append(Dictionary.TABLE_NAME).append(" d ON(d.").append(Dictionary.ID).append(" = bt.").append(BlogTag.TAG_ID).append(") ")
							.append("WHERE bt.").append(BlogTag.BLOG_ID).append(" = ? ")
							.append("ORDER BY d.").append(Dictionary.SORT).append(" ASC, d.").append(Dictionary.ID).append(" DESC");
		
		return BlogTag.dao.find(stringBuffer.toString(), blogId);
	}

	/**
	 * 获得博客的标签以 - 连接字符串
	 * 
	 * @param blogId 博客id
	 * @return
	 */
	public String getBlogTag(int blogId) {
		StringBuilder stringBuilder = new StringBuilder();
		List<BlogTag> blogTagList = BlogTag.dao.searchByBlogId(blogId);
		stringBuilder.append("-");
		for (BlogTag blogTag : blogTagList) {
			stringBuilder.append(blogTag.getInt(BlogTag.TAG_ID));
			stringBuilder.append("-");
		}
		return stringBuilder.toString();
	}
	
	/**
	 * 根据tagId统计blog的数量
	 * 
	 * @param tagId 标签id
	 * @return int
	 */
	public int getBlogCountByTagId(int tagId) {
		return BlogTag.dao.searchCountByTagId(tagId);
	}
	
	/**
	 * 根据tagIds获取blog的数量
	 * 
	 * @param tagId 标签id
	 * @return int
	 */
	public List<BlogTag> getBlogByTagIds(String[] tagIds) {
		return BlogTag.dao.searchByTagIds(tagIds);
	}
}
