package esform.enums;

public enum RetCodeEnum {

	NORMAL("0", "普通"),
	LOGIN_OUT("1102", "退出"),
	LOGIN_OTHER_WHERE("1101", "其它地方登陆"),
	MOBILE_LOGIN_OUT("1102", "移动端退出"),
	UNKNOWN("9999", "未知");

	private String code;
	private String desc;

	RetCodeEnum(String code, String desc) {
		this.code = code;
		this.desc = desc;
	}

	public String code() {
		return code;
	}

	public String desc() {
		return desc;
	}
}
