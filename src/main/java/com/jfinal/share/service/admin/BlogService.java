package com.jfinal.share.service.admin;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;

import com.jfinal.plugin.activerecord.Page;
import com.jfinal.share.core.Constant;
import com.jfinal.share.model.Blog;
import com.jfinal.share.model.BlogTag;
import com.jfinal.share.model.User;
import com.jfinal.share.util.DateUtils;
import com.jfinal.share.util.ModelEnum;

/**
 * blog 业务逻辑层
 * 
 * @author 風佑兲(375910297@qq.com)
 * @date 2014年12月6日 下午11:15:21
 */
public class BlogService {
	private String message;

	public String getMessage() {
		return message;
	}

	/**
	  * 分页查找blog
	 * (关联查询写在service层)
	 * 
	 * @param pageNumber 当前页
	 * @param pageSize 每页显示条数
	 * @param blog blog条件
	 * @param startTime 开始时间
	 * @param endTime 结束时间
	 * @return Page<Blog>
	 */
	public Page<Blog> getBlogPage(int pageNumber, int pageSize, Blog blog, String startTime, String endTime) {
		// 构建select sql
		StringBuffer selectStringBuffer = new StringBuffer("SELECT ");
		selectStringBuffer.append("b.").append(Blog.ID).append(", ")
									  .append("b.").append(Blog.TITLE).append(", ")
									  .append("b.").append(Blog.CONTENT).append(", ")
									  .append("b.").append(Blog.SUPPORT).append(", ")
									  .append("b.").append(Blog.CLICK_COUNT).append(", ")
									  .append("b.").append(Blog.TYPE).append(", ")
									  .append("b.").append(Blog.SHARE_URL).append(", ")
									  .append("b.").append(Blog.CREATE_TIME).append(", ")
									  .append("b.").append(Blog.UPDATE_TIME).append(", ")
									  .append("u.").append(User.NAME).append(" user_name").append(", ")
									  .append("u.").append(User.NICKNAME).append(" user_nickname").append(" ");
		
		// 构建from sql
		StringBuffer fromStringBuffer = new StringBuffer("FROM ");
		fromStringBuffer.append(Blog.TABLE_NAME).append(" b ")
									.append("LEFT JOIN ").append(User.TABLE_NAME).append(" u ON(b.").append(Blog.USER_ID).append(" = u.").append(User.ID).append(") ")
									.append("WHERE b.").append(Blog.DELETE_STATUS).append(" = '").append(ModelEnum.NormalOrDelete.NORMAL.ordinal()).append("' ");
		
		// 判断标题是否为空，不为空则添加条件
		if (null != blog.getStr(Blog.TITLE) && !"".equals(blog.getStr(Blog.TITLE))) {
			fromStringBuffer.append("AND b.").append(Blog.TITLE).append(" like '").append(blog.getStr(Blog.TITLE)).append("%' ");
		}
		// 判断开始时间和结束时间都不为空则查找包含时间段，否则不包含时间段查找
		if (null != startTime && !"".equals(startTime) && null != endTime && !"".equals(endTime)) {
			fromStringBuffer.append("AND b.").append(Blog.CREATE_TIME).append(" BETWEEN '").append(startTime).append("' AND '").append(endTime).append("' ");
		}
		// 获取登陆用户
		Subject subject = SecurityUtils.getSubject();
		Session session = subject.getSession();
		User loginUser = (User) session.getAttribute(Constant.SHIRO_LOGIN_USER);
		// 不为管理员，则只能查看自己发表的博客
		if (!Constant.SYSTEM_ADMINISTRATOR.equals(loginUser.getStr(User.USERNAME))) {
			fromStringBuffer.append("AND b.").append(Blog.USER_ID).append(" = '").append(loginUser.getInt(User.ID)).append("' ");
		}
		fromStringBuffer.append("ORDER BY b.").append(Blog.ID).append(" DESC");
		
		return Blog.dao.paginate(pageNumber, pageSize, selectStringBuffer.toString(), fromStringBuffer.toString());
	}
	
