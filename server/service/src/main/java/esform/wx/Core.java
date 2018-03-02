package esform.wx;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import esform.domain.wx.BaseMsg;
import esform.enums.*;
import esform.enums.param.*;
import esform.util.*;
import esform.wx.msgHandler.CommonMsgHandler;
import esform.wx.msgHandler.IMsgHandlerFace;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.w3c.dom.Document;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;

/**
 * Created by szj46941 on 2018/2/27.
 */
public class Core implements LifeCycle {

    static {
        System.setProperty("jsse.enableSNIExtension", "false"); // 防止SSL错误
    }

    private static IMsgHandlerFace msgHandler = CommonMsgHandler.getInstance();

    int retryCount = 0;
    private String uuid = null;
    private Map<String, Object> loginInfo = new HashMap<>();
    private boolean alive = false;
    private String indexUrl;
    private long lastNormalRetCodeTime; // 最后一次收到正常retcode的时间，秒为单位
    private String userName;
    private String nickName;
    private List<BaseMsg> msgList = new ArrayList<BaseMsg>();
    private List<String> groupIdList = new ArrayList<String>(); // 群ID列表
    private List<String> groupNickNameList = new ArrayList<String>(); // 群NickName列表
    private int memberCount = 0;
    private JSONObject userSelf; // 登陆账号自身信息
    private List<JSONObject> memberList = new ArrayList<JSONObject>(); // 好友+群聊+公众号+特殊账号
    private List<JSONObject> contactList = new ArrayList<JSONObject>();// 好友
    private List<JSONObject> groupList = new ArrayList<JSONObject>(); // 群
    private Map<String, JSONArray> groupMemberMap = new HashMap<String, JSONArray>(); // 群聊成员字典
    private List<JSONObject> publicUsersList = new ArrayList<JSONObject>();// 公众号／服务号
    private List<JSONObject> specialUsersList = new ArrayList<JSONObject>();// 特殊账号
    private Map<String, JSONObject> userInfoMap = new HashMap<String, JSONObject>();

    private boolean useHotReload = false;
    private String hotReloadDir = "itchat.pkl";
    private int receivingRetryCount = 5;

    public void login(String uuid) {

        boolean isLogin = false;

        List<BasicNameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair(LoginParaEnum.LOGIN_ICON.key(), LoginParaEnum.LOGIN_ICON.value()));
        params.add(new BasicNameValuePair(LoginParaEnum.UUID.key(), uuid));
        params.add(new BasicNameValuePair(LoginParaEnum.TIP.key(), LoginParaEnum.TIP.value()));

        long startTime = System.currentTimeMillis();

