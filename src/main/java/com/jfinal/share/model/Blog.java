package com.jfinal.share.model;

import com.jfinal.ext.plugin.tablebind.TableBind;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Page;

/**
 * 博客
 * 
 * @author 風佑兲(375910297@qq.com)
 * @date 2014年11月29日 下午10:53:38
 */
@TableBind(tableName = "blog")
public class Blog extends Model<Blog> {

	private static final long serialVersionUID = 9055386396899161693L;
	public static final Blog dao = new Blog();

	public static final String TABLE_NAME = "blog";

	public static final String ID = "id"; // id
	public static final String TITLE = "title"; // 标题
	public static final String CONTENT = "content"; // 内容
	public static final String USER_ID = "user_id"; // user id
	public static final String SUPPORT = "support"; // 赞
	public static final String CLICK_COUNT = "click_count"; // 点击数
	public static final String TYPE = "type"; // 博客类型 默认：0（原创） 收藏：1
	public static final String SHARE_URL = "share_url"; // 收藏 url
	public static final String CREATE_TIME = "create_time"; // 添加时间
	public static final String UPDATE_TIME = "update_time"; // 更新时间
	public static final String DELETE_STATUS = "delete_status"; // 是否删除 默认：0 删除：1

	/**
	 * 分页查找博客
	 * 
	 * @param pageNumber
	 * @param pageSize
	 * @return Page<Blog>
	 */
	public Page<Blog> searchPaginate(int pageNumber, int pageSize) {
		String select = "SELECT * ";
		String from = "FROM blog WHERE delete_status = '0' ORDER BY id DESC";
		return paginate(pageNumber, pageSize, select, from);
	}
	
	/**
	 * 分页查找热门博客
	 * 
	 * @param pageNumber
	 * @param pageSize
	 * @return Page<Blog>
	 */
	public Page<Blog> searchPaginateHotBlog(int pageNumber, int pageSize) {
		String select = "SELECT * ";
		String from = "FROM blog WHERE delete_status = '0' ORDER BY click_count DESC";
		return paginate(pageNumber, pageSize, select, from);
	}
	
	/**
	 * 获取赞最多的博客（即：根据support倒叙排序）
	 * 
	 * @param pageNumber
	 * @param pageSize
	 * @return Page<Blog>
	 */
	public Page<Blog> searchPaginateSupportBlog(int pageNumber, int pageSize) {
		String select = "SELECT * ";
		String from = "FROM blog WHERE delete_status = '0' ORDER BY support DESC";
		return paginate(pageNumber, pageSize, select, from);
	}
	
	/**
	 * 根据ids批量删除博客
	 * 
	 * @param ids
	 * @return boolean
	 */
	public boolean deleteByIds(String[] ids) {
		StringBuffer stringBuffer = new StringBuffer("DELETE FROM blog WHERE ");
		if ( null != ids && 1 == ids.length) {
			stringBuffer.append("id = ?");
			return 0 == Db.update(stringBuffer.toString(), ids[0]) ? true : false;
		} else if(null != ids && ids.length > 1) {
			stringBuffer.append("id in('");
			for (String id : ids) {
				stringBuffer.append(id).append("','");
			}
			stringBuffer.delete(stringBuffer.length()-2, stringBuffer.length()); // 去掉最后一个,和'
			stringBuffer.append(")");
			return 0 == Db.update(stringBuffer.toString()) ? true : false;
		}
		
		return false;
	}

}
