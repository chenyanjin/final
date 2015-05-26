package com.jfinal.share.service.admin;

import com.jfinal.plugin.activerecord.Page;
import com.jfinal.share.model.WebConfig;
import com.jfinal.share.util.DateUtils;

/**
 * config 业务逻辑层
 * 
 * @author 風佑兲(375910297@qq.com)
 * @date 2014年12月6日 下午7:45:22
 */
public class WebConfigService {
	private String message;

	public String getMessage() {
		return message;
	}

	/**
	 * 分页获取webConfig
	 * 
	 * @param pageNumber 当前页
	 * @param pageSize 每页显示条数
	 * @param webConfig 条件
	 * @return Page<WebConfig>
	 */
	public Page<WebConfig> getConfigPage(int pageNumber, int pageSize, WebConfig webConfig) {
		return WebConfig.dao.searchPaginate(pageNumber, pageSize, webConfig);
	}

	/**
	 * 更新 或 保存webConfig实例
	 * 
	 * @param webConfig webConfig实例
	 * @return boolean
	 */
	public boolean modify(WebConfig webConfig) {
		boolean flag = true;
		
		Number id = webConfig.getNumber(WebConfig.ID);
		if (null == id) {
			WebConfig checkWeConfig = WebConfig.dao.searchByK(webConfig.getStr(WebConfig.K));
			if (null == checkWeConfig) {
				// 设置创建时间
				webConfig.set(WebConfig.CREATE_TIME, DateUtils.getCurrentTime())
					  	  .save();
				message = "配置添加成功";
			} else {
				message = "键已存在!";
				flag = false;
			}
		} else {
			// 设置更新时间
			webConfig.set(WebConfig.UPDATE_TIME, DateUtils.getCurrentTime())
					  .update();
			message = "配置修改成功";
		}
		return flag;
	}

	/**
	 * 批量删除webConfig
	 * 
	 * @param ids 
	 * @return boolean
	 */
	public boolean Deletes(String[] ids) {
		return WebConfig.dao.deleteByIds(ids);
	}
	
}
