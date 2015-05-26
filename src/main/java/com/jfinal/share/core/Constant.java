package com.jfinal.share.core;


/**
 * 系统常量
 * 
 * @author 風佑兲(375910297@qq.com)
 * @date 2014年11月30日 下午4:05:58
 */
public class Constant {
	// 文件上传目录
	public static final String UPLOAD_IMAGE_DIR = "upload/images/"; // 图片文件
	public static final String UPLOAD_MEDIA_DIR = "upload/medias/"; // 媒体文件
	public static final String UPLOAD_FILE_DIR = "upload/files/"; // 其它文件
	public static final String UPLOAD_XHEDITOR_DIR = "upload/xhEditor"; // 编辑器上传文件路径

	// 缓存时间
	public static final int CACHE_TIME_MINI = 60 * 10; // 缓存时间 10分钟
	public static final int CACHE_TIME_MAX = 60 * 30; // 缓存时间 30分钟
	public static final long HTTP_CACHE_TIME = 2592000L; // Http缓存时间 一个月

	public static final int PAGESIZE = 20; // 每页默认显示数
	public static final String DWZ_PAGE_NUMBER = "pageNum"; // dwz page number
	public static final String DWZ_PAGE_SIZE = "numPerPage"; // dwz page size

	// Shiro default
	public static final String DEFAULT_AUTHENTICATION_QUERY = "SELECT * FROM user WHERE enable = '0'  AND username = ?";
	public static final String DEFAULT_USER_ROLES_QUERY = "SELECT role_id FROM u_role_user WHERE user_id = ?";
	public static final String DEFAULT_ADMIN_ROLES_QUERY = "SELECT role_id FROM u_role_user"; // 系统超级管理员默认获取全部角色
	public static final String DEFAULT_PERMISSIONS_QUERY = "SELECT resc_id FROM u_role_resc WHERE role_id = ?";
	public static final String DEFAULT_ADMIN_PERMISSIONS_QUERY = "SELECT id FROM u_resc"; // 系统超级管理员默认获取全部权限
	public static final boolean PERMISSIONS_LOOKUP_ENABLED = true; // 权限查看设置
	
	// 系统默认常量
	public static final String SYSTEM_ADMINISTRATOR = "admin"; // 管理员
	public static final String SHIRO_LOGIN_USER = "loginUser"; // shiro seesion 保存登录用户
	public static final String SHIRO_LOGIN_LOG = "loginLog"; // shiro seesion 保存登录日志
	
	// 菜单项
	public static final String MENU_PARENT = "parent"; // 一级菜单
	public static final String MENU_CHILDREN = "children"; // 二级及以下菜单
	public static final String MENU_PERMITTED = "permitted"; // 菜单对应权限

	// 登录异常
	public static final String INCOMPLETE_LOGIN_INFO = "登录信息填写不完整";
	public static final String TIMEOUT_CAPTCHA_EXCEPTION = "验证码超时!";
	public static final String INCORRECT_CAPTCHA_EXCEPTION = "验证码错误!";
	public static final String UNKNOWN_SESSION_EXCEPTION = "异常会话!";
	public static final String UNKNOWN_ACCOUNT_EXCEPTION = "账号错误!";
	public static final String INCORRECT_CREDENTIALS_EXCEPTION = "密码错误!";
	public static final String LOCKED_ACCOUNT_EXCEPTION = "账号已被锁定，请与系统管理员联系!";
	public static final String EXCESSIVE_ATTEMPTS_EXCEPTION = "用户登录超过限制数,请稍后再访问！";
	public static final String AUTHENTICATION_EXCEPTION = "您没有授权!";
	public static final String LOGIN_SUCCESS = "登录成功";
	public static final String LOGINOUT_AUTHENTICATION_EXCEPTION = "身份异常!";

}
