package com.jfinal.share.controller.admin;

import com.jfinal.ext.render.DwzRender;
import com.jfinal.ext.route.ControllerBind;
import com.jfinal.share.controller.BaseController;
import com.jfinal.share.core.Constant;
import com.jfinal.share.model.Link;
import com.jfinal.share.service.admin.DictionaryService;
import com.jfinal.share.service.admin.LinkService;
import com.jfinal.upload.UploadFile;

/**
 * link 控制层
 * 
 * @author 風佑兲(375910297@qq.com)
 * @date 2014年12月5日 下午2:45:42
 */
@ControllerBind(controllerKey = "/admin/link")
public class LinkController extends BaseController {
	private LinkService linkService = new LinkService();
	private DictionaryService dictionaryService = new DictionaryService();
	
	public void list() {
		keepModel(Link.class);
		Link link = getModel(Link.class);
		
		int pageNumber = getParaToInt(Constant.DWZ_PAGE_NUMBER, 1);
		int pageSize = getParaToInt(Constant.DWZ_PAGE_SIZE, Constant.PAGESIZE);
		setAttr("datas", linkService.getLinkPage(pageNumber, pageSize, link));
		setAttr("linkTypes", dictionaryService.getDictionaryByCode("linkType")); // 传递链接类型给下拉列表
		
		render("list.html");
	}
	
	/**
	 * 跳转到添加or编辑页面
	 */
	public void toModifyPage() {
		String id = getPara();
		Link link = Link.dao.findById(id);
		setAttr("link", null != link ? link : "");
		setAttr("linkTypes", dictionaryService.getDictionaryByCode("linkType")); // 传递链接类型给下拉列表
		
		render("form.html");
	}

	/**
	 * 更新 或 保存link实例
	 */
	public void modify() {
		//先获取getFile()，否则无法获得其他参数
		String path = getSession().getServletContext().getRealPath(Constant.UPLOAD_IMAGE_DIR + "links");
		UploadFile file = getFile("file", path);
		
		Link link = getModel(Link.class);
		
		if (linkService.modify(link, file, path)) {
			render(new DwzRender(linkService.getMessage(), "linkList", "closeCurrent"));
		} else {
			render(DwzRender.error(linkService.getMessage()));
		}
	}
	
	/**
	 * 批量删除link
	 */
	public void delete() {
		String[] ids = getParaValues("ids");
		linkService.Deletes(ids);
		render(DwzRender.refresh("linkList"));
	}

}
