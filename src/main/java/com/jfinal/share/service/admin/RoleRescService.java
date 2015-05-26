package com.jfinal.share.service.admin;

import java.sql.SQLException;
import java.util.List;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.IAtom;
import com.jfinal.share.core.MenuBuilder;
import com.jfinal.share.model.Resc;
import com.jfinal.share.model.Role;
import com.jfinal.share.model.RoleResc;
import com.jfinal.share.util.DateUtils;
import com.jfinal.share.util.ModelEnum;

/**
 * RoleResc 业务逻辑层
 * 
 * @author 風佑兲 (375910297@.qq.com)
 * @date 2014年7月29日 下午12:51:19
 */
public class RoleRescService {
	private String message = "";

	public String getMessage() {
		return message;
	}

	/**
	 * 获得role列表
	 * 
	 * @return
	 */
	public List<Role> getRoleList() {
		return Role.dao.searchAll();
	}

	/**
	 * 获得resc列表
	 * 
	 * @return
	 */
	public List<Resc> getRescList() {
		return Resc.dao.searchAll();
	}

	/**
	 * 重新组装resc的ztree的json格式
	 * 
	 * @return
	 */
	public String getRescTree() {
		List<Resc> rescs = getRescList();
		StringBuilder sb = new StringBuilder();
		sb.append("[");

		for (Resc resc : rescs) {
			sb.append("{id:");
			sb.append(resc.getInt(Resc.ID));
			sb.append(",pid:");
			sb.append(resc.getInt(Resc.PID));
			sb.append(",name:\"");
			int disable = "1".equals(resc.getStr(Resc.ENABLE)) ? 1 : 0;
			if (ModelEnum.EnableOrDisable.DISABLE.ordinal() == disable) {
				sb.append("<span style='color:red'>");
				sb.append(resc.getStr(Resc.NAME));
				sb.append("</span>");
			} else {
				sb.append(resc.getStr(Resc.NAME));
			}
			sb.append("\"");
			if (ModelEnum.EnableOrDisable.DISABLE.ordinal() == disable) {
				sb.append(",chkDisabled:true");
			}
			if (resc.getInt(Resc.LEVEL) > 0) {
				sb.append(",click:\"openDialogToResc('/admin/roleresc/toModifyRescPage/" + resc.getInt(Resc.ID) + "');\"");
			}
			sb.append(",open:true");
			sb.append("}");
			sb.append(",");
		}

		sb.deleteCharAt(sb.length() - 1);
		sb.append("]");
		return sb.toString();
	}

	/**
	 * 用 - 组合role对应resc的id
	 * 
	 * @param roleId
	 * @return
	 */
	public String getRescByRoleId(int roleId) {
		List<RoleResc> roleRescList = RoleResc.dao.searchByRoleId(roleId);
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("-");
		for (RoleResc roleResc : roleRescList) {
			stringBuilder.append(roleResc.getInt(RoleResc.RESC_ID));
			stringBuilder.append("-");
		}
		return stringBuilder.toString();
	}

	/**
	 * 通过role_id保存以 - 分割的resc
	 * 
	 * @param roleId
	 * @param rescId
	 */
	public boolean saveRescByRoleId(String roleId, String rescId) {
		final String roleIds = roleId;
		final String rescIds = rescId;
		return Db.tx(new IAtom() {
			public boolean run() throws SQLException {
				try {
					RoleResc.dao.deleteByRoleId(roleIds);
					RoleResc roleResc = null;
					for (String resc : rescIds.split("-")) {
						if (null != resc && !"".equals(resc.trim())) {
							roleResc = new RoleResc();
							roleResc.set(RoleResc.ROLE_ID, roleIds)
										  .set(RoleResc.RESC_ID, resc)
										  .save();
						}
					}
					message = "权限绑定成功！";
					MenuBuilder.clear();
					return true;
				} catch (Exception e) {
					message = "权限绑定失败！";
					return false;
				}
			}
		});
	}

	/**
	 * 保存或更新resc
	 */
	public void modifyResc(Resc resc) {
		Integer pId = resc.getInt(Resc.PID);
		resc.set(Resc.LEVEL, (Resc.dao.findById(pId)).getInt(Resc.LEVEL) + 1);
		if (null == resc.get(Resc.ID)) {
			resc.set(Resc.CREATE_TIME, DateUtils.getCurrentTime())
				   .save();
		} else {
			resc.update();
		}
		// 有变更，清空菜单
		MenuBuilder.clear();
	}

	/**
	 * 保存或更新role
	 */
	public void modifyRole(Role role) {
		if (null == role.get(Role.ID)) {
			role.set(Role.CREATE_TIME, DateUtils.getCurrentTime())
				  .save();
		} else {
			role.update();
		}
	}

	/**
	 * 批量启用(停用)role
	 * 
	 * @param ids
	 */
	public void enableOrDisable(String[] ids) {
		int enableCount = 0;
		int disableCount = 0;
		for (String id : ids) {
			Role role = Role.dao.findById(id);
			int disable = "1".equals(role.getStr(Role.ENABLE)) ? 1 : 0 ;
			if (ModelEnum.EnableOrDisable.DISABLE.ordinal() == disable) {
				// 启用
				role.set(Role.ENABLE, ModelEnum.EnableOrDisable.ENABLE.ordinal()).update();
				enableCount ++;
			} else {
				// 停用
				role.set(Role.ENABLE, ModelEnum.EnableOrDisable.DISABLE.ordinal()).update();
				disableCount ++;
			}
		}
		message = "启用[" + enableCount + "]个角色</br>停用[" + disableCount + "]个角色";
	}

}
