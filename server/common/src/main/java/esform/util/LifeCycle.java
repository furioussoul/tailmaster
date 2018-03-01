package esform.util;

import java.io.IOException;

/**
 * Created by szj46941 on 2018/3/1.
 */
public interface LifeCycle {
    String getUUid() throws IOException;
    boolean login();
    boolean webWxInit();
    boolean wxStatusNotify();
    void startReceiving();
    void webWxGetContact();
    boolean WebWxBatchGetContact();
    void setUserInfo();
    void CheckLoginStatusThread();
}
