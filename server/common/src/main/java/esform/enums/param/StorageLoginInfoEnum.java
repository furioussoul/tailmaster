package esform.enums.param;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 * Created by szj46941 on 2018/2/27.
 */
public enum StorageLoginInfoEnum {

    //URL
    URL("url",new String()),
    FILE_URL("fileUrl",new String()),
    SYNC_URL("syncUrl",new String()),

    DEVICE_ID("deviceid",new String()), //生成15位随机数

    //baseRequest
    S_KEY("skey",new String()),
    WX_SID("wxsid",new String()),
    WX_UIN("wxuin",new String()),
    PASS_TICKET("pass_ticket",new String()),

    INVITE_START_COUNT("InviteStartCount",new Integer(0)),
    USER("User",new JSONObject()),
    SYNC_KEY_1("SyncKey",new JSONObject()),
    SYNC_KEY_2("synckey",new String()),

    MEMBER_COUNT("MemberCount",new String()),
    MEMBER_LIST("MemberList",new JSONArray());

    private String key;
    private Object type;

    StorageLoginInfoEnum(String key, Object type) {
        this.key = key;
        this.type = type;
    }

    public String key() {
        return key;
    }

    public Object type() {
        return type;
    }
}
