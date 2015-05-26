package com.jfinal.share.model;

import java.util.List;

import com.jfinal.ext.plugin.tablebind.TableBind;
import com.jfinal.plugin.activerecord.Model;

/**
 * 字典表
 * 
 * @author 風佑兲(375910297@qq.com)
 * @date 2014年11月29日 下午10:54:04
 */
@TableBind(tableName = "c_dict")
public class Dictionary extends Model<Dictionary> {

	private static final long serialVersionUID = -8965860744017004236L;
	public static final Dictionary dao = new Dictionary();

	public static final String TABLE_NAME = "c_dict";

	public static final String ID = "id";
	public static final String PID = "pid"; // 父id
	public static final String CODE = "code"; // 字典代码
	public static final String NAME = "name"; // 名称
	public static final String SORT = "sort"; // 排序
	public static final String CREATE_TIME = "create_time"; // 创建时间
	public static final String ENABLE = "enable"; // 停用 默认(可用):0   停用：1
	
	/**
	 * 查找所有字典
	 * 
	 * @return List<Dictionary>
	 */
	public List<Dictionary> searchAll() {
		String sql = "SELECT * FROM c_dict ORDER BY sort ASC";
		return find(sql);
	}
	
	/**
	 * 查找所有父字典（pid == null）
	 * 
	 * @return List<Dictionary>
	 */
	public List<Dictionary> searchParentAll() {
		String sql = "SELECT * FROM c_dict WHERE pid IS NULL ORDER BY sort ASC";
		return find(sql);
	}

	/**
	 * 查找所有子字典（pid != null）
	 * 
	 * @return List<Dictionary>
	 */
	public List<Dictionary> searchChildrenAll() {
		String sql = "SELECT * FROM c_dict WHERE pid IS NOT NULL ORDER BY sort ASC";
		return find(sql);
	}

	/**
	 * 根据code从字典查找数据
	 * 
	 * @param code 字典代码
	 * @return Dictionary
	 */
	public Dictionary searchByCode(String code) {
		String sql = "SELECT * FROM c_dict WHERE code = ?";
		return findFirst(sql, code);
	}
	
	/**
	 * 根据pid查找所有子数据
	 * 
	 * @param pid 父id
	 * @return List<Dictionary>
	 */
	public List<Dictionary> searchByPid(Integer pid) {
		String sql = "SELECT * FROM c_dict WHERE pid = ?";
		return find(sql, pid);
	}
	
	/**
	 * 根据父code查找子字典
	 * 
	 * @param code 父字典代码
	 * @return List<Dictionary>
	 */
	public List<Dictionary> searchChildrenByCode(String code) {
		String sql = "SELECT * FROM c_dict, (SELECT id c_id FROM c_dict WHERE pid IS NULL AND code = ?) c WHERE enable = '0' AND pid = c.c_id";
		return find(sql, code);
	}

}
