package com.jfinal.share.util;

public class ModelEnum {
	/** 否, 是 */
	public enum YesOrNO {
		NO, YES
	}
	/** 原创, 收藏 */
	public enum BlogType {
		ORIGINALITY, COLLECTION
	}
	/** 正常, 删除  */
	public enum NormalOrDelete {
		NORMAL, DELETE
	}
	/** 启用, 停用 */
	public enum EnableOrDisable {
		ENABLE, DISABLE
	}
	/** 前台, 后台 */
	public enum WebOrAdmin {
		WEB, ADMIN
	}
	/** 男, 女 */
	public enum ManOrWoman {
		MAN, WOMAN
	}
	/** 会员, 管理员 */
	public enum MenberOrAdministrator {
		MEMBER, ADMINISTRATOR
	}
	/** 离线, 在线 */
	public enum OnlineOrOffline {
		OFFLINE, ONLINE
	}
	/** 未回复, 已回复，新回复 */
	public enum ReplyMessage {
		DIDNOREPLY, REPLYED, NEWREPLY
	}
	/** 会员, 作者，管理员 */
	public enum Replyer {
		MEMBER, AUTHOR, ADMINISTRATOR
	}
}