        while (!isLogin) {
            long millis = System.currentTimeMillis();

            if (millis - startTime > 60000) {
                //循环尝试超过1分钟
                break;
            }

            params.add(new BasicNameValuePair(LoginParaEnum.R.key(), String.valueOf(millis / 1579L)));
            params.add(new BasicNameValuePair(LoginParaEnum._.key(), String.valueOf(millis)));
            String result = HttpClient.get(URLEnum.LOGIN_URL.url(), params, true, null);

            String status = checkLogin(result);

            if (ResultEnum.SUCCESS.code().equals(status)) {
                this.setAlive(true);
                isLogin = true;
                processLoginInfo(result); // 处理结果
                LogUtil.info("processLoginInfo");

                webWxInit();
                LogUtil.info("webWxInit");

                wxStatusNotify();
                LogUtil.info("wxStatusNotify");

                startReceiving();
                LogUtil.info("startReceiving");

                webWxGetContact();
                LogUtil.info("webWxGetContact");

                WebWxBatchGetContact();
                LogUtil.info("WebWxBatchGetContact");

                setUserInfo();
                LogUtil.info("setUserInfo");

//                CheckLoginStatusThread();
                handleMsg();
                LogUtil.info("handleMsg");
            }
        }
    }

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
            String text = HttpClient.get(originalUrl, null, false, null);

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

    public String getUUid() throws IOException {
        List<BasicNameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair(UUIDParaEnum.APP_ID.key(), UUIDParaEnum.APP_ID.value()));
        params.add(new BasicNameValuePair(UUIDParaEnum.FUN.key(), UUIDParaEnum.FUN.value()));
        params.add(new BasicNameValuePair(UUIDParaEnum.LANG.key(), UUIDParaEnum.LANG.value()));
        params.add(new BasicNameValuePair(UUIDParaEnum.TIME_STAMP.key(), String.valueOf(System.currentTimeMillis())));

        String result = HttpClient.get(URLEnum.UUID_URL.url(), params, false, null);

        String uuid = null;
        String regEx = "window.QRLogin.code = (\\d+); window.QRLogin.uuid = \"(\\S+?)\";";
        Matcher matcher = CommonTool.getMatcher(regEx, result);
        if (matcher.find()) {
            if ((ResultEnum.SUCCESS.code().equals(matcher.group(1)))) {
                uuid = matcher.group(2);
                String finalUuid = uuid;

                ThreadUtil.addTask(() -> {
                    login(finalUuid);
                });

            }
        }
        return uuid;
    }

    @Override
    public boolean login() {
        return false;
    }

    public Map<String, Object> getParamMap() {
        return new HashMap<String, Object>(1) {

            private static final long serialVersionUID = 1L;

            {
                Map<String, String> map = new HashMap<String, String>();
                for (BaseParaEnum baseRequest : BaseParaEnum.values()) {
                    map.put(baseRequest.key(), getLoginInfo().get(baseRequest.value()).toString());
                }
                put("BaseRequest", map);
            }
        };
    }

    @Override
    public boolean webWxInit() {
        this.setAlive(true);
        this.setLastNormalRetCodeTime(System.currentTimeMillis());
        // 组装请求URL和参数
        String url = String.format(URLEnum.INIT_URL.url(),
                getLoginInfo().get(StorageLoginInfoEnum.URL.key()),
                String.valueOf(System.currentTimeMillis() / 3158L),
                getLoginInfo().get(StorageLoginInfoEnum.PASS_TICKET.key()));

        Map<String, Object> paramMap = getParamMap();

        // 请求初始化接口
        HttpEntity entity = HttpClient.doPost(url, JSON.toJSONString(paramMap));
        try {
            String result = EntityUtils.toString(entity, Consts.UTF_8);
            JSONObject obj = JSON.parseObject(result);

            JSONObject user = obj.getJSONObject(StorageLoginInfoEnum.USER.key());
            JSONObject syncKey = obj.getJSONObject(StorageLoginInfoEnum.SYNC_KEY_1.key());

            getLoginInfo().put(StorageLoginInfoEnum.INVITE_START_COUNT.key(),
                    obj.getInteger(StorageLoginInfoEnum.INVITE_START_COUNT.key()));
            getLoginInfo().put(StorageLoginInfoEnum.SYNC_KEY_1.key(), syncKey);

            JSONArray syncArray = syncKey.getJSONArray("List");
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < syncArray.size(); i++) {
                sb.append(syncArray.getJSONObject(i).getString("Key") + "_"
                        + syncArray.getJSONObject(i).getString("Val") + "|");
            }
            // 1_661706053|2_661706420|3_661706415|1000_1494151022|
            String synckey = sb.toString();

            // 1_661706053|2_661706420|3_661706415|1000_1494151022
            getLoginInfo().put(StorageLoginInfoEnum.SYNC_KEY_2.key(), synckey.substring(0, synckey.length() - 1));// 1_656161336|2_656161626|3_656161313|11_656159955|13_656120033|201_1492273724|1000_1492265953|1001_1492250432|1004_1491805192
            setUserName(user.getString("UserName"));
            setNickName(user.getString("NickName"));
            setUserSelf(obj.getJSONObject("User"));

            String chatSet = obj.getString("ChatSet");
            String[] chatSetArray = chatSet.split(",");
            for (int i = 0; i < chatSetArray.length; i++) {
                if (chatSetArray[i].indexOf("@@") != -1) {
                    // 更新GroupIdList
                    getGroupIdList().add(chatSetArray[i]); //
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return false;
    }

    @Override
    public boolean wxStatusNotify() {
        // 组装请求URL和参数
        String url = String.format(URLEnum.STATUS_NOTIFY_URL.url(),
                getLoginInfo().get(StorageLoginInfoEnum.PASS_TICKET.key()));

        Map<String, Object> paramMap = getParamMap();
        paramMap.put(StatusNotifyParaEnum.CODE.para(), StatusNotifyParaEnum.CODE.value());
        paramMap.put(StatusNotifyParaEnum.FROM_USERNAME.para(), getUserName());
        paramMap.put(StatusNotifyParaEnum.TO_USERNAME.para(), getUserName());
        paramMap.put(StatusNotifyParaEnum.CLIENT_MSG_ID.para(), System.currentTimeMillis());
        String paramStr = JSON.toJSONString(paramMap);

        try {
            HttpEntity entity = HttpClient.doPost(url, paramStr);
//            EntityUtils.toString(entity, Consts.UTF_8);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public void startReceiving() {
        ThreadUtil.addTask(() -> {
            while (isAlive()) {
                try {
                    Map<String, String> resultMap = syncCheck();
                    String retcode = resultMap.get("retcode");
                    String selector = resultMap.get("selector");
                    if (retcode.equals(RetCodeEnum.UNKNOWN.code())) {
//                    LOG.info(RetCodeEnum.UNKNOWN.type());
                    } else if (retcode.equals(RetCodeEnum.LOGIN_OUT.code())) { // 退出
//                    LOG.info(RetCodeEnum.LOGIN_OUT.getType());
                        break;
                    } else if (retcode.equals(RetCodeEnum.LOGIN_OTHER_WHERE.code())) { // 其它地方登陆
//                    LOG.info(RetCodeEnum.LOGIN_OTHERWHERE.getType());
                        break;
                    } else if (retcode.equals(RetCodeEnum.MOBILE_LOGIN_OUT.code())) { // 移动端退出
//                    LOG.info(RetCodeEnum.MOBILE_LOGIN_OUT.getType());
                        break;
                    } else if (retcode.equals(RetCodeEnum.NORMAL.code())) {
                        setLastNormalRetCodeTime(System.currentTimeMillis()); // 最后收到正常报文时间
                        JSONObject msgObj = webWxSync();
                        if (selector.equals("2")) {
                            if (msgObj != null) {
                                try {
                                    JSONArray msgList = new JSONArray();
                                    msgList = msgObj.getJSONArray("AddMsgList");
                                    msgList = produceMsg(msgList);
                                    for (int j = 0; j < msgList.size(); j++) {
                                        BaseMsg baseMsg = JSON.parseObject(JSON.toJSONString(msgList.getJSONObject(j)), BaseMsg.class);
                                        getMsgList().add(baseMsg);
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
//                                LOG.info(e.getMessage());
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
                                    msgList = produceMsg(msgList);
                                    for (int j = 0; j < msgList.size(); j++) {
                                        JSONObject userInfo = modContactList.getJSONObject(j);
                                        // 存在主动加好友之后的同步联系人到本地
                                        getContactList().add(userInfo);
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
//                                LOG.info(e.getMessage());
                                }
                            }
                        }
                    } else {
                        JSONObject obj = webWxSync();
                    }
                } catch (Exception e) {
//                LOG.info(e.getMessage());
                    retryCount += 1;
                    if (getReceivingRetryCount() < retryCount) {
                        setAlive(false);
                    } else {
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e1) {
                            e1.printStackTrace();
//                        LOG.info(e.getMessage());
                        }
                    }
                }

            }
        });
    }

    //检查是否有新消息
    private Map<String, String> syncCheck() {
        Map<String, String> resultMap = new HashMap<>();
        String url = getLoginInfo().get(StorageLoginInfoEnum.SYNC_URL.key()) + URLEnum.SYNC_CHECK_URL.url();
        List<BasicNameValuePair> params = new ArrayList<>();
        for (BaseParaEnum baseRequest : BaseParaEnum.values()) {
            params.add(new BasicNameValuePair(baseRequest.key().toLowerCase(),
                    getLoginInfo().get(baseRequest.value()).toString()));
        }
        params.add(new BasicNameValuePair("r", String.valueOf(new Date().getTime())));
        params.add(new BasicNameValuePair("synckey", (String) getLoginInfo().get("synckey")));
        params.add(new BasicNameValuePair("_", String.valueOf(new Date().getTime())));
        try {
            String result = HttpClient.get(url, params, true, null);
            if (result == null) {
                resultMap.put("retcode", "9999");
                resultMap.put("selector", "9999");
                return resultMap;
            }
            String regEx = "window.synccheck=\\{retcode:\"(\\d+)\",selector:\"(\\d+)\"\\}";
            Matcher matcher = CommonTool.getMatcher(regEx, result);
            if (!matcher.find() || matcher.group(1).equals("2")) {
//                LOG.info(String.format("Unexpected sync check result: %s", text));
            } else {
                resultMap.put("retcode", matcher.group(1));
                resultMap.put("selector", matcher.group(2));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultMap;
    }

    //同步消息 sync the messages
    private JSONObject webWxSync() {
        JSONObject result = null;
        String url = String.format(URLEnum.WEB_WX_SYNC_URL.url(),
                getLoginInfo().get(StorageLoginInfoEnum.URL.key()),
                getLoginInfo().get(StorageLoginInfoEnum.WX_SID.key()),
                getLoginInfo().get(StorageLoginInfoEnum.S_KEY.key()),
                getLoginInfo().get(StorageLoginInfoEnum.PASS_TICKET.key()));
        Map<String, Object> paramMap = getParamMap();
        paramMap.put(StorageLoginInfoEnum.SYNC_KEY_1.key(),
                getLoginInfo().get(StorageLoginInfoEnum.SYNC_KEY_1.key()));
        paramMap.put("rr", -new Date().getTime() / 1000);
        String paramStr = JSON.toJSONString(paramMap);
        try {
            HttpEntity entity = HttpClient.doPost(url, paramStr);
            String text = EntityUtils.toString(entity, Consts.UTF_8);
            JSONObject obj = JSON.parseObject(text);
            if (obj.getJSONObject("BaseResponse").getInteger("Ret") != 0) {
                result = null;
            } else {
                result = obj;
                getLoginInfo().put(StorageLoginInfoEnum.SYNC_KEY_1.key(), obj.getJSONObject("SyncCheckKey"));
                JSONArray syncArray = obj.getJSONObject(StorageLoginInfoEnum.SYNC_KEY_1.key()).getJSONArray("List");
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < syncArray.size(); i++) {
                    sb.append(syncArray.getJSONObject(i).getString("Key") + "_"
                            + syncArray.getJSONObject(i).getString("Val") + "|");
                }
                String synckey = sb.toString();
                getLoginInfo().put(StorageLoginInfoEnum.SYNC_KEY_2.key(),
                        synckey.substring(0, synckey.length() - 1));// 1_656161336|2_656161626|3_656161313|11_656159955|13_656120033|201_1492273724|1000_1492265953|1001_1492250432|1004_1491805192
            }
        } catch (Exception e) {
            e.printStackTrace();
//            LOG.info(e.getMessage());
        }
        return result;

    }

    @Override
    public void webWxGetContact() {
        String url = String.format(URLEnum.WEB_WX_GET_CONTACT.url(),
                getLoginInfo().get(StorageLoginInfoEnum.URL.key()));
        Map<String, Object> paramMap = getParamMap();
        HttpEntity entity = HttpClient.doPost(url, JSON.toJSONString(paramMap));

        try {
            String result = EntityUtils.toString(entity, Consts.UTF_8);
            JSONObject fullFriendsJsonList = JSON.parseObject(result);
            // 查看seq是否为0，0表示好友列表已全部获取完毕，若大于0，则表示好友列表未获取完毕，当前的字节数（断点续传）
            long seq = 0;
            long currentTime = 0L;
            List<BasicNameValuePair> params = new ArrayList<>();
            if (fullFriendsJsonList.get("Seq") != null) {
                seq = fullFriendsJsonList.getLong("Seq");
                currentTime = new Date().getTime();
            }
            setMemberCount(fullFriendsJsonList.getInteger(StorageLoginInfoEnum.MEMBER_COUNT.key()));
            JSONArray member = fullFriendsJsonList.getJSONArray(StorageLoginInfoEnum.MEMBER_LIST.key());
            // 循环获取seq直到为0，即获取全部好友列表 ==0：好友获取完毕 >0：好友未获取完毕，此时seq为已获取的字节数
            while (seq > 0) {
                // 设置seq传参
                params.add(new BasicNameValuePair("r", String.valueOf(currentTime)));
                params.add(new BasicNameValuePair("seq", String.valueOf(seq)));
                result = HttpClient.get(url, params, false, null);

                params.remove(new BasicNameValuePair("r", String.valueOf(currentTime)));
                params.remove(new BasicNameValuePair("seq", String.valueOf(seq)));

                fullFriendsJsonList = JSON.parseObject(result);

                if (fullFriendsJsonList.get("Seq") != null) {
                    seq = fullFriendsJsonList.getLong("Seq");
                    currentTime = new Date().getTime();
                }

                // 累加好友列表
                member.addAll(fullFriendsJsonList.getJSONArray(StorageLoginInfoEnum.MEMBER_LIST.key()));
            }
            setMemberCount(member.size());
            for (Iterator<?> iterator = member.iterator(); iterator.hasNext(); ) {
                JSONObject o = (JSONObject) iterator.next();
                if ((o.getInteger("VerifyFlag") & 8) != 0) { // 公众号/服务号
                    getPublicUsersList().add(o);
                } else if (Config.API_SPECIAL_USER.contains(o.getString("UserName"))) { // 特殊账号
                    getSpecialUsersList().add(o);
                } else if (o.getString("UserName").indexOf("@@") != -1) { // 群聊
                    if (!getGroupIdList().contains(o.getString("UserName"))) {
                        getGroupNickNameList().add(o.getString("NickName"));
                        getGroupIdList().add(o.getString("UserName"));
                        getGroupList().add(o);
                    }
                } else if (o.getString("UserName").equals(getUserSelf().getString("UserName"))) { // 自己
                    getContactList().remove(o);
                } else { // 普通联系人
                    getContactList().add(o);
                }
            }
            return;
        } catch (Exception e) {
            e.printStackTrace();
//            LOG.error(e.getMessage(), e);
        }
        return;
    }

    @Override
    public boolean WebWxBatchGetContact() {
        String url = String.format(URLEnum.WEB_WX_BATCH_GET_CONTACT.url(),
                getLoginInfo().get(StorageLoginInfoEnum.URL.key()), new Date().getTime(),
                getLoginInfo().get(StorageLoginInfoEnum.PASS_TICKET.key()));
        Map<String, Object> paramMap = getParamMap();
        paramMap.put("Count", getGroupIdList().size());
        List<Map<String, String>> list = new ArrayList<Map<String, String>>();
        for (int i = 0; i < getGroupIdList().size(); i++) {
            HashMap<String, String> map = new HashMap<String, String>();
            map.put("UserName", getGroupIdList().get(i));
            map.put("EncryChatRoomId", "");
            list.add(map);
        }
        paramMap.put("List", list);
        HttpEntity entity = HttpClient.doPost(url, JSON.toJSONString(paramMap));
        try {
            String text = EntityUtils.toString(entity, Consts.UTF_8);
            JSONObject obj = JSON.parseObject(text);
            JSONArray contactList = obj.getJSONArray("ContactList");
            for (int i = 0; i < contactList.size(); i++) { // 群好友
                if (contactList.getJSONObject(i).getString("UserName").indexOf("@@") > -1) { // 群
                    getGroupNickNameList().add(contactList.getJSONObject(i).getString("NickName")); // 更新群昵称列表
                    getGroupList().add(contactList.getJSONObject(i)); // 更新群信息（所有）列表
                    getGroupMemberMap().put(contactList.getJSONObject(i).getString("UserName"),
                            contactList.getJSONObject(i).getJSONArray("MemberList")); // 更新群成员Map
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
//            LOG.info(e.getMessage());
        }
        return false;
    }

    @Override
    public void setUserInfo() {
        for (JSONObject o : getContactList()) {
            getUserInfoMap().put(o.getString("NickName"), o);
            getUserInfoMap().put(o.getString("UserName"), o);
        }
    }

    @Override
    public void CheckLoginStatusThread() {
        while (isAlive()) {
            long t1 = System.currentTimeMillis(); // 秒为单位
            if (t1 - getLastNormalRetCodeTime() > 60 * 1000) { // 超过60秒，判为离线
                setAlive(false);
//                LOG.info("微信已离线");
            }
            try {
                Thread.sleep(10 * 1000); // 休眠10秒
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 接收消息，放入队列
     */
    public JSONArray produceMsg(JSONArray msgList) {
        JSONArray result = new JSONArray();
        for (int i = 0; i < msgList.size(); i++) {
            JSONObject msg = new JSONObject();
            JSONObject m = msgList.getJSONObject(i);
            m.put("groupMsg", false);// 是否是群消息
            if (m.getString("FromUserName").contains("@@") || m.getString("ToUserName").contains("@@")) { // 群聊消息
                if (m.getString("FromUserName").contains("@@")
                        && !getGroupIdList().contains(m.getString("FromUserName"))) {
                    getGroupIdList().add((m.getString("FromUserName")));
                } else if (m.getString("ToUserName").contains("@@")
                        && !getGroupIdList().contains(m.getString("ToUserName"))) {
                    getGroupIdList().add((m.getString("ToUserName")));
                }
                // 群消息与普通消息不同的是在其消息体（Content）中会包含发送者id及":<br/>"消息，这里需要处理一下，去掉多余信息，只保留消息内容
                if (m.getString("Content").contains("<br/>")) {
                    String content = m.getString("Content").substring(m.getString("Content").indexOf("<br/>") + 5);
                    m.put("Content", content);
                    m.put("groupMsg", true);
                }
            } else {
                CommonTool.msgFormatter(m, "Content");
            }
            if (m.getInteger("MsgType").equals(MsgCodeEnum.MSGTYPE_TEXT.getCode())) { // words
                // 文本消息
                if (m.getString("Url").length() != 0) {
                    String regEx = "(.+?\\(.+?\\))";
                    Matcher matcher = CommonTool.getMatcher(regEx, m.getString("Content"));
                    String data = "Map";
                    if (matcher.find()) {
                        data = matcher.group(1);
                    }
                    msg.put("Type", "Map");
                    msg.put("Text", data);
                } else {
                    msg.put("Type", MsgTypeEnum.TEXT.getType());
                    msg.put("Text", m.getString("Content"));
                }
                m.put("Type", msg.getString("Type"));
                m.put("Text", msg.getString("Text"));
            } else if (m.getInteger("MsgType").equals(MsgCodeEnum.MSGTYPE_IMAGE.getCode())
                    || m.getInteger("MsgType").equals(MsgCodeEnum.MSGTYPE_EMOTICON.getCode())) { // 图片消息
                m.put("Type", MsgTypeEnum.PIC.getType());
            } else if (m.getInteger("MsgType").equals(MsgCodeEnum.MSGTYPE_VOICE.getCode())) { // 语音消息
                m.put("Type", MsgTypeEnum.VOICE.getType());
            } else if (m.getInteger("MsgType").equals(MsgCodeEnum.MSGTYPE_VERIFYMSG.getCode())) {// friends
                // 好友确认消息
                // MessageTools.addFriend(core, userName, 3, ticket); // 确认添加好友
                m.put("Type", MsgTypeEnum.VERIFYMSG.getType());

            } else if (m.getInteger("MsgType").equals(MsgCodeEnum.MSGTYPE_SHARECARD.getCode())) { // 共享名片
                m.put("Type", MsgTypeEnum.NAMECARD.getType());

            } else if (m.getInteger("MsgType").equals(MsgCodeEnum.MSGTYPE_VIDEO.getCode())
                    || m.getInteger("MsgType").equals(MsgCodeEnum.MSGTYPE_MICROVIDEO.getCode())) {// viedo
                m.put("Type", MsgTypeEnum.VIEDO.getType());
            } else if (m.getInteger("MsgType").equals(MsgCodeEnum.MSGTYPE_MEDIA.getCode())) { // 多媒体消息
                m.put("Type", MsgTypeEnum.MEDIA.getType());
            } else if (m.getInteger("MsgType").equals(MsgCodeEnum.MSGTYPE_STATUSNOTIFY.getCode())) {// phone
                // init
                // 微信初始化消息

            } else if (m.getInteger("MsgType").equals(MsgCodeEnum.MSGTYPE_SYS.getCode())) {// 系统消息
                m.put("Type", MsgTypeEnum.SYS.getType());
            } else if (m.getInteger("MsgType").equals(MsgCodeEnum.MSGTYPE_RECALLED.getCode())) { // 撤回消息

            } else {
                System.out.println("Useless msg");
//                LOG.info("Useless msg");
            }
            System.out.println("收到消息一条，来自: " + m.getString("FromUserName"));
            result.add(m);
        }
        return result;
    }

    /**
     * 消息处理
     */
    public void handleMsg() {
        while (true) {
            if (getMsgList().size() > 0 && getMsgList().get(0).getContent() != null) {
                if (getMsgList().get(0).getContent().length() > 0) {
                    BaseMsg msg = getMsgList().get(0);
                    if (msg.getType() != null) {
                        try {
                            if (msg.getType().equals(MsgTypeEnum.TEXT.getType())) {
                                String result = msgHandler.textMsgHandle(this, msg);
                                if (StringUtils.isNotEmpty(result)) {
                                    BaseMsg baseMsg = getMsgList().get(0);
                                    if (!this.getUserName().equals(baseMsg.getFromUserName())) {
                                        MessageTools.sendMsgById(this, result, getMsgList().get(0).getFromUserName());
                                    }
                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
                getMsgList().remove(0);
            }
            try {
                TimeUnit.MILLISECONDS.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
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

    public String getUuid() {
        return uuid;
    }

    public long getLastNormalRetCodeTime() {
        return lastNormalRetCodeTime;
    }

    public void setLastNormalRetCodeTime(long lastNormalRetCodeTime) {
        this.lastNormalRetCodeTime = lastNormalRetCodeTime;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public List<BaseMsg> getMsgList() {
        return msgList;
    }

    public void setMsgList(List<BaseMsg> msgList) {
        this.msgList = msgList;
    }

    public JSONObject getUserSelf() {
        return userSelf;
    }

    public void setUserSelf(JSONObject userSelf) {
        this.userSelf = userSelf;
    }

    public List<String> getGroupIdList() {
        return groupIdList;
    }

    public void setGroupIdList(List<String> groupIdList) {
        this.groupIdList = groupIdList;
    }

    public List<String> getGroupNickNameList() {
        return groupNickNameList;
    }

    public void setGroupNickNameList(List<String> groupNickNameList) {
        this.groupNickNameList = groupNickNameList;
    }

    public int getRetryCount() {
        return retryCount;
    }

    public void setRetryCount(int retryCount) {
        this.retryCount = retryCount;
    }

    public int getMemberCount() {
        return memberCount;
    }

    public void setMemberCount(int memberCount) {
        this.memberCount = memberCount;
    }

    public List<JSONObject> getMemberList() {
        return memberList;
    }

    public void setMemberList(List<JSONObject> memberList) {
        this.memberList = memberList;
    }

    public List<JSONObject> getContactList() {
        return contactList;
    }

    public void setContactList(List<JSONObject> contactList) {
        this.contactList = contactList;
    }

    public List<JSONObject> getGroupList() {
        return groupList;
    }

    public void setGroupList(List<JSONObject> groupList) {
        this.groupList = groupList;
    }

    public Map<String, JSONArray> getGroupMemberMap() {
        return groupMemberMap;
    }

    public void setGroupMemberMap(Map<String, JSONArray> groupMemberMap) {
        this.groupMemberMap = groupMemberMap;
    }

    public List<JSONObject> getPublicUsersList() {
        return publicUsersList;
    }

    public void setPublicUsersList(List<JSONObject> publicUsersList) {
        this.publicUsersList = publicUsersList;
    }

    public List<JSONObject> getSpecialUsersList() {
        return specialUsersList;
    }

    public void setSpecialUsersList(List<JSONObject> specialUsersList) {
        this.specialUsersList = specialUsersList;
    }

    public Map<String, JSONObject> getUserInfoMap() {
        return userInfoMap;
    }

    public void setUserInfoMap(Map<String, JSONObject> userInfoMap) {
        this.userInfoMap = userInfoMap;
    }

    public boolean isUseHotReload() {
        return useHotReload;
    }

    public void setUseHotReload(boolean useHotReload) {
        this.useHotReload = useHotReload;
    }

    public String getHotReloadDir() {
        return hotReloadDir;
    }

    public void setHotReloadDir(String hotReloadDir) {
        this.hotReloadDir = hotReloadDir;
    }

    public int getReceivingRetryCount() {
        return receivingRetryCount;
    }

    public void setReceivingRetryCount(int receivingRetryCount) {
        this.receivingRetryCount = receivingRetryCount;
    }
}
