package esform.enums;

/**
 * Created by szj46941 on 2018/2/27.
 */
public enum ResultEnum {

    SUCCESS("200", "成功"),
    WAIT_CONFIRM("201", "请在手机上点击确认"),
    WAIT_SCAN("400", "请扫描二维码");

    private String code;
    private String desc;

    ResultEnum(String code, String desc) {
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
