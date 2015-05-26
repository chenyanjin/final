package com.jfinal.share.controller.admin;

import com.jfinal.ext.route.ControllerBind;
import com.jfinal.share.controller.BaseController;
import com.jfinal.share.core.Constant;
import com.jfinal.share.model.User;
import com.jfinal.share.service.admin.LoginLogService;
import com.jfinal.share.util.SqlParaUtils;

/**
 * loginLog 控制层
 * 
 * @author 風佑兲(375910297@qq.com)
 * @date 2014年12月5日 上午10:05:56
 */
@ControllerBind(controllerKey = "/admin/loginLog")
public class LoginLogController extends BaseController {
	private LoginLogService loginLogService = new LoginLogService();
	
	public void list() {
		keepModel(User.class);
		User user = getModel(User.class);
		String startTime = SqlParaUtils.startTime(getPara("startTime"), getPara("endTime"));
		String endTime = SqlParaUtils.endTime(getPara("startTime"), getPara("endTime"));
		int pageNumber = getParaToInt(Constant.DWZ_PAGE_NUMBER, 1);
		int pageSize = getParaToInt(Constant.DWZ_PAGE_SIZE, Constant.PAGESIZE);
		setAttr("datas", loginLogService.getLoginLogPage(pageNumber, pageSize, user, startTime, endTime));
		render("list.html");
	}

	
}
