package com.jfinal.share.listener;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.SessionListenerAdapter;

import com.jfinal.share.core.Constant;

/**
 * session过期处理
 * 
 * @author 風佑兲(375910297@qq.com)
 * @date 2014年11月30日 下午4:41:01
 */
public class WebSessionListener extends SessionListenerAdapter {

	/**
	 * seesion 结束时调用
	 */
	@Override
    public void onStop(Session session) {
		super.onExpiration(session);
		session.removeAttribute(Constant.SHIRO_LOGIN_USER); // session结束，移除用户
    }
	
	/**
	 * session过期处理
	 */
	@Override
	public void onExpiration(Session session) {
		super.onExpiration(session);
		session.removeAttribute(Constant.SHIRO_LOGIN_USER); // 移除旧的session
	}
	
}
