package esform.enums;

/**
 * 消息类型
 */
public enum MsgCodeEnum {

	MSGTYPE_TEXT(1, "文本消息类型"),
	MSGTYPE_IMAGE(3, "图片消息"),
	MSGTYPE_VOICE(34, "语音消息"),
	MSGTYPE_VIDEO(43, "小视频消息"),
	MSGTYPE_MICROVIDEO(62, "短视频消息"),
	MSGTYPE_EMOTICON(47, "表情消息"),
	MSGTYPE_MEDIA(49, "多媒体消息"),
	MSGTYPE_VOIPMSG(50, ""),
	MSGTYPE_VOIPNOTIFY(52, ""),
	MSGTYPE_VOIPINVITE(53, ""),
	MSGTYPE_LOCATION(48, ""),
	MSGTYPE_STATUSNOTIFY(51, ""),
	MSGTYPE_SYSNOTICE(9999, ""),
	MSGTYPE_POSSIBLEFRIEND_MSG(40, ""),
	MSGTYPE_VERIFYMSG(37, "好友请求"),
	MSGTYPE_SHARECARD(42, ""),
	MSGTYPE_SYS(10000, "系统消息"),
	MSGTYPE_RECALLED(10002, "");

	private int code;
	private String desc;

	MsgCodeEnum(int code, String desc) {
		this.code = code;
		this.desc = desc;
	}

	public int getCode() {
		return code;
	}

	public String getDesc() {
		return desc;
	}
}
