package com.jfinal.share.model;

import java.util.List;

import com.jfinal.ext.plugin.tablebind.TableBind;
import com.jfinal.plugin.activerecord.Model;

/**
 * 权限
 * 
 * @author 風佑兲(375910297@qq.com)
 * @date 2014年11月30日 上午10:42:50
 */
@TableBind(tableName = "u_resc")
public class Resc extends Model<Resc> {

	private static final long serialVersionUID = 672021427009956027L;
	public static final Resc dao = new Resc();

	public static final String TABLE_NAME = "u_resc";

	public static final String ID = "id";
	public static final String PID = "pid"; // 父id
	public static final String NAME = "name"; // 名称
	public static final String LEVEL = "level"; // 层级(不超过10层)
	public static final String SORT = "sort"; // 排序(不超过100)
	public static final String REMARK = "remark"; // 备注
	public static final String TYPE = "type"; // 权限类型 默认：0 后台权限：1
	public static final String FUNCTION_TYPE = "function_type"; // 功能类型 从数据字典获得
	public static final String URL = "url"; // URL
	public static final String REL = "rel"; // DWZ定制(rel)
	public static final String TARGET = "target";
	public static final String WIDTH = "width";
	public static final String HEIGHT = "height";
	public static final String CREATE_TIME = "create_time"; // 创建时间
	public static final String ENABLE = "enable"; // 是否停用 默认：0 停用：1

	/**
	 * 查找所有权限
	 * @return List<Resc>
	 */
	public List<Resc> searchAll() {
		String sql = "SELECT * FROM u_resc ORDER BY level ASC, sort ASC";
		return find(sql);
	}
}
