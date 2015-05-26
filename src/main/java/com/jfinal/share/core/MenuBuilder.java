package com.jfinal.share.core;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.apache.shiro.SecurityUtils;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.share.model.User;

/**
 * 系统菜单构建器 为dwz专门定制 支持三层目录
 * 
 * @author chenle 2014-3-9 下午3:39:25
 */
public class MenuBuilder {
	/**
	 * 一级菜单
	 */
	private static List<Map<String, Object>> parent = new ArrayList<Map<String, Object>>();
	/**
	 * 二级菜单
	 */
	private static Map<Integer, List<Map<String, Object>>> children = new TreeMap<Integer, List<Map<String, Object>>>();
	/**
	 * 存放权限Map<RescId, boolean>
	 */
	private static Map<Integer, Boolean> menu = new TreeMap<Integer, Boolean>();

	/**
	 * 初始化数据
	 */
	private static void initFromDb() {
		if (parent.isEmpty() && children.isEmpty() && menu.isEmpty()) {
			List<Map<String, Object>> resc = null;

			User loginUser = (User) SecurityUtils.getSubject().getSession().getAttribute(Constant.SHIRO_LOGIN_USER);
			List<Record> rescList = null;
			if (!Constant.SYSTEM_ADMINISTRATOR.equals(loginUser.getStr(User.USERNAME))) {
				rescList = Db.find("SELECT * FROM u_resc WHERE level > 0 AND enable = '0' ORDER BY level ASC, sort ASC");
			} else {
				// 管理员无论菜单是否启用都可获取查看
				rescList = Db.find("SELECT * FROM u_resc WHERE level > 0 ORDER BY level ASC, sort ASC");
			}
			Integer level = 0;
			for (Record r : rescList) {
				level = r.getInt("level");
				if (1 == level) {
					parent.add(r.getColumns());
				} else {
					resc = children.get(level);
					if (null == resc) {
						resc = new ArrayList<Map<String, Object>>();
					}
					resc.add(r.getColumns());
					children.put(level, resc);
				}
				menu.put(r.getInt("id"), false);
			}
			rescList = null;
		}
	}

	/**
	 * 获得一级菜单
	 * 
	 * @return
	 */
	public static List<Map<String, Object>> getMenuParent() {
		initFromDb();
		return parent;
	}

	/**
	 * 获得一级菜单下子菜单 key是层级 val是菜单List
	 * 
	 * @return
	 */
	public static Map<Integer, List<Map<String, Object>>> getMenuChildren() {
		initFromDb();
		return children;
	}

	/**
	 * 清除数据，便于重新初始化数据，适用于数据库数据变化之后调用
	 */
	public static void clear() {
		parent.clear();;
		children.clear();
		menu.clear();
	}

	/**
	 * ***************** menu method *************
	 */

	/**
	 * 获得所有菜单的操作权限Map<rescId, false>
	 * 
	 * @return
	 */
	public static Map<Integer, Boolean> getMenu() {
		initFromDb();
		return menu;
	}

	/**
	 * 根据操作权限菜单为true的menu把可操作的菜单用 - 连接
	 * 
	 * @param menu
	 * @return
	 */
	public static String converMenuStr(Map<Integer, Boolean> menu) {
		StringBuilder sb = new StringBuilder();
		sb.append("-");

		Set<Integer> keys = menu.keySet();
		for (Integer key : keys) {
			if (menu.get(key)) {
				sb.append(key);
				sb.append("-");
			}
		}

		return sb.toString();
	}
}
