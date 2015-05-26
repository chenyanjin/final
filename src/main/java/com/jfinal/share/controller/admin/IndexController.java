package com.jfinal.share.controller.admin;

import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

import com.jfinal.ext.route.ControllerBind;
import com.jfinal.share.controller.BaseController;
import com.jfinal.share.core.MenuBuilder;

/**
 * 系统后台
 * 
 * @author 風佑兲(375910297@qq.com)
 * @date 2014年12月2日 上午8:51:03
 */
@ControllerBind(controllerKey = "/admin")
public class IndexController extends BaseController {

	public void index() {
		if (SecurityUtils.getSubject().isAuthenticated()) {
			// 登录了，跳转主界面
			redirect("/main");
		} else {
			// 未登录，跳转登录界面
			redirect("/login");
		}
	}

	public void loading() {
		if (SecurityUtils.getSubject().isAuthenticated()) {
			// 登录了，跳转加载界面，再跳转到主页面
			render("loading.html");
		} else {
			// 未登录，跳转登录界面
			redirect("/login");
		}
	}

	/**
	 * 系统主菜单
	 */
	public void main() {
		// 获得系统菜单信息
		setAttr("parent", MenuBuilder.getMenuParent());
		setAttr("children", MenuBuilder.getMenuChildren());
		// 获得菜单权限
		Subject subject = SecurityUtils.getSubject();
		Map<Integer, Boolean> menu = new TreeMap<Integer, Boolean>();
		menu.putAll(MenuBuilder.getMenu());
		Set<Integer> keys = menu.keySet();
		for (Integer key : keys) {
			if (subject.isPermitted(String.valueOf(key))) {
				menu.put(key, true);
			}
		}
		setAttr("menu", MenuBuilder.converMenuStr(menu));

		render("index.html");
	}

}
