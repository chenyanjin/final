package com.jfinal.share.service.admin;

import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;

import com.jfinal.plugin.activerecord.Page;
import com.jfinal.share.core.Constant;
import com.jfinal.share.model.Message;
import com.jfinal.share.model.User;
import com.jfinal.share.util.DateUtils;
import com.jfinal.share.util.ModelEnum;

/**
 * message 后台 业务逻辑层
 * 
 * @author 風佑兲(375910297@qq.com)
 * @date 2014年12月16日 下午2:45:39
 */
public class MessageService {

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
	public Page<Message> getMessagePage(int pageNumber, int pageSize, Message message, String startTime, String endTime) {
		return Message.dao.searchPaginate(pageNumber, pageSize, message, startTime, endTime);
	}
	
	/**
	 * 回复主题会话Message(添加一条Message作为Reply)
	 * 
	 * @param reply 回复内容
	 * @return
	 */
	public boolean modifyReply(Message reply) {
		boolean flag = false;
		Integer pid = reply.getInt(Message.PID);
		
		if (null !=  pid|| !"".equals(pid)) {
			Message message = Message.dao.findById(pid);
			
			// 获取登陆用户
			Subject subject = SecurityUtils.getSubject();
			Session session = subject.getSession();
			User loginUser = (User) session.getAttribute(Constant.SHIRO_LOGIN_USER);
			
			//如果loginUser不为空则是后台回复 loginUser为空则为前台回复
			if (null != loginUser) {
				// 设置pid，姓名，回复者，回复时间（创建时间）
				reply.set(Message.PID, pid)
						.set(Message.REPLYER, ModelEnum.Replyer.ADMINISTRATOR.ordinal())
						.set(Message.CREATE_TIME, DateUtils.getCurrentTime());
				// 主题留言状态设置成已回复
				message.set(Message.STATUS, ModelEnum.ReplyMessage.REPLYED.ordinal());
			} else {
				// 设置pid，回复者，回复时间（创建时间）
				reply.set(Message.PID, pid)
						.set(Message.CREATE_TIME, DateUtils.getCurrentTime());
				// 如果回复人姓名与主题者姓名相同则replyer设为作者，否则设为普通会员
				if (reply.getStr(Message.NAME).equals(message.getStr(Message.NAME))) {
					reply.set(Message.REPLYER, ModelEnum.Replyer.AUTHOR.ordinal());
				} else {
					reply.set(Message.REPLYER, ModelEnum.Replyer.MEMBER.ordinal());
				}
				// 主题留言状态设置成有新回复
				message.set(Message.STATUS, ModelEnum.ReplyMessage.NEWREPLY.ordinal());
			}
			
			reply.save();
			message.update();
			
			flag = true;
		}
		
		return flag;
	}
	
	/**
	 * 通过id获取留言 或 回复
	 * 
	 * @param id 
	 * @return Message
	 */
	public Message getById(Object id) {
		return Message.dao.findById(id);
	}

	/**
	 * 通过pid获得一个Message的Reply回复信息
	 * 
	 * @param pid 父id
	 * @return List<Message>
	 */
	public List<Message> getReplyByPid(Object pid) {
		return Message.dao.searchByPid(pid);
	}
	
	/**
	 * 通过pid获得一个Message最新的一条reply信息
	 * 
	 * @param pid 父id
	 * @return LMessage
	 */
	public Message getFirstReplyByPid(Object pid) {
		return Message.dao.searchFirstByPid(pid);
	}
	
	/**
	 * 批量删除Message
	 * 
	 * @param ids
	 * @return String
	 */
	public String deleteMessages(String[] ids) {
		Message.dao.deleteByIds(ids); // 删除留言及其回复
		return "成功删除了[" + ids.length + "]条留言！";
	}
	
	/**
	 * 前台创建添加一条留言
	 * 
	 * @param message 留言
	 * @return
	 */
	public boolean addMessage(Message message) {
		// 设置创建时间、状态置为未回复
		message.set(Message.CREATE_TIME, DateUtils.getCurrentTime())
					   .set(Message.STATUS, ModelEnum.ReplyMessage.DIDNOREPLY.ordinal())
					   .save();
		return true;
	}

	/**
	 * 根据博客id获取博客留言
	 * 
	 * @param blogId 博客id
	 * @return List<Message>
	 */
	public List<Message> getMessageByBlogId(String blogId) {
		return Message.dao.searchByBlogId(blogId);
	}

	/**
	 * 获取最新回复
	 * 
	 * @param pageNumber
	 * @param pageSize
	 * @return Page<Message>
	 */
	public Page<Message> getNewReplys(int pageNumber, int pageSize) {
		return Message.dao.searchPaginateNewReply(pageNumber, pageSize);
	}
	
	/**
	 * 根据blogId统计message和reply的数量
	 * 
	 * @param blogId 标签id
	 * @return int
	 */
	public int getCountByBlogId(Object blogId) {
		return Message.dao.searchCountByBlogId(blogId);
	}
	
}
