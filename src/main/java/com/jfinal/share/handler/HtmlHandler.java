package com.jfinal.share.handler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jfinal.handler.Handler;

public class HtmlHandler extends Handler {
	@Override
	public void handle(String target, HttpServletRequest request, HttpServletResponse response, boolean[] isHandled) {
		// 将以.html结尾的url路径进行处理，截取.html后缀
		int index = target.toLowerCase().indexOf(".html");
		if (index != -1) {
			// 从url中截取.html
			target = target.substring(0, index) + target.substring(index + 5);
		}
		nextHandler.handle(target, request, response, isHandled);
	}
}
