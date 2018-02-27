package esform.enums;

/**
 * Created by szj46941 on 2018/2/27.
 */
public enum LoginParaEnum {

    LOGIN_ICON("loginicon", "true"),
    UUID("uuid", ""),
    TIP("tip", "0"),
    R("r", ""),
    _("_", "");

    private String key;
    private String value;

    LoginParaEnum(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String key() {
        return key;
    }

    public String value() {
        return value;
    }
}
