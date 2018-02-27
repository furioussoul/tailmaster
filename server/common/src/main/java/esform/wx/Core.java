package esform.wx;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import esform.enums.RetCodeEnum;
import esform.enums.StorageLoginInfoEnum;
import esform.enums.URLEnum;
import esform.util.CommonTool;
import esform.util.HttpClient;
import esform.util.ThreadUtil;
import org.apache.http.HttpEntity;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.w3c.dom.Document;

import java.util.*;
import java.util.regex.Matcher;

/**
 * WX监控核心类，单例
 * Created by szj46941 on 2018/2/27.
 */
public class Core {

    static {
        System.setProperty("jsse.enableSNIExtension", "false"); // 防止SSL错误
    }

    private String uuid = null;
    private boolean alive = false;
    private String indexUrl;
    private Map<String, Object> loginInfo = new HashMap<>();

    /**
     * 处理登陆信息
     */
    public void processLoginInfo(String loginContent) {

        String regEx = "window.redirect_uri=\"(\\S+)\";";
        Matcher matcher = CommonTool.getMatcher(regEx, loginContent);
        if (matcher.find()) {

            String originalUrl = matcher.group(1);
            String url = originalUrl.substring(0, originalUrl.lastIndexOf('/')); // https://wx2.qq.com/cgi-bin/mmwebwx-bin
            this.getLoginInfo().put("url", url);
            Map<String, List<String>> possibleUrlMap = this.getPossibleUrlMap();
            Iterator<Map.Entry<String, List<String>>> iterator = possibleUrlMap.entrySet().iterator();
            Map.Entry<String, List<String>> entry;
            String fileUrl;
            String syncUrl;
            while (iterator.hasNext()) {
                entry = iterator.next();
                String indexUrl = entry.getKey();
                fileUrl = "https://" + entry.getValue().get(0) + "/cgi-bin/mmwebwx-bin";
                syncUrl = "https://" + entry.getValue().get(1) + "/cgi-bin/mmwebwx-bin";
                if (this.getLoginInfo().get("url").toString().contains(indexUrl)) {
                    this.setIndexUrl(indexUrl);
                    this.getLoginInfo().put("fileUrl", fileUrl);
                    this.getLoginInfo().put("syncUrl", syncUrl);
                    break;
                }
            }
            if (this.getLoginInfo().get("fileUrl") == null && this.getLoginInfo().get("syncUrl") == null) {
                this.getLoginInfo().put("fileUrl", url);
                this.getLoginInfo().put("syncUrl", url);
            }
            this.getLoginInfo().put("deviceid", "e" + String.valueOf(new Random().nextLong()).substring(1, 16)); // 生成15位随机数
            this.getLoginInfo().put("BaseRequest", new ArrayList<String>());
            String text = "";

            try {
                HttpEntity entity = HttpClient.doGet(originalUrl, null, false, null);
                text = EntityUtils.toString(entity);
            } catch (Exception e) {
                e.printStackTrace();
                return;
            }
            //add by 默非默 2017-08-01 22:28:09
            //如果登录被禁止时，则登录返回的message内容不为空，下面代码则判断登录内容是否为空，不为空则退出程序
            String msg = getLoginMessage(text);
            if (!"".equals(msg)) {
                System.exit(0);
            }
            Document doc = CommonTool.xmlParser(text);
            if (doc != null) {
                this.getLoginInfo().put(StorageLoginInfoEnum.S_KEY.key(),
                        doc.getElementsByTagName(StorageLoginInfoEnum.S_KEY.key()).item(0).getFirstChild()
                                .getNodeValue());
                this.getLoginInfo().put(StorageLoginInfoEnum.WX_SID.key(),
                        doc.getElementsByTagName(StorageLoginInfoEnum.WX_SID.key()).item(0).getFirstChild()
                                .getNodeValue());
                this.getLoginInfo().put(StorageLoginInfoEnum.WX_UIN.key(),
                        doc.getElementsByTagName(StorageLoginInfoEnum.WX_UIN.key()).item(0).getFirstChild()
                                .getNodeValue());
                this.getLoginInfo().put(StorageLoginInfoEnum.PASS_TICKET.key(),
                        doc.getElementsByTagName(StorageLoginInfoEnum.PASS_TICKET.key()).item(0).getFirstChild()
                                .getNodeValue());
            }
        }
    }

    /**
     * 解析登录返回的消息，如果成功登录，则message为空
     */
    private String getLoginMessage(String result) {
        String[] strArr = result.split("<message>");
        String[] rs = strArr[1].split("</message>");
        if (rs.length > 1) {
            return rs[0];
        }
        return "";
    }

    private Map<String, List<String>> getPossibleUrlMap() {
        Map<String, List<String>> possibleUrlMap = new HashMap<String, List<String>>();
        possibleUrlMap.put("wx.qq.com", new ArrayList<String>() {
            /**
             *
             */
            private static final long serialVersionUID = 1L;

            {
                add("file.wx.qq.com");
                add("webpush.wx.qq.com");
            }
        });

        possibleUrlMap.put("wx2.qq.com", new ArrayList<String>() {
            /**
             *
             */
            private static final long serialVersionUID = 1L;

            {
                add("file.wx2.qq.com");
                add("webpush.wx2.qq.com");
            }
        });
        possibleUrlMap.put("wx8.qq.com", new ArrayList<String>() {
            /**
             *
             */
            private static final long serialVersionUID = 1L;

            {
                add("file.wx8.qq.com");
                add("webpush.wx8.qq.com");
            }
        });

        possibleUrlMap.put("web2.wechat.com", new ArrayList<String>() {
            /**
             *
             */
            private static final long serialVersionUID = 1L;

            {
                add("file.web2.wechat.com");
                add("webpush.web2.wechat.com");
            }
        });
        possibleUrlMap.put("wechat.com", new ArrayList<String>() {
            /**
             *
             */
            private static final long serialVersionUID = 1L;

            {
                add("file.web.wechat.com");
                add("webpush.web.wechat.com");
            }
        });
        return possibleUrlMap;
    }

