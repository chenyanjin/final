package com.jfinal.share.model;

import java.util.List;

import com.jfinal.ext.plugin.tablebind.TableBind;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Page;

/**
 * 网站配置
 * 
 * @author 風佑兲(375910297@qq.com)
 * @date 2014年11月29日 下午10:57:03
 */
@TableBind(tableName = "c_webconfig")
public class WebConfig extends Model<WebConfig> {

	private static final long serialVersionUID = -4888296493766000643L;
	public static final WebConfig dao = new WebConfig();

	public static final String TABLE_NAME = "c_webconfig";

	public static final String ID = "id";
	public static final String K = "k"; // key键
	public static final String VAL = "val"; // value值
	public static final String DES = "des"; // description 描述
	public static final String CREATE_TIME = "create_time"; // 创建时间
	public static final String UPDATE_TIME = "update_time"; // 更新时间
	public static final String IS_SYSTEM = "is_system"; // 是否系统配置 否：0 是：1
	
	/**
	 * 查找所有webConfig
	 * 
	 * @return List<WebConfig>
	 */
	public List<WebConfig> searchAll() {
		String sql = "SELECT * FROM c_webconfig";
		return find(sql);
	}
	
	/**
	 * 根据k查找webConfig
	 * 
	 * @param k 键
	 * @return List<WebConfig>
	 */
	public WebConfig searchByK(String k) {
		String sql = "SELECT * FROM c_webconfig WHERE k = ?";
		return findFirst(sql, k);
	}

	/**
	 *  根据isSystem查找所有webConfig
	 *  
	 * @param isSystem 是否系统配置
	 * @return List<WebConfig>
	 */
	public List<WebConfig> searchByIsSystem(int isSystem) {
		String sql = "SELECT * FROM c_webconfig WHERE is_system = ?";
		return find(sql, isSystem);
	}

	/**
	 * 分页获取webConfig
	 * 
	 * @param pageNumber 当前页
	 * @param pageSize 每页显示条数
	 * @param webConfig 条件
	 * @return Page<WebConfig>
	 */
	public Page<WebConfig> searchPaginate(int pageNumber, int pageSize, WebConfig webConfig) {
		String sql = "SELECT * ";
		
		StringBuffer stringBuffer = new StringBuffer("FROM c_webconfig WHERE 1=1 ");
		// 判断键是否为空，不为空添加条件
		if (null != webConfig.getStr(K) && !"".equals(webConfig.getStr(K))) {
			stringBuffer.append("AND ").append(K).append(" like '").append(webConfig.getStr(K)).append("%' "); // 键向后模糊查找
		}
		// 判断系统配置是否为空，不为空添加条件
		if (null != webConfig.getStr(IS_SYSTEM) && !"".equals(webConfig.getStr(IS_SYSTEM))) {
			stringBuffer.append("AND ").append(IS_SYSTEM).append(" = '").append(webConfig.getStr(IS_SYSTEM)).append("' "); // 系统配置向后模糊查找
		}
		stringBuffer.append("ORDER BY ").append(ID).append(" DESC");
		return paginate(pageNumber, pageSize, sql, stringBuffer.toString());
	}

	
	/**
	 * 根据ids批量删除配置
	 * 
	 * @param ids
	 * @return boolean
	 */
	public boolean deleteByIds(String[] ids) {
		StringBuffer stringBuffer = new StringBuffer("DELETE FROM c_webconfig WHERE ");
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

}
