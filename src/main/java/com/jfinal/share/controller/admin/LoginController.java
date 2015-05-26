package com.jfinal.share.controller.admin;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.subject.Subject;

import com.jfinal.aop.Before;
import com.jfinal.aop.ClearInterceptor;
import com.jfinal.aop.ClearLayer;
import com.jfinal.ext.route.ControllerBind;
import com.jfinal.log.Logger;
import com.jfinal.share.controller.BaseController;
import com.jfinal.share.core.Constant;
import com.jfinal.share.interceptor.ManagerInterceptor;
import com.jfinal.share.model.LoginLog;
import com.jfinal.share.model.User;
import com.jfinal.share.util.DateUtils;
import com.jfinal.share.util.ModelEnum;

/**
 * login 控制层
 * 
 * @author 風佑兲(375910297@qq.com)
 * @date 2014年11月30日 下午5:02:19
 */
@ControllerBind(controllerKey = "/admin/login")
@ClearInterceptor(ClearLayer.ALL)
public class LoginController extends BaseController {
	private static final Logger logger = Logger.getLogger(LoginController.class);

	public void index() {
		if (SecurityUtils.getSubject().isAuthenticated()) {
			redirect("../main"); // 登录状态，跳转主页面
		} else {
			render("../login.html"); // 未登录，跳转到登录页面
		}
	}

	/**
	 * 验证人员登录
	 */
	public void validateLogin() {
		String loginName = getPara("loginName", null);
		String loginPwd = getPara("loginPwd", null);
		String code = getPara("code", null);
		String check = getSessionAttr("check");

		if (null == loginName || null == loginPwd || null == code) {
			toJson(300, Constant.INCOMPLETE_LOGIN_INFO);
		} else if (check == null) {
			toJson(300, Constant.TIMEOUT_CAPTCHA_EXCEPTION);
		} else if (check != null && code != null && !check.equals(code.toLowerCase())) {
			toJson(300, Constant.INCORRECT_CAPTCHA_EXCEPTION);
		} else {
			removeSessionAttr("check"); // 验证码验证完毕，清楚验证码的session
			UsernamePasswordToken token = new UsernamePasswordToken(loginName, loginPwd, true, getRequest().getRemoteAddr());
			// 这里可以调用 subject 做判断
			Subject subject = SecurityUtils.getSubject();
			try {
				if (!subject.isAuthenticated()) {
					subject.login(token);
				}
				// 设置日期格式，获得当前时间
				String currentDate = DateUtils.getCurrentTime();
				
				User loginUser = (User) subject.getPrincipal();
				// 设置登陆时间，登陆ip，登陆状态
				loginUser.set(User.LAST_LOGIN_TIME, currentDate)
								.set(User.LAST_LOGIN_IP, getRequest().getRemoteAddr())
								.set(User.LOGIN_STATUS, ModelEnum.OnlineOrOffline.ONLINE.ordinal())
								.update();

				// 创建本次登陆日志
				LoginLog loginLog = new LoginLog();
				loginLog.set(LoginLog.USER_ID, loginUser.get(User.ID))
							  .set(LoginLog.LOGIN_TIME, currentDate)
							  .set(LoginLog.LOGIN_IP, getRequest().getRemoteAddr())
							  .save();
				
				toJson(200, Constant.LOGIN_SUCCESS);
			} catch (UnknownSessionException use) {
				subject = new Subject.Builder().buildSubject();
				subject.login(token);
				logger.error(Constant.UNKNOWN_SESSION_EXCEPTION);
				toJson(300, Constant.UNKNOWN_SESSION_EXCEPTION);
			} catch (UnknownAccountException e) {
				logger.error(Constant.UNKNOWN_ACCOUNT_EXCEPTION);
				toJson(300, Constant.UNKNOWN_ACCOUNT_EXCEPTION);
			} catch (IncorrectCredentialsException e) {
				toJson(300, Constant.INCORRECT_CREDENTIALS_EXCEPTION);
			} catch (LockedAccountException e) {
				toJson(300, Constant.LOCKED_ACCOUNT_EXCEPTION);
			} catch (ExcessiveAttemptsException e) {
				toJson(300, Constant.EXCESSIVE_ATTEMPTS_EXCEPTION);
			} catch (AuthenticationException e) {
				toJson(300, Constant.AUTHENTICATION_EXCEPTION);
			}
		}
	}

