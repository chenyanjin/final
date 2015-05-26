package com.jfinal.share.controller.web;

import com.jfinal.aop.Before;
import com.jfinal.aop.ClearInterceptor;
import com.jfinal.aop.ClearLayer;
import com.jfinal.ext.interceptor.SessionInViewInterceptor;
import com.jfinal.ext.route.ControllerBind;
import com.jfinal.share.controller.WebController;
import com.jfinal.share.model.Message;
import com.jfinal.share.service.admin.MessageService;

@ControllerBind(controllerKey = "/web/message")
@ClearInterceptor(ClearLayer.ALL)
@Before(SessionInViewInterceptor.class)
public class MessageController extends WebController {
	private MessageService messageService = new MessageService();
	
	/**
	 * 留言板
	 */
	public void index() {
		/*
		int pageNumber = getParaToInt(0, 1);
		String blogId = getPara(1, "fyt"); // 获取分页标识 pageFlag 默认fyt
		setAttr("pageFlag", blogId);
		
		// 分页获取留言信息
		Page<Message> messages = null;
		Message message = null;
		if (!"fyt".equals(blogId)) {
			message = new Message();
			message.set(Message.BLOG_ID, blogId); // 博客id不为fyt则添加条件
			messages = messageService.getMessagePage(pageNumber, 20, message, null, null); // 博客留言不分页，之获取前20条留言
		} else {
			messages = messageService.getMessagePage(pageNumber, 10, message, null, null); // 分页获取留言信息前10条留言
		}
		setAttr("page", messages);
		
		setAttr("pageUrl", "web/message");
		*/
		
		render("message.html");
	}
	
	/**
	 * 留言的回复
	 */
	public void reply() {
		String id = getPara();
		
		setAttr("message", Message.dao.findById(id)); // 根据id获取留言
		setAttr("replys", messageService.getReplyByPid(id)); // 获取留言的回复信息
		
		render("reply.html");
	}
	
	/**
	 * 留言
	 */
	public void messageOrReply() {
		String verification = getPara("verification");
		String check = getSessionAttr("check");
		removeSessionAttr("check"); // 验证码在前台校验完毕，清除验证码的session
		
		keepModel(Message.class);
		Message message = getModel(Message.class);
		Integer pid = message.getInt(Message.PID);
		// 验证验证码是否正确
		if (check != null && verification != null && check.equals(verification.toLowerCase())) {
			if (null == pid || "".equals(pid)) {
				messageService.addMessage(message); // 前台留言
			} else {
				messageService.modifyReply(message); // 前台回复
			}
			renderText("提交成功，查看请自行刷新页面");
		} else {
			renderText("<span style='color:red;'>* 验证码错误</span>");
		}
	}
	
}
