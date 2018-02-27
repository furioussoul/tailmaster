package esform.enums;

/**
 * Created by szj46941 on 2018/2/27.
 */
public enum UUIDParaEnum {

    APP_ID("appid", "wx782c26e4c19acffb"),
    FUN("fun", "new"),
    LANG("lang", "zh_CN"),
    TIME_STAMP("_", "时间戳");

    private String key;
    private String value;

    UUIDParaEnum(String key, String value) {
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
