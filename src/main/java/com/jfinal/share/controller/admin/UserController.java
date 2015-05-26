package com.jfinal.share.controller.admin;

import com.jfinal.ext.render.DwzRender;
import com.jfinal.ext.route.ControllerBind;
import com.jfinal.share.controller.BaseController;
import com.jfinal.share.core.Constant;
import com.jfinal.share.model.User;
import com.jfinal.share.service.admin.RoleRescService;
import com.jfinal.share.service.admin.UserService;

/**
 * User 控制层
 * 
 * @author 風佑兲(375910297@qq.com)
 * @date 2014年12月3日 下午9:26:28
 */
@ControllerBind(controllerKey = "/admin/user")
public class UserController extends BaseController {
	private UserService userService = new UserService();
	private RoleRescService rolerescService = new RoleRescService();

	public void list() {
		keepModel(User.class);
		User user = getModel(User.class);
		int pageNumber = getParaToInt(Constant.DWZ_PAGE_NUMBER, 1);
		int pageSize = getParaToInt(Constant.DWZ_PAGE_SIZE, Constant.PAGESIZE);
		setAttr("datas", userService.getUserPage(pageNumber, pageSize, user));
		render("list.html");
	}

	/**
	 * 进入修改或添加user界面
	 */
	public void toModifyPage() {
		String id = getPara(0);
		String flag = getPara(1, null);
		
		setAttr("user", null != id ? User.dao.findById(id) : "");
		setAttr("roleStr", null != id ? userService.getUserRole(Integer.valueOf(id)) : "");
		
		setAttr("roleRescs", rolerescService.getRoleList());
		
		if (null == flag) {
			render("form.html");
		} else if ("modifyInfo".equals(flag)) {
			render("modifyInfo.html");
		}
	}

	/**
	 * 修改或保存user
	 */
	public void modifyUser() {
		User user = getModel(User.class);
		String[] roleIds = getParaValues("rids");
		
		if (userService.modifyUser(user, roleIds)) {
			render(DwzRender.closeCurrentAndRefresh("userList"));
		} else {
			render(DwzRender.error(userService.getMessage()));
		}
	}
	
	/**
	 * 修改个人信息
	 */
	public void modifyInfo() {
		User user = getModel(User.class);
		// 密码顺序：0、当前密码 1、新密码 2、确认密码
		String[] password = new String[]{getPara("currentPwd", null), getPara("newPwd", null), getPara("verifyPwd", null)};
		
		if (userService.modifyInfo(user, password)) {
			render(new DwzRender("修改成功", "modifyInfoDialog", "closeCurrent"));
		} else {
			render(DwzRender.error(userService.getMessage()));
		}
	}

	/**
	 * 批量删除用户
	 */
	public void deleteUser() {
		String[] ids = getParaValues("ids");
		userService.deleteUsers(ids);
		render(DwzRender.success(userService.getMessage()));
	}

	/**
	 * 重置用户密码
	 */
	public void resetPassword() {
		String[] ids = getParaValues("ids");
		userService.resetPasswords(ids);
		render(DwzRender.success(userService.getMessage()));
	}

}