    /*public void startReceiving() {
        this.setAlive(true);
        final Core core = this;
        ThreadUtil.addTask((new Runnable() {
            int retryCount = 0;

            @Override
            public void run() {
                while (core.isAlive()) {
                    try {
                        Map<String, String> resultMap = syncCheck();
                        String retcode = resultMap.get("retcode");
                        String selector = resultMap.get("selector");
                        if (retcode.equals(RetCodeEnum.UNKNOWN.code())) {
                            continue;
                        } else if (retcode.equals(RetCodeEnum.LOGIN_OUT.code())) { // 退出
                            break;
                        } else if (retcode.equals(RetCodeEnum.LOGIN_OTHER_WHERE.code())) { // 其它地方登陆
                            break;
                        } else if (retcode.equals(RetCodeEnum.MOBILE_LOGIN_OUT.code())) { // 移动端退出
                            break;
                        } else if (retcode.equals(RetCodeEnum.NORMAL.code())) {
                            core.setLastNormalRetcodeTime(System.currentTimeMillis()); // 最后收到正常报文时间
                            JSONObject msgObj = webWxSync();
                            if (selector.equals("2")) {
                                if (msgObj != null) {
                                    try {
                                        JSONArray msgList = new JSONArray();
                                        msgList = msgObj.getJSONArray("AddMsgList");
                                        msgList = MsgCenter.produceMsg(msgList);
                                        for (int j = 0; j < msgList.size(); j++) {
                                            BaseMsg baseMsg = JSON.toJavaObject(msgList.getJSONObject(j),
                                                    BaseMsg.class);
                                            core.getMsgList().add(baseMsg);
                                        }
                                    } catch (Exception e) {
                                        LOG.info(e.getMessage());
                                    }
                                }
                            } else if (selector.equals("7")) {
                                webWxSync();
                            } else if (selector.equals("4")) {
                                continue;
                            } else if (selector.equals("3")) {
                                continue;
                            } else if (selector.equals("6")) {
                                if (msgObj != null) {
                                    try {
                                        JSONArray msgList = new JSONArray();
                                        msgList = msgObj.getJSONArray("AddMsgList");
                                        JSONArray modContactList = msgObj.getJSONArray("ModContactList"); // 存在删除或者新增的好友信息
                                        msgList = MsgCenter.produceMsg(msgList);
                                        for (int j = 0; j < msgList.size(); j++) {
                                            JSONObject userInfo = modContactList.getJSONObject(j);
                                            // 存在主动加好友之后的同步联系人到本地
                                            core.getContactList().add(userInfo);
                                        }
                                    } catch (Exception e) {
                                        LOG.info(e.getMessage());
                                    }
                                }

                            }
                        } else {
                            JSONObject obj = webWxSync();
                        }
                    } catch (Exception e) {
                        LOG.info(e.getMessage());
                        retryCount += 1;
                        if (core.getReceivingRetryCount() < retryCount) {
                            core.setAlive(false);
                        } else {
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e1) {
                                LOG.info(e.getMessage());
                            }
                        }
                    }

                }
            }
        }));
    }
*/
    /**
     * 检查登陆状态
     *
     * @param result
     * @return
     */
    public String checkLogin(String result) {
        String regEx = "window.code=(\\d+)";
        Matcher matcher = CommonTool.getMatcher(regEx, result);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return null;
    }

 /*   //检查是否有新消息
    private Map<String, String> syncCheck() {
        Map<String, String> resultMap = new HashMap<>();
        // 组装请求URL和参数
        String url = this.getLoginInfo().get(StorageLoginInfoEnum.SYNC_URL.key()) + URLEnum.SYNC_CHECK_URL.url();
        List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
        for (BaseParaEnum baseRequest : BaseParaEnum.values()) {
            params.add(new BasicNameValuePair(baseRequest.para().toLowerCase(),
                    core.getLoginInfo().get(baseRequest.value()).toString()));
        }
        params.add(new BasicNameValuePair("r", String.valueOf(new Date().getTime())));
        params.add(new BasicNameValuePair("synckey", (String) core.getLoginInfo().get("synckey")));
        params.add(new BasicNameValuePair("_", String.valueOf(new Date().getTime())));
        SleepUtils.sleep(7);
        try {
            HttpEntity entity = myHttpClient.doGet(url, params, true, null);
            if (entity == null) {
                resultMap.put("retcode", "9999");
                resultMap.put("selector", "9999");
                return resultMap;
            }
            String text = EntityUtils.toString(entity);
            String regEx = "window.synccheck=\\{retcode:\"(\\d+)\",selector:\"(\\d+)\"\\}";
            Matcher matcher = CommonTools.getMatcher(regEx, text);
            if (!matcher.find() || matcher.group(1).equals("2")) {
                LOG.info(String.format("Unexpected sync check result: %s", text));
            } else {
                resultMap.put("retcode", matcher.group(1));
                resultMap.put("selector", matcher.group(2));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultMap;
    }*/


    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public boolean isAlive() {
        return alive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    public String getIndexUrl() {
        return indexUrl;
    }

    public void setIndexUrl(String indexUrl) {
        this.indexUrl = indexUrl;
    }

    public Map<String, Object> getLoginInfo() {
        return loginInfo;
    }

    public void setLoginInfo(Map<String, Object> loginInfo) {
        this.loginInfo = loginInfo;
    }
}
