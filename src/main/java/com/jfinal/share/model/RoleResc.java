package com.jfinal.share.model;

import java.util.List;

import com.jfinal.ext.plugin.tablebind.TableBind;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Model;

/**
 * 用户栏目权限关联
 * 
 * @author 風佑兲(375910297@qq.com)
 * @date 2014年11月30日 上午11:04:25
 */
@TableBind(tableName = "u_role_resc")
public class RoleResc extends Model<RoleResc> {
	
	private static final long serialVersionUID = -6208844824012637187L;
	public static final RoleResc dao = new RoleResc();
	
	public static final String TABLE_NAME = "u_role_resc";
	
	public static final String ID = "id";
	public static final String ROLE_ID = "role_id";			// role_id
	public static final String RESC_ID = "resc_id";			// resc_id
	
	/**
	 * 根据roleId查找角色权限
	 * 
	 * @param roleId 角色id
	 * @return List<RoleResc>
	 */
	public List<RoleResc> searchByRoleId(Integer roleId) {
		String sql = "SELECT * FROM u_role_resc WHERE role_id = ?";
		return find(sql, roleId);
	}

	/**
	 * 根据roleId删除角色权限关系
	 * 
	 * @param roleId 角色id
	 * @return boolean
	 */
	public boolean deleteByRoleId(String roleId) {
		String sql = "DELETE FROM u_role_resc WHERE role_id = ?";
		return 0 == Db.update(sql, roleId) ? true : false;
	}
	
}
