package esform.enums.param;

/**
 * Created by szj46941 on 2018/3/1.
 */
public enum BaseParaEnum {
    Uin("Uin", "wxuin"),
    Sid("Sid", "wxsid"),
    Skey("Skey", "skey"),
    DeviceID("DeviceID", "pass_ticket");

    private String key;
    private String value;

    BaseParaEnum(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String key() {
        return key;
    }


    public Object value() {
        return value;
    }
}
