package com.jfinal.share.service.admin;

import com.jfinal.plugin.activerecord.Page;
import com.jfinal.share.model.LoginLog;
import com.jfinal.share.model.User;

/**
 * loginLog 业务逻辑层
 * 
 * @author 風佑兲(375910297@qq.com)
 * @date 2014年12月5日 上午10:06:09
 */
public class LoginLogService {

	/**
	 * 分页获取loginLog
	 * (表关联查询的sql写在外面)
	 * 
	 * @param pageNumber 当前页
	 * @param pageSize 每页显示条数
	 * @param user 用户条件
	 * @param startTime 开始时间
	 * @param endTime  结束时间
	 * @return Page<LoginLog>
	 */
	public Page<LoginLog> getLoginLogPage(int pageNumber, int pageSize, User user, String startTime, String endTime) {
		// 构建select sql
		StringBuffer selectStringBuffer = new StringBuffer("SELECT ");
		selectStringBuffer.append("u.").append(User.USERNAME).append(", ")
									  .append("u.").append(User.NAME).append(", ")
									  .append("ll.").append(LoginLog.ID).append(", ")
									  .append("ll.").append(LoginLog.LOGIN_TIME).append(", ")
									  .append("ll.").append(LoginLog.LOGIN_IP).append(", ")
									  .append("ll.").append(LoginLog.LOGOUT_TIME).append(" ");
		
		// 构建from sql
		StringBuffer fromStringBuffer = new StringBuffer("FROM ");
		fromStringBuffer.append(LoginLog.TABLE_NAME).append(" ll ")
									.append("LEFT JOIN ").append(User.TABLE_NAME).append(" u ON (").append("ll.").append(LoginLog.USER_ID).append(" = u.").append(User.ID).append(") ")
									.append("WHERE 1=1 ");
		
		// 判断用户名是否为空，不为空添加条件
		if (null != user.getStr(User.USERNAME) && !"".equals(user.getStr(User.USERNAME))) {
			fromStringBuffer.append("AND u.").append(User.USERNAME).append(" like '").append(user.getStr(User.USERNAME)).append("%' ");
		}
		// 判断姓名是否为空，不为空添加条件
		if (null != user.getStr(User.NAME) && !"".equals(user.getStr(User.NAME))) {
			fromStringBuffer.append("AND u.").append(User.NAME).append(" like '").append(user.getStr(User.NAME)).append("%' ");
		}
		// 判断开始时间和结束时间都不为空则查找包含时间段，否则不包含时间段查找
		if (null != startTime && !"".equals(startTime) && null != endTime && !"".equals(endTime)) {
			fromStringBuffer.append("AND ll.").append(LoginLog.LOGIN_TIME).append(" BETWEEN '").append(startTime).append("' AND '").append(endTime).append("' ");
		}
		fromStringBuffer.append("ORDER BY ll.").append(LoginLog.ID).append(" DESC");
		
		return LoginLog.dao.paginate(pageNumber, pageSize, selectStringBuffer.toString(), fromStringBuffer.toString());
	}
	
}