	/**
	 * 用户注销
	 */
	@Before(ManagerInterceptor.class)
	public void logout() {
		try {
			Subject subject = SecurityUtils.getSubject();
			Session session = subject.getSession();
			// 设置用户下线
			User loginUser = (User) session.getAttribute(Constant.SHIRO_LOGIN_USER);
			loginUser.set(User.LOGIN_STATUS, ModelEnum.OnlineOrOffline.OFFLINE.ordinal())
							.update();
			// 设置登录日志下线时间
			LoginLog loginLog = LoginLog.dao.searchByUserIdAndLoginTime(loginUser.getInt(User.ID), loginUser.getStr(User.LAST_LOGIN_TIME));
			loginLog.set(LoginLog.LOGOUT_TIME, DateUtils.getCurrentTime()).update();

			subject.logout(); // 销毁subject session会在WebSessionListener清除
			redirect("/index");
		} catch (AuthenticationException e) {
			toJson(300, Constant.LOGINOUT_AUTHENTICATION_EXCEPTION);
		}
	}

	/**
	 * 开始验证码代码
	 */
	private static final String chars = "23456789ABCDEFGHJKLMNPQRSTUVWXYZabcdefghjkmnpqrstuvwxyz";
	private static final int WIDTH = 150;
	private static final int HEIGHT = 50;

	public void jpg() {
		HttpServletResponse response = getResponse();
		HttpSession session = getSession();
		response.setContentType("image/jpeg");

		// 防止浏览器缓冲
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);

		BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
		Graphics g = image.getGraphics();
		char[] rands = getCode(4);
		drawBackground(g);
		drawRands(g, rands);
		g.dispose();
		try {
			ServletOutputStream out = response.getOutputStream();
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			ImageIO.write(image, "PNG", bos);
			byte[] buf = bos.toByteArray();
			response.setContentLength(buf.length);
			out.write(buf);
			bos.close();
			out.close();
			session.setAttribute("check", new String(rands).toLowerCase());
		} catch (Exception e) {
		}
		renderNull();
	}

	/**
	 * 产生随机数
	 * 
	 * @return
	 */
	private char[] getCode(int length) {
		char[] rands = new char[length];
		for (int i = 0; i < length; i++) {
			int rand = (int) (Math.random() * chars.length());
			rands[i] = chars.charAt(rand);
		}
		return rands;
	}

	/**
	 * 绘制背景
	 * 
	 * @param g
	 */
	private void drawBackground(Graphics g) {
		g.setColor(new Color(255, 255, 255));
		g.fillRect(0, 0, WIDTH, HEIGHT);
		Random random = new Random();
		int len = 0;
		while (len <= 5) {
			len = random.nextInt(15);
		}
		for (int i = 0; i < len; i++) {
			int x = (int) (random.nextInt(WIDTH));
			int y = (int) (random.nextInt(HEIGHT));
			int red = (int) (255 - random.nextInt(200));
			int green = (int) (255 - random.nextInt(200));
			int blue = (int) (255 - random.nextInt(200));
			g.setColor(new Color(red, green, blue));
			// g.drawLine(x, y, random.nextInt(WIDTH)-x,
			// random.nextInt(HEIGHT)-y);
			g.drawOval(x, y, 2, 2);
		}
	}

	/**
	 * 绘制验证码
	 * 
	 * @param g
	 * @param rands
	 */
	private void drawRands(Graphics g, char[] rands) {
		Random random = new Random();

		g.setFont(new Font("黑体", Font.ITALIC | Font.BOLD, 45));
		for (int i = 0; i < rands.length; i++) {
			int red = (int) (random.nextInt(255));
			int green = (int) (random.nextInt(255));
			int blue = (int) (random.nextInt(255));
			g.setColor(new Color(red, green, blue));
			g.drawString("" + rands[i], i * 40, 40);
		}
	}
}
