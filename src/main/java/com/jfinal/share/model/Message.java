package com.jfinal.share.model;

import java.util.List;

import com.jfinal.ext.plugin.tablebind.TableBind;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Page;

/**
 * 留言板
 * 
 * @author 風佑兲(375910297@qq.com)
 * @date 2014年12月16日 下午2:26:03
 */
@TableBind(tableName = "message")
public class Message extends Model<Message> {

	private static final long serialVersionUID = 3913441535227310064L;
	public static final Message dao = new Message();
	
	public static final String TABLE_NAME = "message";
	
	public static final String ID = "id"; // id
	public static final String PID = "pid"; // 父id
	public static final String BLOG_ID = "blog_id"; // 博客id
	public static final String TITLE = "title"; // 标题
	public static final String NAME = "name"; // 名称
	public static final String EMAIL = "email"; // 邮箱
	public static final String CONTENT = "content"; // 留言内容
	public static final String REPLYER ="replyer"; // 回复者， 0：普通用户 ， 1：留言者， 2：管理员
	public static final String CREATE_TIME = "create_time"; // 创建时间
	public static final String STATUS = "status"; // 状态 0：未回复，1:已回复，2：有新回复
	
	/**
	 * 分页获取留言信息
	 * 
	 * @param pageNumber 当前页
	 * @param pageSize 每页显示条目
	 * @param message 留言条件
	 * @param startTime 开始时间
	 * @param endTime 结束时间
	 * @return Page<Message>
	 */
	public Page<Message> searchPaginate(int pageNumber, int pageSize, Message message, String startTime, String endTime) {
		String select = "SELECT * ";
		StringBuffer stringBuffer = new StringBuffer("FROM message WHERE pid IS NULL ");
		// 先判断message中是否存在条件
		if (null != message) {
			// 判断状态是否为空，不为空添加条件
			if (null != message.getStr(STATUS) && !"".equals(message.getStr(STATUS))) {
				stringBuffer.append("AND ").append(STATUS).append(" = '").append(message.getStr(STATUS)).append("' ");
			}
			// 判断博客id是否为空，不为空添加条件
			if (null != message.getStr(BLOG_ID) && !"".equals(message.getStr(BLOG_ID))) {
				stringBuffer.append("AND ").append(BLOG_ID).append(" = '").append(message.getStr(BLOG_ID)).append("' ");
			}
		}
		// 判断开始时间和结束时间都不为空则查找包含时间段，否则不包含时间段查找
		if (null != startTime && !"".equals(startTime) && null != endTime && !"".equals(endTime)) {
			stringBuffer.append("AND ").append(CREATE_TIME).append(" BETWEEN '").append(startTime).append("' AND '").append(endTime).append("' ");
		}
		stringBuffer.append("ORDER BY id DESC");
		
		return paginate(pageNumber, pageSize, select, stringBuffer.toString());
	}

	/**
	 * 通过pid获得一个Message的Reply实例
	 * 
	 * @param pid 父id
	 * @return List<Message>
	 */
	public List<Message> searchByPid(Object pid) {
		String sql = "SELECT * FROM message WHERE pid = ? ORDER BY id DESC";
		return find(sql, pid);
	}
	
	/**
	 * 通过pid获得一个Message最新的一条reply信息
	 * 
	 * @param pid 父id
	 * @return Message
	 */
	public Message searchFirstByPid(Object pid) {
		String sql = "SELECT * FROM message WHERE pid = ? ORDER BY id DESC";
		return findFirst(sql, pid);
	}

	/**
	 * 通过blogId获得一个Message的Reply实例
	 * 
	 * @param blogId 博客id
	 * @return List<Message>
	 */
	public List<Message> searchByBlogId(String blogId) {
		String sql = "SELECT * FROM message WHERE pid IS NULL AND blog_id = ? ORDER BY id DESC";
		return find(sql, blogId);
	}
	
	/**
	 * 根据ids批量删除博客
	 * 
	 * @param ids
	 * @return boolean
	 */
	public boolean deleteByIds(String[] ids) {
		StringBuffer stringBuffer = new StringBuffer("DELETE FROM message WHERE ");
		if ( null != ids && 1 == ids.length) {
			stringBuffer.append("id = ? OR pid = ?");
			return 0 == Db.update(stringBuffer.toString(), ids[0], ids[0]) ? true : false;
		} else if(null != ids && ids.length > 1) {
			stringBuffer.append("id IN('");
			// 封装in里的id以便 id or pid使用
			StringBuffer conditionStringBuffer = new StringBuffer();
			for (String id : ids) {
				conditionStringBuffer.append(id).append("','");
			}
			conditionStringBuffer.delete(conditionStringBuffer.length()-2, conditionStringBuffer.length()); // 去掉最后一个,和'
			
			stringBuffer.append(conditionStringBuffer.toString()).append(") ")
								.append("OR pid IN('").append(conditionStringBuffer.toString()).append(") ");
			return 0 == Db.update(stringBuffer.toString()) ? true : false;
		}
		
		return false;
	}

	/**
	 * 分页获取最新的回复
	 * 
	 * @param pageNumber 当前页
	 * @param pageSize 每页显示条目
	 * @return Page<Message>
	 */
	public Page<Message> searchPaginateNewReply(int pageNumber, int pageSize) {
		String select = "SELECT * ";
		String sqlExceptSelect = "FROM message WHERE pid IS NOT NULL ORDER BY id DESC";
		return paginate(pageNumber, pageSize, select, sqlExceptSelect);
	}

	/**
	 * 根据blogId统计message和reply的数量
	 * 
	 * @param blogId 博客id
	 * @return int
	 */
	public int searchCountByBlogId(Object blogId) {
		String sql = "SELECT COUNT(*) num FROM message WHERE pid IN(SELECT id FROM message WHERE blog_id = ?) OR blog_id = ?";
		return Integer.valueOf(findFirst(sql, blogId, blogId).getNumber("num").toString());
	}

}
