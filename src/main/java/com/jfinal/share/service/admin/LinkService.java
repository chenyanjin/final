package com.jfinal.share.service.admin;

import java.io.File;

import com.jfinal.plugin.activerecord.Page;
import com.jfinal.share.core.Constant;
import com.jfinal.share.model.Dictionary;
import com.jfinal.share.model.Link;
import com.jfinal.share.util.DateUtils;
import com.jfinal.share.util.StaticFactory;
import com.jfinal.upload.UploadFile;

/**
 * link 业务逻辑层
 * 
 * @author 風佑兲(375910297@qq.com)
 * @date 2014年12月5日 下午2:45:55
 */
public class LinkService {
	private String message = null;

	public String getMessage() {
		return message;
	}

	/**
	 * 分页查找link
	 * 
	 * @param pageNumber 当前页
	 * @param pageSize 每页显示条数
	 * @param link link条件
	 * @return Page<Link>
	 */
	public Page<Link> getLinkPage(int pageNumber, int pageSize, Link link) {
		// 构建select sql
		StringBuffer selectStringBuffer = new StringBuffer("SELECT ");
		selectStringBuffer.append("l.").append(Link.ID).append(", ")
									  .append("l.").append(Link.NAME).append(", ")
									  .append("l.").append(Link.URL).append(", ")
									  .append("l.").append(Link.IMG_PATH).append(", ")
									  .append("l.").append(Link.SORT).append(", ")
									  .append("l.").append(Link.CREATE_TIME).append(", ")
									  .append("l.").append(Link.UPDATE_TIME).append(", ")
									  .append("l.").append(Link.ENABLE).append(", ")
									  .append("l.").append(Link.TYPE).append(", ")
									  .append("d.").append(Dictionary.NAME).append(" type_name").append(" ");
		
		// 构建from sql
		StringBuffer fromStringBuffer = new StringBuffer("FROM ");
		fromStringBuffer.append(Link.TABLE_NAME).append(" l ")
									.append("LEFT JOIN ").append(Dictionary.TABLE_NAME).append(" d ON(d.").append(Dictionary.ID).append(" = l.").append(Link.TYPE).append(") ")
									.append("WHERE 1=1 ");
		
		// 判断名称是否为空，不为空则添加条件
		if (null != link.getStr(Link.NAME) && !"".equals(link.getStr(Link.NAME))) {
			fromStringBuffer.append("AND l.").append(Link.NAME).append(" like '").append(link.getStr(Link.NAME)).append("%' ");
		}
		// 判断类型是否为空，不为空则添加条件
		if (null != link.getNumber(Link.TYPE) && !"".equals(link.getNumber(Link.TYPE))) {
			fromStringBuffer.append("AND l.").append(Link.TYPE).append(" = '").append(link.getNumber(Link.TYPE)).append("' ");
		}
		// 判断启用状态是否为空，不为空则添加条件
		if (null != link.getStr(Link.ENABLE) && !"".equals(link.getStr(Link.ENABLE))) {
			fromStringBuffer.append("AND l.").append(Link.ENABLE).append(" = '").append(link.getStr(Link.ENABLE)).append("' ");
		}
		fromStringBuffer.append("ORDER BY l.").append(Link.ID).append(" DESC");
		
		return Link.dao.paginate(pageNumber, pageSize, selectStringBuffer.toString(), fromStringBuffer.toString());
	}

	/**
	 * 更新 或 保存link实例
	 * 
	 * @param link link实例
	 * @param file 图片文件
	 * @param path 图片保存路径
	 * @return boolean
	 */
	public boolean modify(Link link, UploadFile file, String path) {
		boolean flag = false;
		
		if (null != file) {
			// 上传文件       获取文件的后缀
			String type = file.getFileName().substring(file.getFileName().lastIndexOf("."));
			// 对文件重命名取得的文件名+后缀
			String fileName = System.currentTimeMillis() + type;
			String dest = path + "/" + fileName;
			file.getFile().renameTo(new File(dest));
			link.set(Link.IMG_PATH, Constant.UPLOAD_IMAGE_DIR + "links/" + fileName);
		}
		String url = link.getStr(Link.URL);
		// 判断图片地址是否包含http，不包含则手动添加
		if (url.indexOf("http://")  == -1) {
			link.set(Link.URL, "http://" + url);
		}
		
		Number id = link.getNumber(Link.ID);
		if (null == id) {
			// 设置创建时间
			link.set(Link.CREATE_TIME, DateUtils.getCurrentTime())
				  .save();
			message = "链接添加成功";
			flag = true;
			
		} else {
			// 判断图片是否修改
			if (null == link.getStr(Link.IMG_PATH) || "".equals(link.getStr(Link.IMG_PATH))) {
				link.set(Link.IMG_PATH, Link.dao.findById(id).getStr(Link.IMG_PATH)); // 获取原图片路径
			}
			// 设置更新时间
			link.set(Link.UPDATE_TIME, DateUtils.getCurrentTime())
				  .update();
			message = "链接修改成功";
			flag = true;
		}
		
		// 添加 或 修改成功后清空静态工厂中的配置
		if (flag) {
			StaticFactory.clearMyProject();
			StaticFactory.clearFriendlyLink();
		}
		
		return flag;
	}
	
	/**
	 * 批量删除link
	 * 
	 * @param ids 
	 * @return boolean
	 */
	public boolean Deletes(String[] ids) {
		return Link.dao.deleteByIds(ids);
	}

}
