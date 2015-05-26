package com.jfinal.share.service.admin;

import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

import com.jfinal.plugin.activerecord.Page;
import com.jfinal.share.model.RoleUser;
import com.jfinal.share.model.User;
import com.jfinal.share.util.DateUtils;
import com.jfinal.share.util.MD5;
import com.jfinal.share.util.ModelEnum;

/**
 * User 业务逻辑层
 * 
 * @author 風佑兲(375910297@qq.com)
 * @date 2014年12月3日 下午9:27:03
 */
public class UserService {
	private String message = null;

	public String getMessage() {
		return message;
	}

	/**
	 * 用户分页列表
	 * 
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	public Page<User> getUserPage(int pageNumber, int pageSize, User user) {
		return User.dao.searchPaginate(pageNumber, pageSize, user);
	}

	/**
	 * 获得用户的权限以 - 连接字符串
	 * 
	 * @param userId
	 * @return
	 */
	public String getUserRole(int userId) {
		StringBuilder stringBuilder = new StringBuilder();
		List<RoleUser> roleUsers = RoleUser.dao.searchRoleUserByUserId(userId);
		stringBuilder.append("-");
		for (RoleUser roleUser : roleUsers) {
			stringBuilder.append(roleUser.getInt(RoleUser.ROLE_ID));
			stringBuilder.append("-");
		}
		return stringBuilder.toString();
	}

	/**
	 * 保存或更新用户
	 */
	public boolean modifyUser(User user, String[] roleIds) {
		boolean flag = true;
		Integer id = user.getInt(User.ID);
		
		if (null == id) {
			User checkUser = User.dao.searchByUsername(user.getStr(User.USERNAME));
			if (null == checkUser) {
				// 创建时间，删除状态，启用状态
				user.set(User.CREATE_TIME, DateUtils.getCurrentTime())
					   .set(User.DELETE_STATUS, ModelEnum.NormalOrDelete.NORMAL.ordinal())
					   .set(User.ENABLE, ModelEnum.EnableOrDisable.ENABLE.ordinal())
					   .save();
			} else {
				message = "用户名已经存在";
				flag = false;
			}
		} else {
			RoleUser.dao.deleteByUserId(id); // 删除用户角色信息
			// 更新时间
			user.set(User.UPDATE_TIME, DateUtils.getCurrentTime())
				   .update();
		}
		// 用户绑定角色
		if (null != roleIds && flag) {
			RoleUser roleUser = null;
			for (String roleId : roleIds) {
				roleUser = new RoleUser();
				roleUser.set(RoleUser.ROLE_ID, roleId)
							  .set(RoleUser.USER_ID, id)
							  .save();
			}
		}
		
		return flag;
	}
	
	/**
	 * 更新用户个人信息
	 */
	public boolean modifyInfo(User user, String[] password) {
		if (null != password[0]) {
			// 判断新密码与确认密码是否为空
			if (null != password[1] && null != password[2]) {
				Subject subject = SecurityUtils.getSubject();
				String curPwd = ((User) subject.getPrincipal()).getStr(User.PASSWORD); // 获取当前用户密码
				// 比较用户输入的的当前密码与数据库中的密码是否匹配
				if (curPwd.equals(MD5.getMD5ofStr(password[0]))) {
					// 判断新密码是否大于等于6位
					if (password[1].length() >= 6) {
						// 判断新密码与确认密码是否相同
						if (password[1] == password[2] || password[1].equals(password[2])) {
							user.set(User.PASSWORD, MD5.getMD5ofStr(password[1]));
							user.update();
							return true;
						} else {
							message = "新密码与确认密码必须相同!";
							return false;
						}
					} else {
						message = "新密码必须大于等于6位!";
						return false;
					}
				} else {
					message = "当前密码错误!";
					return false;
				}
			} else {
				message = "如若修改密码</br>请填写新密码与确认密码!";
				return false;
			}
		} else {
			user.update();
			return true;
		}
	}

	/**
	 * 批量删除用户
	 * 
	 * @param ids
	 */
	public void deleteUsers(String[] ids) {
		User.dao.deleteByIds(ids);
		message = "成功删除了[" + ids.length + "]个用户！";
	}

	/**
	 * 重置用户密码为六个0
	 * 
	 * @param ids
	 */
	public void resetPasswords(String[] ids) {
		User.dao.resetPasswords(ids);
		message = "重置[" + ids.length + "]个用户密码成功！</br>默认密码(6个0),即：000000";
	}
}
