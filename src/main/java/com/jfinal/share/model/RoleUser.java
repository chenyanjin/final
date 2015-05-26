package com.jfinal.share.model;

import java.util.List;

import com.jfinal.ext.plugin.tablebind.TableBind;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Model;

/**
 * 角色用户关联
 * 
 * @author 風佑兲(375910297@qq.com)
 * @date 2014年11月30日 上午11:05:36
 */
@TableBind(tableName = "u_role_user")
public class RoleUser extends Model<RoleUser> {

	private static final long serialVersionUID = 7273627915980003223L;
	public static final RoleUser dao = new RoleUser();

	public static final String TABLE_NAME = "u_role_user";

	public static final String ID = "id";
	public static final String ROLE_ID = "role_id"; // role_id
	public static final String USER_ID = "user_id"; // user_id
	
	/**
	 * 根据userid查找用户的权限
	 * 
	 * @param userId 用户id
	 * @return List<RoleUser>
	 */
	public List<RoleUser> searchRoleUserByUserId(int userId) {
		String sql = "SELECT * FROM u_role_user WHERE user_id = ?";
		return find(sql, userId);
	}

	/**
	 * 根据userid来删除role_user
	 * 
	 * @param userId 用户id
	 * @return boolean
	 */
	public boolean deleteByUserId(Integer userId) {
		String sql = "DELETE FROM u_role_user WHERE user_id = ?";
		return 0 == Db.update(sql, userId) ? true : false;
	}

	/**
	 * 根据roleId来删除role_user
	 * 
	 * @param roleId
	 * @return
	 */
	public boolean deleteByRoleId(String roleId) {
		String sql = "DELETE FROM u_role_user WHERE role_id = ?";
		return 0 == Db.update(sql, roleId) ? true : false;
	}

}