	/**
	 * 按标签分页获取博客
	 * 
	 * @param tagIds 标签
	 * @param pageNumber 当前页
	 * @param pageSize 每一页条目
	 * @return Page<Blog>
	 */
	public Page<Blog> getBlogPageByTag(int pageNumber, int pageSize, String[] tagIds) {
		// 构建select sql
		StringBuffer selectStringBuffer = new StringBuffer("SELECT DISTINCT ");
		selectStringBuffer.append("b.").append(Blog.ID).append(", ")
									  .append("b.").append(Blog.TITLE).append(", ")
									  .append("b.").append(Blog.CONTENT).append(", ")
									  .append("b.").append(Blog.SUPPORT).append(", ")
									  .append("b.").append(Blog.CLICK_COUNT).append(", ")
									  .append("b.").append(Blog.TYPE).append(", ")
									  .append("b.").append(Blog.SHARE_URL).append(", ")
									  .append("b.").append(Blog.CREATE_TIME).append(", ")
									  .append("b.").append(Blog.UPDATE_TIME).append(" ");
		
		// 构建from sql
		StringBuffer fromStringBuffer = new StringBuffer("FROM ");
		fromStringBuffer.append(BlogTag.TABLE_NAME).append(" bt ")
									.append("LEFT JOIN ").append(Blog.TABLE_NAME).append(" b ON(bt.").append(BlogTag.BLOG_ID).append(" = b.").append(Blog.ID).append(") ")
									.append("WHERE b.").append(Blog.DELETE_STATUS).append(" = '").append(ModelEnum.NormalOrDelete.NORMAL.ordinal()).append("' ");
		
		if ( null != tagIds && 1 == tagIds.length) {
			fromStringBuffer.append("AND bt.").append(BlogTag.TAG_ID).append(" = '").append(tagIds[0]).append("' ");
		} else if(null != tagIds && tagIds.length > 1) {
			fromStringBuffer.append("AND bt.").append(BlogTag.TAG_ID).append(" in('");
			for (String id : tagIds) {
				fromStringBuffer.append(id).append("','");
			}
			fromStringBuffer.delete(fromStringBuffer.length()-2, fromStringBuffer.length()); // 去掉最后一个,和'
			fromStringBuffer.append(") ");
		}
		
		fromStringBuffer.append("ORDER BY b.").append(Blog.ID).append(" DESC");
		
		return Blog.dao.paginate(pageNumber, pageSize, selectStringBuffer.toString(), fromStringBuffer.toString());
	}
	
	/**
	 * 无条件无关联分页获取博客
	 * 
	 * @param pageNumber 当前页
	 * @param pageSize 每一页条目
	 * @return Page<Blog>
	 */
	public Page<Blog> getBlogPage(int pageNumber, int pageSize) {
		return Blog.dao.searchPaginate(pageNumber, pageSize);
	}
	
	/**
	 * 无条件无关联分页获取热门博客（即：根据click_count倒叙排序）
	 * 
	 * @param pageNumber 当前页
	 * @param pageSize 每一页条目
	 * @return Page<Blog>
	 */
	public Page<Blog> getHotBlogPage(int pageNumber, int pageSize) {
		return Blog.dao.searchPaginateHotBlog(pageNumber, pageSize);
	}
	
	/**
	 * 无条件无关联分页获取赞最多的博客（即：根据support倒叙排序）
	 * 
	 * @param pageNumber 当前页
	 * @param pageSize 每一页条目
	 * @return Page<Blog>
	 */
	public Page<Blog> getSupportBlogPage(int pageNumber, int pageSize) {
		return Blog.dao.searchPaginateSupportBlog(pageNumber, pageSize);
	}
	
