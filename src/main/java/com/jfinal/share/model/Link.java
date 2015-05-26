package com.jfinal.share.model;

import java.util.List;

import com.jfinal.ext.plugin.tablebind.TableBind;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Model;

/**
 * 链接
 * 
 * @author 風佑兲(375910297@qq.com)
 * @date 2014年11月29日 下午10:54:19
 */
@TableBind(tableName = "c_link")
public class Link extends Model<Link> {

	private static final long serialVersionUID = -5238531365030259865L;
	public static final Link dao = new Link();

	public static final String TABLE_NAME = "c_link";

	public static final String ID = "id"; // id
	public static final String TYPE = "type"; // 从数据字典获得
	public static final String NAME = "name"; // 名称
	public static final String URL = "url"; // 链接url
	public static final String IMG_PATH = "img_path"; // 链接图片路径
	public static final String SORT = "sort"; // 排序
	public static final String CREATE_TIME = "create_time"; // 添加时间
	public static final String UPDATE_TIME = "update_time"; // 更新时间
	public static final String ENABLE = "enable"; // 启用状态 启用：0 停用：1

	/**
	 * 根据链接类型查找链接
	 * 
	 * @param type 链接类型
	 * @return List<Link>
	 */
	public List<Link> searchByType(Integer type) {
		String sql = "SELECT * FROM c_link WHERE enable = '0' AND type = ? ORDER BY sort ASC";
		return find(sql, type);
	}
	
	/**
	 * 根据ids批量删除链接
	 * 
	 * @param ids
	 * @return boolean
	 */
	public boolean deleteByIds(String[] ids) {
		StringBuffer stringBuffer = new StringBuffer("DELETE FROM c_link WHERE ");
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
