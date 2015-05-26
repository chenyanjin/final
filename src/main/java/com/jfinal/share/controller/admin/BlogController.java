package com.jfinal.share.controller.admin;

import java.io.File;

import javax.servlet.http.HttpServletRequest;

import com.jfinal.ext.render.DwzRender;
import com.jfinal.ext.route.ControllerBind;
import com.jfinal.share.controller.BaseController;
import com.jfinal.share.core.Constant;
import com.jfinal.share.model.Blog;
import com.jfinal.share.service.admin.BlogService;
import com.jfinal.share.service.admin.BlogTagService;
import com.jfinal.share.service.admin.DictionaryService;
import com.jfinal.share.util.SqlParaUtils;
import com.jfinal.upload.UploadFile;

/**
 * blog 控制层
 * 
 * @author 風佑兲(375910297@qq.com)
 * @date 2014年12月6日 下午10:53:24
 */
@ControllerBind(controllerKey = "/admin/blog")
public class BlogController extends BaseController{
	
	private BlogService blogService = new BlogService();
	private DictionaryService dictionaryService = new DictionaryService();
	private BlogTagService blogTagService = new BlogTagService();
	
	public void list() {
		keepModel(Blog.class);
		Blog blog = getModel(Blog.class);
		String startTime = SqlParaUtils.startTime(getPara("startTime"), getPara("endTime"));
		String endTime = SqlParaUtils.endTime(getPara("startTime"), getPara("endTime"));
		int pageNumber = getParaToInt(Constant.DWZ_PAGE_NUMBER, 1);
		int pageSize = getParaToInt(Constant.DWZ_PAGE_SIZE, Constant.PAGESIZE);
		setAttr("datas", blogService.getBlogPage(pageNumber, pageSize, blog, startTime, endTime));
		
		render("list.html");
	}
	
	/**
	 * 跳转到添加 或 编辑页面
	 */
	public void toModifyPage() {
		String id = getPara();
		setAttr("blog", null != id ? Blog.dao.findById(id) : "");
		setAttr("tagStr", null != id ? blogTagService.getBlogTag(Integer.valueOf(id)) : "");
		
		setAttr("tags", dictionaryService.getDictionaryByCode("tag")); // 传递标签给复选框
		
		render("form.html");
	}
	
	/**
	 * 添加 或 修改 blog
	 */
	public void modify() {
		Blog blog = getModel(Blog.class);
		String[] blogTags = getParaValues("tag_ids");
		
		if (blogService.modify(blog, blogTags)) {
			this.render(new DwzRender(blogService.getMessage(), "blogList", "closeCurrent"));
		} else {
			this.render(DwzRender.error(blogService.getMessage()));
		}
	}
	
	/**
	 * 编辑器上传
	 */
	public void xhEditorUpload(){
		//用于保存上传错误，暂时用不到
		String err = "";
		//获取当前目录
		HttpServletRequest request = getRequest();
		String basePath = request.getContextPath();
		
		//存储路径
		String path = getSession().getServletContext().getRealPath(Constant.UPLOAD_XHEDITOR_DIR);
		UploadFile file = getFile("filedata", path);
		
		//上传文件
		String type = file.getFileName().substring(file.getFileName().lastIndexOf(".")); // 获取文件的后缀
		String fileName = System.currentTimeMillis() + type; // 对文件重命名取得的文件名+后缀
		String dest = path + "/" + fileName;
		file.getFile().renameTo(new File(dest));
		//直接传回完整路径
		renderJson("{\"err\":\"" + err + "\",\"msg\":\"" + basePath + "/" + Constant.UPLOAD_XHEDITOR_DIR + "/" + fileName + "\"}");
	}
	
	/**
	 * 批量删除Blog
	 */
	public void delete() {
		String[] ids = getParaValues("ids");
		blogService.deletes(ids);
		render(DwzRender.refresh("blogList"));
	}
	
}
