package esform.domain.wx;

import java.io.Serializable;

/**
 * Created by szj46941 on 2018/3/1.
 */
public class AppInfo implements Serializable {
    private static final long serialVersionUID = 1L;
    private int type;
    private String appId;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

}
