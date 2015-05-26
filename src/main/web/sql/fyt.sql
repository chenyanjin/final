/*
Navicat MySQL Data Transfer

Source Server         : ubuntu
Source Server Version : 50621
Source Host           : 192.168.1.63:3306
Source Database       : fyt

Target Server Type    : MYSQL
Target Server Version : 50621
File Encoding         : 65001

Date: 2014-12-17 19:05:13
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for blog
-- ----------------------------
DROP TABLE IF EXISTS `blog`;
CREATE TABLE `blog` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `title` varchar(255) DEFAULT NULL COMMENT '标题',
  `content` text COMMENT '内容',
  `user_id` int(11) DEFAULT NULL COMMENT 'user id',
  `support` int(11) DEFAULT NULL COMMENT '赞',
  `click_count` int(11) DEFAULT NULL COMMENT '点击数',
  `type` char(1) DEFAULT '0' COMMENT ' 博客类型 默认：0（原创） 收藏：1',
  `share_url` varchar(255) DEFAULT NULL COMMENT '收藏 url',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `delete_status` char(1) DEFAULT '0' COMMENT '是否删除 默认：0 删除：1',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of blog
-- ----------------------------
INSERT INTO `blog` VALUES ('1', '测试添加博客', '<pre class=\"brush: java\">public void xhEditorUpload(){\r\n		//用于保存上传错误，暂时用不到\r\n		String err = &quot;&quot;;\r\n		//获取当前目录\r\n		HttpServletRequest request = getRequest();\r\n		String basePath = request.getContextPath();\r\n		\r\n		//存储路径\r\n		String path = getSession().getServletContext().getRealPath(Constant.UPLOAD_XHEDITOR_DIR);\r\n		UploadFile file = getFile(&quot;filedata&quot;, path);\r\n		\r\n		//上传文件\r\n		String type = file.getFileName().substring(file.getFileName().lastIndexOf(&quot;.&quot;)); // 获取文件的后缀\r\n		String fileName = System.currentTimeMillis() + type; // 对文件重命名取得的文件名+后缀\r\n		String dest = path + &quot;/&quot; + fileName;\r\n		file.getFile().renameTo(new File(dest));\r\n		//直接传回完整路径\r\n		renderJson(&quot;{\\&quot;err\\&quot;:\\&quot;&quot; + err + &quot;\\&quot;,\\&quot;msg\\&quot;:\\&quot;&quot; + basePath + &quot;/&quot; + Constant.UPLOAD_XHEDITOR_DIR + &quot;/&quot; + fileName + &quot;\\&quot;}&quot;);\r\n	}</pre>', '2', '7', '31', '0', null, '2014-12-09 21:57:04', '2014-12-10 00:00:00', '0');
INSERT INTO `blog` VALUES ('2', '测试转载', '<p>添加博客内容<br /><pre class=\"brush: java\">public void xhEditorUpload(){\r\n		//用于保存上传错误，暂时用不到\r\n		String err = &quot;&quot;;\r\n		//获取当前目录\r\n		HttpServletRequest request = getRequest();\r\n		String basePath = request.getContextPath();\r\n		\r\n		//存储路径\r\n		String path = getSession().getServletContext().getRealPath(Constant.UPLOAD_XHEDITOR_DIR);\r\n		UploadFile file = getFile(&quot;filedata&quot;, path);\r\n		\r\n		//上传文件\r\n		String type = file.getFileName().substring(file.getFileName().lastIndexOf(&quot;.&quot;)); // 获取文件的后缀\r\n		String fileName = System.currentTimeMillis() + type; // 对文件重命名取得的文件名+后缀\r\n		String dest = path + &quot;/&quot; + fileName;\r\n		file.getFile().renameTo(new File(dest));\r\n		//直接传回完整路径\r\n		renderJson(&quot;{\\&quot;err\\&quot;:\\&quot;&quot; + err + &quot;\\&quot;,\\&quot;msg\\&quot;:\\&quot;&quot; + basePath + &quot;/&quot; + Constant.UPLOAD_XHEDITOR_DIR + &quot;/&quot; + fileName + &quot;\\&quot;}&quot;);\r\n	}</pre>&nbsp;查看代码。</p>', '2', '2', '10', '1', 'www.fengyoutian.com', '2014-12-13 19:42:20', null, '0');

-- ----------------------------
-- Table structure for blog_tag
-- ----------------------------
DROP TABLE IF EXISTS `blog_tag`;
CREATE TABLE `blog_tag` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `blog_id` int(11) DEFAULT NULL COMMENT 'blog id',
  `tag_id` int(11) DEFAULT NULL COMMENT 'tag_id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of blog_tag
-- ----------------------------
INSERT INTO `blog_tag` VALUES ('7', '1', '12');
INSERT INTO `blog_tag` VALUES ('8', '1', '13');
INSERT INTO `blog_tag` VALUES ('9', '2', '12');
INSERT INTO `blog_tag` VALUES ('10', '2', '13');

-- ----------------------------
-- Table structure for c_dict
-- ----------------------------
DROP TABLE IF EXISTS `c_dict`;
CREATE TABLE `c_dict` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '字典主键 无逻辑意义',
  `pid` int(11) DEFAULT NULL COMMENT '父id',
  `code` varchar(50) DEFAULT NULL COMMENT '字典代码',
  `name` varchar(50) DEFAULT NULL COMMENT '字典名称',
  `sort` int(3) DEFAULT NULL COMMENT '排序',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `enable` char(1) DEFAULT '0' COMMENT '停用 默认(可用):0   停用：1',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of c_dict
-- ----------------------------
INSERT INTO `c_dict` VALUES ('1', null, 'functionType', '操作功能', '1', '2014-12-02 21:18:33', '0');
INSERT INTO `c_dict` VALUES ('2', '1', 'operate', '操作', '1', '2014-12-02 21:18:33', '0');
INSERT INTO `c_dict` VALUES ('3', '1', 'create', '添加', '2', '2014-12-02 21:18:33', '0');
INSERT INTO `c_dict` VALUES ('4', '1', 'modify', '修改', '3', '2014-12-02 21:18:33', '0');
INSERT INTO `c_dict` VALUES ('5', '1', 'delete', '删除', '4', '2014-12-02 21:18:33', '0');
INSERT INTO `c_dict` VALUES ('6', '1', 'icon', '按钮', '5', '2014-12-02 21:18:33', '0');
INSERT INTO `c_dict` VALUES ('7', null, 'linkType', '链接类型', '2', '2014-12-02 21:18:33', '0');
INSERT INTO `c_dict` VALUES ('8', '7', 'friendlyLink', '友情链接', '1', '2014-12-02 21:18:33', '0');
INSERT INTO `c_dict` VALUES ('9', '7', 'myProject', '我的项目', '2', '2014-12-02 21:18:33', '0');
INSERT INTO `c_dict` VALUES ('10', '7', 'other', '其他', '3', '2014-12-02 21:18:33', '0');
INSERT INTO `c_dict` VALUES ('11', null, 'tag', '标签', '3', '2014-12-04 21:24:29', '0');
INSERT INTO `c_dict` VALUES ('12', '11', 'java', 'java', '1', '2014-12-05 20:12:20', '0');
INSERT INTO `c_dict` VALUES ('13', '11', 'jfinal', 'jfinal', '2', '2014-12-05 20:12:37', '0');
INSERT INTO `c_dict` VALUES ('14', '11', 'javascript', 'javascript', '3', '2014-12-05 20:14:52', '0');
INSERT INTO `c_dict` VALUES ('15', '11', 'mysql', 'mysql', '4', '2014-12-05 20:15:30', '0');
INSERT INTO `c_dict` VALUES ('16', '11', 'oracle', 'oracle', '5', '2014-12-05 20:15:53', '0');
INSERT INTO `c_dict` VALUES ('17', '11', 'ubuntu', 'ubuntu', '6', '2014-12-05 20:16:08', '0');

-- ----------------------------
-- Table structure for c_link
-- ----------------------------
DROP TABLE IF EXISTS `c_link`;
CREATE TABLE `c_link` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `type` int(11) DEFAULT NULL COMMENT '从数据字典获得',
  `name` varchar(50) DEFAULT '' COMMENT '名称',
  `url` varchar(150) DEFAULT '' COMMENT '链接',
  `img_path` varchar(150) DEFAULT '' COMMENT '图片路径',
  `sort` int(3) DEFAULT NULL COMMENT '排序',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `enable` char(1) DEFAULT '0' COMMENT '启用状态 启用：0 停用：1',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of c_link
-- ----------------------------
INSERT INTO `c_link` VALUES ('1', '8', 'JFinal', 'http://www.jfinal.com', '', '2', '2014-12-05 17:55:33', '2014-12-13 11:41:30', '0');
INSERT INTO `c_link` VALUES ('3', '8', '开源中国', 'http://www.oschina.net/', '', '1', '2014-12-05 20:27:33', '2014-12-13 11:41:26', '0');
INSERT INTO `c_link` VALUES ('4', '9', '实验预约系统', 'http://www.fengyoutian.com/syyy', '', '1', '2014-12-13 11:24:13', null, '0');

-- ----------------------------
-- Table structure for c_webconfig
-- ----------------------------
DROP TABLE IF EXISTS `c_webconfig`;
CREATE TABLE `c_webconfig` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `k` varchar(100) DEFAULT NULL COMMENT 'key 键',
  `val` varchar(150) DEFAULT NULL COMMENT 'value 值',
  `des` varchar(255) DEFAULT NULL COMMENT 'description 描述',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `is_system` char(1) DEFAULT '0' COMMENT '是否系统配置 否：0 是：1',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of c_webconfig
-- ----------------------------
INSERT INTO `c_webconfig` VALUES ('1', 'webName', '風佑兲个人博客', '网站名称', '2014-12-02 09:55:13', null, '1');
INSERT INTO `c_webconfig` VALUES ('2', 'webCopyright', '&copy; 2014.fengyoutian ', '网站底部copyright', '2014-12-02 09:55:13', null, '1');
INSERT INTO `c_webconfig` VALUES ('3', 'ICP', '琼ICP备14002015号', '网站ICP', '2014-12-02 09:55:13', null, '1');
INSERT INTO `c_webconfig` VALUES ('4', 'webVersion', '1.0.0', '网站版本', '2014-12-02 09:55:13', null, '1');

-- ----------------------------
-- Table structure for message
-- ----------------------------
DROP TABLE IF EXISTS `message`;
CREATE TABLE `message` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `pid` int(11) DEFAULT NULL,
  `blog_id` int(11) DEFAULT NULL COMMENT '博客id',
  `title` varchar(50) DEFAULT NULL,
  `name` varchar(20) DEFAULT NULL,
  `email` varchar(50) DEFAULT NULL,
  `content` text,
  `replyer` char(1) DEFAULT '0' COMMENT '回复者, 0:普通用户 1:留言者 2：管理员',
  `create_time` datetime DEFAULT NULL,
  `status` char(1) DEFAULT '0' COMMENT '0:未回复 1：已回复 2：有新回复',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=29 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of message
-- ----------------------------
INSERT INTO `message` VALUES ('1', null, null, 'wewe', 'wew', null, 'wewe', '0', '2014-12-17 12:15:41', '0');
INSERT INTO `message` VALUES ('2', null, null, '测试留言板', '博主', null, '两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字', '0', '2014-12-17 12:53:48', '0');
INSERT INTO `message` VALUES ('3', null, null, '测试留言板', '博主', null, '两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字', '0', '2014-12-17 12:54:05', '0');
INSERT INTO `message` VALUES ('4', null, null, '测试留言板', '博主', null, '两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字', '0', '2014-12-17 12:54:50', '0');
INSERT INTO `message` VALUES ('5', null, null, '测试留言板', '博主', null, '两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字', '0', '2014-12-17 12:56:03', '0');
INSERT INTO `message` VALUES ('6', null, null, '测试留言板', '博主', null, '两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字', '0', '2014-12-17 12:57:02', '2');
INSERT INTO `message` VALUES ('7', '6', null, '测试回复', '普通人员', null, '普通人员回复', '0', '2014-12-17 12:58:41', '0');
INSERT INTO `message` VALUES ('8', '6', null, '测试回复', '博主', null, '普通人员回复', '1', '2014-12-17 12:59:05', '0');
INSERT INTO `message` VALUES ('9', '6', null, '测试回复', '博主', null, '普通人员回复', '1', '2014-12-17 12:59:25', '0');
INSERT INTO `message` VALUES ('10', '6', null, '测试回复', '博主', null, '两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字两百字普通人员回复', '1', '2014-12-17 13:00:41', '0');
INSERT INTO `message` VALUES ('11', '6', null, '最新回复', '博主', null, '最新回复', '1', '2014-12-17 13:11:07', '0');
INSERT INTO `message` VALUES ('12', '6', null, '最新回复', '博主', null, '最新回复', '1', '2014-12-17 13:13:50', '0');
INSERT INTO `message` VALUES ('13', '6', null, '最新回复', '普通人员', null, '最新回复', '0', '2014-12-17 13:14:15', '0');
INSERT INTO `message` VALUES ('14', '6', null, '最新回复', '普通人员', null, '最新回复', '0', '2014-12-17 13:14:39', '0');
INSERT INTO `message` VALUES ('15', '6', null, '121', '12', null, '12', '0', '2014-12-17 13:15:07', '0');
INSERT INTO `message` VALUES ('16', '6', null, '121', '12', null, '12', '0', '2014-12-17 13:15:07', '0');
INSERT INTO `message` VALUES ('17', '6', null, '11', '11', null, '11', '0', '2014-12-17 13:17:52', '0');
INSERT INTO `message` VALUES ('18', '6', null, 'as', 'as', null, 'as', '0', '2014-12-17 13:19:26', '0');
INSERT INTO `message` VALUES ('19', null, '1', '测试博客回复', '博客', null, '测试博客回复', '0', '2014-12-17 13:34:51', '2');
INSERT INTO `message` VALUES ('20', null, '1', '测试博客回复', '博客', null, '测试博客回复', '0', '2014-12-17 13:37:06', '2');
INSERT INTO `message` VALUES ('21', null, '1', '测试博客回复', '博客', null, '测试博客回复', '0', '2014-12-17 13:39:09', '0');
INSERT INTO `message` VALUES ('22', '19', null, '测试博客回复', '博客', null, '测试博客回复', '1', '2014-12-17 13:43:32', '0');
INSERT INTO `message` VALUES ('23', '19', null, 'ceshi', 'ceshi', null, '测试', '0', '2014-12-17 13:43:47', '0');
INSERT INTO `message` VALUES ('24', null, '1', '测试博客留言', '博主', 'fengyoutian@126.com', 'renderJavascript(\"history.go(0);\");', '0', '2014-12-17 14:47:27', '0');
INSERT INTO `message` VALUES ('25', null, '1', '测试博客留言', '博主', 'fengyoutian@126.com', '			keepModel(Message.class);\n', '0', '2014-12-17 14:48:47', '0');
INSERT INTO `message` VALUES ('26', null, '1', '测试留言板', '博主', 'fengyoutian@126.com', 'location.reload()', '0', '2014-12-17 14:50:46', '0');
INSERT INTO `message` VALUES ('27', null, '1', 'renderText', 'renderText', null, 'renderText', '0', '2014-12-17 14:52:16', '0');
INSERT INTO `message` VALUES ('28', '20', null, '测试博客回复', '博主', 'fengyoutian@126.com', 'renderText', '0', '2014-12-17 14:54:55', '0');

-- ----------------------------
-- Table structure for u_login_log
-- ----------------------------
DROP TABLE IF EXISTS `u_login_log`;
CREATE TABLE `u_login_log` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT NULL COMMENT 'user id',
  `login_time` datetime DEFAULT NULL COMMENT '登录时间',
  `login_ip` varchar(15) DEFAULT NULL COMMENT '登录ip',
  `logout_time` datetime DEFAULT NULL COMMENT '退出时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=87 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of u_login_log
-- ----------------------------
INSERT INTO `u_login_log` VALUES ('1', '1', '2014-12-02 09:13:20', '127.0.0.1', null);
INSERT INTO `u_login_log` VALUES ('2', '1', '2014-12-02 09:59:52', '127.0.0.1', null);
INSERT INTO `u_login_log` VALUES ('3', '1', '2014-12-02 10:13:46', '127.0.0.1', null);
INSERT INTO `u_login_log` VALUES ('4', '1', '2014-12-02 12:15:22', '127.0.0.1', null);
INSERT INTO `u_login_log` VALUES ('5', '1', '2014-12-02 12:23:32', '127.0.0.1', '2014-12-02 12:24:25');
INSERT INTO `u_login_log` VALUES ('6', '1', '2014-12-02 12:31:27', '127.0.0.1', '2014-12-02 12:31:35');
INSERT INTO `u_login_log` VALUES ('7', '1', '2014-12-02 21:25:07', '127.0.0.1', null);
INSERT INTO `u_login_log` VALUES ('8', '1', '2014-12-02 21:30:43', '127.0.0.1', null);
INSERT INTO `u_login_log` VALUES ('9', '1', '2014-12-02 21:32:33', '127.0.0.1', null);
INSERT INTO `u_login_log` VALUES ('10', '1', '2014-12-02 21:36:31', '127.0.0.1', null);
INSERT INTO `u_login_log` VALUES ('11', '1', '2014-12-02 21:37:49', '127.0.0.1', null);
INSERT INTO `u_login_log` VALUES ('12', '1', '2014-12-04 20:17:11', '127.0.0.1', null);
INSERT INTO `u_login_log` VALUES ('13', '1', '2014-12-04 20:48:31', '127.0.0.1', null);
INSERT INTO `u_login_log` VALUES ('14', '1', '2014-12-04 21:17:18', '127.0.0.1', '2014-12-04 22:17:36');
INSERT INTO `u_login_log` VALUES ('15', '2', '2014-12-04 22:17:44', '127.0.0.1', '2014-12-04 22:17:50');
INSERT INTO `u_login_log` VALUES ('16', '1', '2014-12-04 22:17:58', '127.0.0.1', '2014-12-04 22:18:29');
INSERT INTO `u_login_log` VALUES ('17', '2', '2014-12-04 22:18:41', '127.0.0.1', '2014-12-04 22:18:53');
INSERT INTO `u_login_log` VALUES ('18', '1', '2014-12-04 22:18:58', '127.0.0.1', '2014-12-04 22:20:02');
INSERT INTO `u_login_log` VALUES ('19', '1', '2014-12-04 22:20:07', '127.0.0.1', '2014-12-04 22:20:18');
INSERT INTO `u_login_log` VALUES ('20', '2', '2014-12-04 22:20:25', '127.0.0.1', '2014-12-04 22:20:55');
INSERT INTO `u_login_log` VALUES ('21', '2', '2014-12-04 22:21:02', '127.0.0.1', '2014-12-04 22:21:05');
INSERT INTO `u_login_log` VALUES ('22', '1', '2014-12-04 22:45:28', '127.0.0.1', null);
INSERT INTO `u_login_log` VALUES ('23', '1', '2014-12-04 22:56:12', '127.0.0.1', '2014-12-04 22:56:23');
INSERT INTO `u_login_log` VALUES ('24', '1', '2014-12-04 22:56:27', '127.0.0.1', '2014-12-04 22:56:30');
INSERT INTO `u_login_log` VALUES ('25', '2', '2014-12-04 22:56:37', '127.0.0.1', '2014-12-04 22:59:00');
INSERT INTO `u_login_log` VALUES ('26', '1', '2014-12-04 22:59:05', '127.0.0.1', '2014-12-04 22:59:32');
INSERT INTO `u_login_log` VALUES ('27', '2', '2014-12-04 22:59:38', '127.0.0.1', '2014-12-04 23:04:16');
INSERT INTO `u_login_log` VALUES ('28', '1', '2014-12-05 11:35:11', '127.0.0.1', '2014-12-05 11:37:43');
INSERT INTO `u_login_log` VALUES ('29', '1', '2014-12-05 11:37:46', '127.0.0.1', null);
INSERT INTO `u_login_log` VALUES ('30', '1', '2014-12-05 11:38:07', '127.0.0.1', null);
INSERT INTO `u_login_log` VALUES ('31', '1', '2014-12-05 12:16:07', '127.0.0.1', null);
INSERT INTO `u_login_log` VALUES ('32', '1', '2014-12-05 12:22:09', '127.0.0.1', null);
INSERT INTO `u_login_log` VALUES ('33', '2', '2014-12-05 12:27:56', '127.0.0.1', '2014-12-05 12:28:05');
INSERT INTO `u_login_log` VALUES ('34', '1', '2014-12-05 12:28:10', '127.0.0.1', '2014-12-05 12:41:44');
INSERT INTO `u_login_log` VALUES ('35', '2', '2014-12-05 12:41:52', '127.0.0.1', '2014-12-05 12:53:24');
INSERT INTO `u_login_log` VALUES ('36', '2', '2014-12-05 12:53:32', '127.0.0.1', '2014-12-05 12:53:55');
INSERT INTO `u_login_log` VALUES ('37', '2', '2014-12-05 12:54:10', '127.0.0.1', '2014-12-05 12:55:18');
INSERT INTO `u_login_log` VALUES ('38', '2', '2014-12-05 12:57:08', '127.0.0.1', '2014-12-05 12:59:38');
INSERT INTO `u_login_log` VALUES ('39', '1', '2014-12-05 12:59:55', '127.0.0.1', null);
INSERT INTO `u_login_log` VALUES ('40', '1', '2014-12-05 13:13:17', '127.0.0.1', '2014-12-05 13:14:40');
INSERT INTO `u_login_log` VALUES ('41', '2', '2014-12-05 13:14:46', '127.0.0.1', null);
INSERT INTO `u_login_log` VALUES ('42', '2', '2014-12-05 13:16:19', '127.0.0.1', '2014-12-05 13:19:07');
INSERT INTO `u_login_log` VALUES ('43', '1', '2014-12-05 17:24:15', '127.0.0.1', null);
INSERT INTO `u_login_log` VALUES ('44', '1', '2014-12-05 18:01:45', '127.0.0.1', null);
INSERT INTO `u_login_log` VALUES ('45', '1', '2014-12-05 19:52:51', '127.0.0.1', '2014-12-05 20:02:50');
INSERT INTO `u_login_log` VALUES ('46', '1', '2014-12-05 20:02:59', '127.0.0.1', '2014-12-05 20:36:14');
INSERT INTO `u_login_log` VALUES ('47', '2', '2014-12-05 20:36:22', '127.0.0.1', '2014-12-05 20:36:45');
INSERT INTO `u_login_log` VALUES ('48', '1', '2014-12-05 20:36:50', '127.0.0.1', '2014-12-05 20:45:06');
INSERT INTO `u_login_log` VALUES ('49', '2', '2014-12-05 20:45:11', '127.0.0.1', '2014-12-05 20:45:47');
INSERT INTO `u_login_log` VALUES ('50', '2', '2014-12-05 20:45:53', '127.0.0.1', null);
INSERT INTO `u_login_log` VALUES ('51', '1', '2014-12-05 20:54:04', '127.0.0.1', null);
INSERT INTO `u_login_log` VALUES ('52', '1', '2014-12-05 21:01:24', '127.0.0.1', null);
INSERT INTO `u_login_log` VALUES ('53', '1', '2014-12-05 21:15:58', '127.0.0.1', null);
INSERT INTO `u_login_log` VALUES ('54', '1', '2014-12-06 19:35:02', '127.0.0.1', null);
INSERT INTO `u_login_log` VALUES ('55', '1', '2014-12-06 20:22:19', '127.0.0.1', '2014-12-06 20:23:31');
INSERT INTO `u_login_log` VALUES ('56', '1', '2014-12-06 20:23:48', '127.0.0.1', null);
INSERT INTO `u_login_log` VALUES ('57', '1', '2014-12-06 20:42:48', '127.0.0.1', null);
INSERT INTO `u_login_log` VALUES ('58', '1', '2014-12-06 20:50:40', '127.0.0.1', null);
INSERT INTO `u_login_log` VALUES ('59', '1', '2014-12-06 21:07:26', '127.0.0.1', null);
INSERT INTO `u_login_log` VALUES ('60', '1', '2014-12-06 21:11:36', '127.0.0.1', null);
INSERT INTO `u_login_log` VALUES ('61', '1', '2014-12-09 20:43:28', '127.0.0.1', '2014-12-09 21:08:49');
INSERT INTO `u_login_log` VALUES ('62', '1', '2014-12-09 20:46:29', '192.168.1.63', '2014-12-09 20:47:06');
INSERT INTO `u_login_log` VALUES ('63', '1', '2014-12-09 21:08:54', '127.0.0.1', '2014-12-09 21:10:44');
INSERT INTO `u_login_log` VALUES ('64', '1', '2014-12-09 21:11:16', '127.0.0.1', null);
INSERT INTO `u_login_log` VALUES ('65', '1', '2014-12-09 21:23:48', '127.0.0.1', '2014-12-09 21:27:06');
INSERT INTO `u_login_log` VALUES ('66', '1', '2014-12-09 21:27:11', '127.0.0.1', null);
INSERT INTO `u_login_log` VALUES ('67', '1', '2014-12-09 21:27:28', '127.0.0.1', null);
INSERT INTO `u_login_log` VALUES ('68', '1', '2014-12-09 21:28:39', '127.0.0.1', '2014-12-09 21:33:11');
INSERT INTO `u_login_log` VALUES ('69', '2', '2014-12-09 21:33:19', '127.0.0.1', null);
INSERT INTO `u_login_log` VALUES ('70', '1', '2014-12-09 21:49:55', '192.168.1.63', null);
INSERT INTO `u_login_log` VALUES ('71', '1', '2014-12-09 22:09:37', '127.0.0.1', null);
INSERT INTO `u_login_log` VALUES ('72', '1', '2014-12-09 22:55:15', '127.0.0.1', null);
INSERT INTO `u_login_log` VALUES ('73', '1', '2014-12-10 10:30:26', '127.0.0.1', null);
INSERT INTO `u_login_log` VALUES ('74', '1', '2014-12-10 11:19:48', '127.0.0.1', null);
INSERT INTO `u_login_log` VALUES ('75', '1', '2014-12-10 12:08:32', '127.0.0.1', null);
INSERT INTO `u_login_log` VALUES ('76', '1', '2014-12-12 20:45:48', '127.0.0.1', null);
INSERT INTO `u_login_log` VALUES ('77', '2', '2014-12-12 20:47:31', '192.168.1.63', '2014-12-12 21:05:04');
INSERT INTO `u_login_log` VALUES ('78', '1', '2014-12-12 22:08:53', '127.0.0.1', '2014-12-12 22:09:02');
INSERT INTO `u_login_log` VALUES ('79', '1', '2014-12-13 11:22:52', '127.0.0.1', null);
INSERT INTO `u_login_log` VALUES ('80', '1', '2014-12-13 11:40:48', '127.0.0.1', null);
INSERT INTO `u_login_log` VALUES ('81', '2', '2014-12-13 16:31:07', '127.0.0.1', '2014-12-13 16:31:21');
INSERT INTO `u_login_log` VALUES ('82', '1', '2014-12-13 19:39:07', '127.0.0.1', '2014-12-13 19:39:24');
INSERT INTO `u_login_log` VALUES ('83', '2', '2014-12-13 19:40:42', '127.0.0.1', null);
INSERT INTO `u_login_log` VALUES ('84', '1', '2014-12-16 17:16:05', '127.0.0.1', null);
INSERT INTO `u_login_log` VALUES ('85', '1', '2014-12-16 17:52:45', '127.0.0.1', null);
INSERT INTO `u_login_log` VALUES ('86', '1', '2014-12-16 17:54:45', '127.0.0.1', null);

-- ----------------------------
-- Table structure for u_resc
-- ----------------------------
DROP TABLE IF EXISTS `u_resc`;
CREATE TABLE `u_resc` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `pid` int(11) DEFAULT NULL COMMENT '父id',
  `name` varchar(50) DEFAULT NULL COMMENT '名称',
  `level` int(1) DEFAULT NULL COMMENT '层级(不超过10层)',
  `sort` int(2) DEFAULT NULL COMMENT '排序(不超过100)',
  `remark` varchar(100) DEFAULT NULL COMMENT '备注',
  `type` char(1) DEFAULT '0' COMMENT '权限类型 默认：0 后台权限：1',
  `function_type` int(11) DEFAULT '0' COMMENT '功能类型 从数据字典获得',
  `url` varchar(150) DEFAULT NULL COMMENT 'URL',
  `rel` varchar(50) DEFAULT NULL COMMENT 'DWZ定制',
  `target` varchar(10) DEFAULT NULL,
  `width` varchar(4) DEFAULT NULL,
  `height` varchar(4) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `enable` char(1) DEFAULT '0' COMMENT '是否停用 默认：0 停用：1',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of u_resc
-- ----------------------------
INSERT INTO `u_resc` VALUES ('1', '-1', '系统操作菜单', '0', '1', null, '1', '2', '#', '#', '#', null, null, '2014-12-02 12:40:02', '0');
INSERT INTO `u_resc` VALUES ('2', '2', '系统管理', '1', '2', '系统基本配置信息', '1', '2', '#', '#', '#', null, null, '2014-12-02 12:40:02', '0');
INSERT INTO `u_resc` VALUES ('3', '2', '用户信息配置', '2', '1', null, '1', '2', '#', '#', '#', null, null, '2014-12-02 12:40:02', '0');
INSERT INTO `u_resc` VALUES ('4', '3', '用户管理', '3', '1', null, '1', '2', 'admin/user/list', 'userList', 'navTab', null, null, '2014-12-02 12:40:02', '0');
INSERT INTO `u_resc` VALUES ('5', '3', '角色权限管理', '3', '2', null, '1', '2', 'admin/roleresc/list', 'rolerescList', 'navTab', null, null, '2014-12-02 12:40:02', '0');
INSERT INTO `u_resc` VALUES ('6', '2', '数据字典', '2', '4', null, '1', '2', 'admin/dictionary/list', 'dictionaryList', 'navTab', null, null, '2014-12-02 12:40:02', '0');
INSERT INTO `u_resc` VALUES ('7', '3', '登陆日志', '3', '3', '登陆日志', '1', '2', 'admin/loginLog/list', 'loginLogList', 'navTab', null, null, '2014-12-05 11:37:29', '0');
INSERT INTO `u_resc` VALUES ('8', '10', '链接管理', '2', '3', '链接管理', '1', '2', 'admin/link/list', 'linkList', 'navTab', null, null, '2014-12-05 17:25:44', '0');
INSERT INTO `u_resc` VALUES ('9', '2', '系统配置', '2', '3', null, '1', '2', 'admin/webConfig/list', 'webConfigList', 'navTab', null, null, '2014-12-06 19:39:52', '0');
INSERT INTO `u_resc` VALUES ('10', '1', '网站管理', '1', '1', '网站管理', '1', '2', '#', '#', '#', null, null, '2014-12-09 20:58:46', '0');
INSERT INTO `u_resc` VALUES ('11', '10', '博客管理', '2', '1', '博客管理', '1', '2', 'admin/blog/list', 'blogList', 'navTab', null, null, '2014-12-09 21:07:53', '0');
INSERT INTO `u_resc` VALUES ('12', '10', '留言管理', '2', '2', '留言管理', '1', '2', 'admin/message/list', 'messageList', 'navTab', null, null, '2014-12-09 21:07:53', '0');

-- ----------------------------
-- Table structure for u_role
-- ----------------------------
DROP TABLE IF EXISTS `u_role`;
CREATE TABLE `u_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `name` varchar(50) DEFAULT NULL COMMENT '名称',
  `type` char(1) DEFAULT '0' COMMENT ' 角色类型 默认：0 后台角色：1',
  `remark` varchar(100) DEFAULT NULL COMMENT '备注',
  `sort` int(2) DEFAULT NULL COMMENT '排序(不超过100)',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `enable` char(1) DEFAULT '0' COMMENT '是否停用 默认：0 停用：1',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of u_role
-- ----------------------------
INSERT INTO `u_role` VALUES ('1', '超级管理员', '1', '系统管理员', '1', '2014-12-02 12:37:48', '1');
INSERT INTO `u_role` VALUES ('2', '管理员', '1', '普通管理员', '2', '2014-12-04 21:45:31', '0');

-- ----------------------------
-- Table structure for u_role_resc
-- ----------------------------
DROP TABLE IF EXISTS `u_role_resc`;
CREATE TABLE `u_role_resc` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `role_id` int(11) DEFAULT NULL COMMENT 'role id',
  `resc_id` int(11) DEFAULT NULL COMMENT 'resc_id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=33 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of u_role_resc
-- ----------------------------
INSERT INTO `u_role_resc` VALUES ('22', '2', '1');
INSERT INTO `u_role_resc` VALUES ('23', '2', '10');
INSERT INTO `u_role_resc` VALUES ('24', '2', '11');
INSERT INTO `u_role_resc` VALUES ('25', '2', '8');
INSERT INTO `u_role_resc` VALUES ('26', '2', '2');
INSERT INTO `u_role_resc` VALUES ('27', '2', '3');
INSERT INTO `u_role_resc` VALUES ('28', '2', '4');
INSERT INTO `u_role_resc` VALUES ('29', '2', '5');
INSERT INTO `u_role_resc` VALUES ('30', '2', '7');
INSERT INTO `u_role_resc` VALUES ('31', '2', '9');
INSERT INTO `u_role_resc` VALUES ('32', '2', '6');

-- ----------------------------
-- Table structure for u_role_user
-- ----------------------------
DROP TABLE IF EXISTS `u_role_user`;
CREATE TABLE `u_role_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `role_id` int(11) DEFAULT NULL COMMENT 'role id',
  `user_id` int(11) DEFAULT NULL COMMENT 'user id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of u_role_user
-- ----------------------------
INSERT INTO `u_role_user` VALUES ('1', '1', '1');
INSERT INTO `u_role_user` VALUES ('2', '2', '2');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `username` varchar(80) DEFAULT NULL COMMENT '登录账号',
  `password` varchar(64) DEFAULT NULL COMMENT '登录密码',
  `nickname` varchar(50) DEFAULT NULL COMMENT '昵称',
  `name` varchar(30) DEFAULT NULL COMMENT '姓名',
  `sex` char(1) DEFAULT '0' COMMENT '性别 默认：0（男） 女：1',
  `birthday` date DEFAULT NULL COMMENT '生日',
  `head_photo_path` varchar(150) DEFAULT NULL COMMENT '头像路径',
  `signature` varchar(255) DEFAULT NULL COMMENT '个性签名',
  `website` varchar(150) DEFAULT NULL COMMENT '个人网站',
  `type` char(1) DEFAULT '0' COMMENT '用户类型 默认：0（普通用户） 管理员：1',
  `login_status` char(1) DEFAULT '0' COMMENT '是否登录状态 默认：0 在线：1',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `last_login_time` datetime DEFAULT NULL COMMENT '最后登录时间',
  `last_login_ip` varchar(15) DEFAULT NULL COMMENT '最后登录IP',
  `delete_status` char(1) DEFAULT '0' COMMENT '删除状态 默认：0 删除：1',
  `enable` char(1) DEFAULT '0' COMMENT '是否停用 默认：0 停用：1',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('1', 'admin', '21232f297a57a5a743894a0e4a801fc3', '管理员', 'administrator', '0', '2014-12-04', null, null, null, '1', '1', '2014-11-30 16:16:06', null, '2014-12-16 17:54:45', '127.0.0.1', '0', '0');
INSERT INTO `user` VALUES ('2', 'fengyoutian', '511ab1735c8a6d580aa1a4b7d34653ca', '風佑兲', '李秀光', '0', '2014-12-04', null, null, 'www.fengyoutian.com', '1', '1', '2014-12-04 22:07:14', '2014-12-05 13:02:52', '2014-12-13 19:40:42', '127.0.0.1', '0', '0');
