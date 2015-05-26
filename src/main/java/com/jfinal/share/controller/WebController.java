package com.jfinal.share.controller;

import com.jfinal.core.Controller;
import com.jfinal.share.util.StaticFactory;

public class WebController extends Controller{
	
	@Override
	public void render(String view) {
		setAttr("ctx", getRequest().getContextPath());
		setAttr("systemConfig", StaticFactory.getSystemConfigMap());
		/** header */
		setAttr("myProject", StaticFactory.getMyProjectList());
		/** footer */
		setAttr("friendlyLink", StaticFactory.getFriendlyLinkList());
		super.render(view);
	}
}
