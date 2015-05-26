package com.jfinal.share.model;

import java.util.List;

import com.jfinal.ext.plugin.tablebind.TableBind;
import com.jfinal.plugin.activerecord.Model;

/**
 * 登录日志
 * 
 * @author 風佑兲(375910297@qq.com)
 * @date 2014年11月30日 上午10:36:12
 */
@TableBind(tableName = "u_login_log")
public class LoginLog extends Model<LoginLog> {

	private static final long serialVersionUID = -5810623927589890448L;
	public static final LoginLog dao = new LoginLog();

	public static final String TABLE_NAME = "u_login_log";

	public static final String ID = "id";
	public static final String USER_ID = "user_id"; // user_id
	public static final String LOGIN_TIME = "login_time"; // 登录时间
	public static final String LOGIN_IP = "login_ip"; // 登录ip
	public static final String LOGOUT_TIME = "logout_time"; // 退出时间
	
	/**
	 * 但会所有登陆日志
	 * 
	 * @return List<LoginLog>
	 */
	public List<LoginLog> searchAll() {
		String sql = "SELECT * FROM u_login_log";
		return find(sql);
	}
	
	/**
	 * 根据userId和loginTime查找登陆日志
	 * @param userId
	 * @param loginTime 登陆时间
	 * @return LoginLog
	 */
	public LoginLog searchByUserIdAndLoginTime(Integer userId, String loginTime) {
		String sql = "SELECT * FROM u_login_log WHERE user_id = ? AND login_time = ?";
		return findFirst(sql, userId, loginTime);
	}

}
