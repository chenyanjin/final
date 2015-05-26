package com.jfinal.share.model;

import java.util.List;

import com.jfinal.ext.plugin.tablebind.TableBind;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Model;

/**
 * 博客标签
 * 
 * @author 風佑兲(375910297@qq.com)
 * @date 2014年11月29日 下午10:53:16
 */
@TableBind(tableName = "blog_tag")
public class BlogTag extends Model<BlogTag> {

	private static final long serialVersionUID = -5249007156111877114L;
	public static final BlogTag dao = new BlogTag();

	public static final String TABLE_NAME = "blog_tag";

	public static final String ID = "id";
	public static final String BLOG_ID = "blog_id"; 				// blog_id
	public static final String TAG_ID = "tag_id"; 					// tag_id
	
	/**
	 * 根据blogId查找blogTag
	 * 
	 * @param blogId 博客id
	 * @return
	 */
	public List<BlogTag> searchByBlogId(int blogId) {
		String sql = "SELECT * FROM blog_tag WHERE blog_id = ?";
		return find(sql, blogId);
	}

	/**
	 * 根据blogId来删除tag_id
	 * 
	 * @param blogId 博客id
	 * @return boolean
	 */
	public boolean deleteByBlogId(Integer blogId) {
		String sql = "DELETE FROM blog_tag WHERE blog_id = ?";
		return 0 == Db.update(sql, blogId) ? true : false;
	}
	
	/**
	 * 根据ids批量删除博客标签
	 * 
	 * @param blogids
	 * @return boolean
	 */
	public boolean deleteByIds(String[] blogids) {
		StringBuffer stringBuffer = new StringBuffer("DELETE FROM blog_tag WHERE ");
		if ( null != blogids && 1 == blogids.length) {
			stringBuffer.append("blog_id = ?");
			return 0 == Db.update(stringBuffer.toString(), blogids[0]) ? true : false;
		} else if(null != blogids && blogids.length > 1) {
			stringBuffer.append("blog_id in('");
			for (String id : blogids) {
				stringBuffer.append(id).append("','");
			}
			stringBuffer.delete(stringBuffer.length()-2, stringBuffer.length()); // 去掉最后一个,和'
			stringBuffer.append(")");
			return 0 == Db.update(stringBuffer.toString()) ? true : false;
		}
		
		return false;
	}
	
	/**
	 * 根据tagIds获取blog的数量
	 * 
	 * @param tagIds
	 * @return List<BlogTag>
	 */
	public List<BlogTag> searchByTagIds(String[] tagIds) {
		StringBuffer stringBuffer = new StringBuffer("SLECT * FROM blog_tag WHERE ");
		if ( null != tagIds && 1 == tagIds.length) {
			stringBuffer.append("tag_id = ?");
			return find(stringBuffer.toString(), tagIds[0]);
		} else if(null != tagIds && tagIds.length > 1) {
			stringBuffer.append("tag_id in('");
			for (String id : tagIds) {
				stringBuffer.append(id).append("','");
			}
			stringBuffer.delete(stringBuffer.length()-2, stringBuffer.length()); // 去掉最后一个,和'
			stringBuffer.append(")");
			return find(stringBuffer.toString());
		}
		return null;
	}

	/**
	 * 根据tagId统计blog的数量
	 * 
	 * @param tagId 标签id
	 * @return int BlogTag.getInt("num")
	 */
	public int searchCountByTagId(Integer tagId) {
		String sql = "SELECT COUNT(*) num FROM blog_tag WHERE tag_id = ?";
		return Integer.valueOf(findFirst(sql, tagId).getNumber("num").toString());
	}

}