	/**
	 * 添加 或 修改 blog
	 * 
	 * @param blog
	 * @param blogTags
	 * @return boolean
	 */
	public boolean modify(Blog blog, String[] blogTags) {
		// 获取登陆用户
		Subject subject = SecurityUtils.getSubject();
		Session session = subject.getSession();
		User loginUser = (User) session.getAttribute(Constant.SHIRO_LOGIN_USER);
		
		Integer id = blog.getInt(Blog.ID);
		
		if(null == id) {
			//如果创建时间为空则设置为当前时间
			if(null == blog.get(Blog.CREATE_TIME)){
				blog.set(Blog.CREATE_TIME, DateUtils.getCurrentTime());
			}
			// support = 0（赞）、click_count=0（点击数）、delete_status=0（删除状态）、user_id
			blog.set(Blog.SUPPORT, 0)
				   .set(Blog.CLICK_COUNT, 0)
				   .set(Blog.DELETE_STATUS, ModelEnum.NormalOrDelete.NORMAL.ordinal())
				   .set(Blog.USER_ID, loginUser.get(User.ID))
				   .save();
			message = "博客添加成功";
		} else {
			BlogTag.dao.deleteByBlogId(id); // 删除博客的标签信息
			//如果创建时间为空则设置为当前时间
			if(null == blog.get(Blog.CREATE_TIME)){
				blog.set(Blog.CREATE_TIME, DateUtils.getCurrentTime());
			}
			blog.set(Blog.UPDATE_TIME, DateUtils.getCurrentDay())
				   .update();
			message = "博客编辑成功";
		}
		
		// 博客绑定标签
		if (null != blogTags) {
			BlogTag blogTag = null;
			for (String tagId : blogTags) {
				blogTag = new BlogTag();
				blogTag.set(BlogTag.BLOG_ID, blog.getInt(Blog.ID))
							 .set(BlogTag.TAG_ID, tagId)
							 .save();
			}
		}
		
		return true;
	}

	/**
	 * 批量删除博客
	 * 
	 * @param ids
	 */
	public void deletes(String[] ids) {
		Blog.dao.deleteByIds(ids); // 删除博客
		BlogTag.dao.deleteByIds(ids); // 删除博客标签
		message = "成功删除了[" + ids.length + "]篇博客！";
	}

	/**
	 * 根据id获取博客信息
	 * (关联查询写在service层)
	 * 
	 * @param id
	 * @return Blog
	 */
	public Blog getBlog(String id) {
		// 构建select sql
		StringBuffer sqlStringBuffer = new StringBuffer("SELECT ");
		sqlStringBuffer.append("b.").append(Blog.ID).append(", ")
								 .append("b.").append(Blog.TITLE).append(", ")
								 .append("b.").append(Blog.CONTENT).append(", ")
								 .append("b.").append(Blog.SUPPORT).append(", ")
								 .append("b.").append(Blog.CLICK_COUNT).append(", ")
								 .append("b.").append(Blog.TYPE).append(", ")
								 .append("b.").append(Blog.SHARE_URL).append(", ")
								 .append("b.").append(Blog.CREATE_TIME).append(", ")
								 .append("b.").append(Blog.UPDATE_TIME).append(", ")
								 .append("u.").append(User.NAME).append(" user_name").append(", ")
								 .append("u.").append(User.NICKNAME).append(" user_nickname").append(" ")
								 .append("FROM ")
								 .append(Blog.TABLE_NAME).append(" b ")
								 .append("LEFT JOIN ").append(User.TABLE_NAME).append(" u ON(b.").append(Blog.USER_ID).append(" = u.").append(User.ID).append(") ")
								 .append("WHERE b.").append(Blog.DELETE_STATUS).append(" = '").append(ModelEnum.NormalOrDelete.NORMAL.ordinal()).append("' ")
								 .append("AND b.").append(Blog.ID).append(" = ?");
		return Blog.dao.findFirst(sqlStringBuffer.toString(), id);
	}
	
}
