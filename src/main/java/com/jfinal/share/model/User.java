package com.jfinal.share.model;

import com.jfinal.ext.plugin.tablebind.TableBind;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.share.util.MD5;

/**
 * 用户
 * 
 * @author 風佑兲(375910297@qq.com)
 * @date 2014年11月29日 下午10:54:28
 */
@TableBind(tableName = "user")
public class User extends Model<User> {

	private static final long serialVersionUID = 3412441464329317748L;
	public static final User dao = new User();

	public static final String TABLE_NAME = "user";

	public static final String ID = "id";
	public static final String USERNAME = "username"; // 登录账号
	public static final String PASSWORD = "password"; // 登录密码
	public static final String NICKNAME = "nickname"; // 昵称
	public static final String NAME = "name"; // 姓名
	public static final String SEX = "sex"; // 性别 默认：0（男） 女：1
	public static final String BIRTHDAY = "birthday"; // 生日
	public static final String HEAD_PHOTO_PATH = "head_photo_path"; // 头像
	public static final String SIGNATURE = "signature"; // 个性签名
	public static final String WEBSITE = "website"; // 个人网站
	public static final String TYPE = "type"; // 用户类型 默认：0（普通用户） 管理员：1
	public static final String LOGIN_STATUS = "login_status"; // 是否登录状态 默认：0 在线：1
	public static final String CREATE_TIME = "create_time"; // 创建时间
	public static final String UPDATE_TIME = "update_time"; // 更新时间
	public static final String LAST_LOGIN_TIME = "last_login_time"; // 最后登录时间
	public static final String LAST_LOGIN_IP = "last_login_ip"; // 最后登录IP
	public static final String DELETE_STATUS = "delete_status"; // 删除状态 默认：0 删除：1
	public static final String ENABLE = "enable"; // 是否停用 默认：0 停用：1

	/**
	 * 根据user条件分页查找user用户
	 * 
	 * @param pageNumber 当前页
	 * @param pageSize 每页显示条数
	 * @param user 条件
	 * @return Page<User>
	 */
	public Page<User> searchPaginate(int pageNumber, int pageSize, User user) {
		String select = "SELECT * ";
		StringBuffer stringBuffer = new StringBuffer("FROM user WHERE delete_status = '0' ");
		// 判断用户名是否为空，不为空添加条件
		if (null != user.getStr(USERNAME) && !"".equals(user.getStr(USERNAME))) {
			stringBuffer.append("AND ").append(USERNAME).append(" like '").append(user.getStr(USERNAME)).append("%' "); // 用户名向后模糊查找
		}
		// 判断姓名是否为空，不为空添加条件
		if (null != user.getStr(NAME) && !"".equals(user.getStr(NAME))) {
			stringBuffer.append("AND ").append(NAME).append(" like '").append(user.getStr(NAME)).append("%' "); // 姓名向后模糊查找
		}
		// 判断性别是否为空，不为空添加条件
		if (null != user.getStr(SEX) && !"".equals(user.getStr(SEX))) {
			stringBuffer.append("AND ").append(SEX).append(" = '").append(user.getStr(SEX)).append("' ");
		}
		// 判断用户类型是否为空，不为空添加条件
		if (null != user.getStr(TYPE) && !"".equals(user.getStr(TYPE))) {
			stringBuffer.append("AND ").append(TYPE).append(" = '").append(user.getStr(TYPE)).append("' ");
		}
		// 判断在线状态是否为空，不为空添加条件
		if (null != user.getStr(LOGIN_STATUS) && !"".equals(user.getStr(LOGIN_STATUS))) {
			stringBuffer.append("AND ").append(LOGIN_STATUS).append(" = '").append(user.getStr(LOGIN_STATUS)).append("' ");
		}
		// 判断启用状态是否为空，不为空添加条件
		if (null != user.getStr(ENABLE) && !"".equals(user.getStr(ENABLE))) {
			stringBuffer.append("AND ").append(ENABLE).append(" = '").append(user.getStr(ENABLE)).append("' ");
		}
		
		stringBuffer.append("ORDER BY id DESC");
		
		return paginate(pageNumber, pageSize, select, stringBuffer.toString());
	}

	/**
	 * 根据username查找用户
	 * 
	 * @param username 用户名
	 * @return User
	 */
	public User searchByUsername(String username) {
		String sql = "SELECT * FROM user WHERE delete_status = '0' AND username = ?";
		return findFirst(sql, username);
	}

	/**
	 * 根据ids批量删除用户（实际：更新delete_status = '1'）
	 * 
	 * @param ids
	 * @return boolean
	 */
	public boolean deleteByIds(String[] ids) {
		StringBuffer stringBuffer = new StringBuffer("UPDATE user SET delete_status = '1',enable = '1' WHERE ");
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

	/**
	 * 根据ids批量重置用户密码
	 * 
	 * @param ids
	 * @return boolean
	 */
	public boolean resetPasswords(String[] ids) {
		StringBuffer stringBuffer = new StringBuffer("UPDATE user SET password = ? WHERE ");
		if ( null != ids && 1 == ids.length) {
			stringBuffer.append("id = ?");
			return 0 == Db.update(stringBuffer.toString(), MD5.getMD5ofStr("000000"), ids[0]) ? true : false;
		} else if(null != ids && ids.length > 1) {
			stringBuffer.append("id in('");
			for (String id : ids) {
				stringBuffer.append(id).append("','");
			}
			stringBuffer.delete(stringBuffer.length()-2, stringBuffer.length()); // 去掉最后一个,和'
			stringBuffer.append(")");
			return 0 == Db.update(stringBuffer.toString(), MD5.getMD5ofStr("000000")) ? true : false;
		}
		
		return false;
	}
}
