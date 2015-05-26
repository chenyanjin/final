package com.jfinal.share.controller.admin;

import com.jfinal.ext.render.DwzRender;
import com.jfinal.ext.route.ControllerBind;
import com.jfinal.share.controller.BaseController;
import com.jfinal.share.model.Dictionary;
import com.jfinal.share.service.admin.DictionaryService;

/**
 * Dictionary 控制层
 * 
 * @author 風佑兲(375910297@qq.com)
 * @date 2014年12月4日 下午3:15:55
 */
@ControllerBind(controllerKey = "/admin/dictionary")
public class DictionaryController extends BaseController {
	private DictionaryService dictionaryService = new DictionaryService();

	/**
	 * 读取字典数据
	 */
	public void list() {
		setAttr("parent", dictionaryService.getParentList());
		setAttr("children", dictionaryService.getChildrenList());
		render("list.html");
	}

	/**
	 * 得到字典树形结构
	 */
	public void getTree() {
		renderText(dictionaryService.getTree());
	}

	/**
	 * 进入修改或添加Dictionary界面
	 */
	public void toModifyPage() {
		Integer id = getParaToInt();
		setAttr("dictionary", null != id ? Dictionary.dao.findById(id) : "");
		setAttr("parent", dictionaryService.getParentList());
		
		render("form.html");
	}
	
	/**
	 * 修改或保存Dictionary
	 */
	public void modify() {
		Dictionary dictionary = getModel(Dictionary.class);
		dictionaryService.modify(dictionary);
		render(DwzRender.closeCurrentAndRefresh("dictionaryList"));
	}

	/**
	 * 批量启用 - 停用 Dictionary
	 */
	public void enableOrdisable() {
		String[] ids = this.getParaValues("ids");
		dictionaryService.enableOrdisable(ids);
		render(DwzRender.success(dictionaryService.getMessage()));
	}

	/**
	 * 从tree批量启用 - 停用 Dictionary
	 */
	public void enableOrdisableByTree() {
		String[] ids = getPara("ids").split(",");
		dictionaryService.enableOrdisable(ids);
		renderText(dictionaryService.getMessage());
	}
	
}
