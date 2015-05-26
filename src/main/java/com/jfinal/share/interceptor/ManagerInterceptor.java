package com.jfinal.share.interceptor;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

import com.jfinal.aop.Interceptor;
import com.jfinal.core.ActionInvocation;
import com.jfinal.core.Controller;
import com.jfinal.share.core.Constant;
import com.jfinal.share.model.User;

/**
 * 简单的未登录拦截
 * 
 * @author 風佑兲(375910297@qq.com)
 * @date 2014年11月30日 下午4:38:01
 */
public class ManagerInterceptor implements Interceptor {

	public void intercept(ActionInvocation ai) {
		Controller controller = ai.getController();
		// 获取shiro中的session
		Subject subject = SecurityUtils.getSubject();
		User loginUser = (User) subject.getSession().getAttribute(Constant.SHIRO_LOGIN_USER);
		// session为空
		if (null == loginUser) {
			controller.redirect("/admin/login/index");
		} else {
			ai.invoke();// 注意 一定要执行此方法
		}
	}

}
