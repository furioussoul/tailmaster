package esform.wx.msgHandler;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import esform.domain.wx.BaseMsg;
import esform.util.HttpClient;
import esform.util.LogUtil;
import esform.wx.Core;
import org.apache.http.HttpEntity;
import org.apache.http.util.EntityUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by szj46941 on 2018/3/1.
 */
public class CommonMsgHandler implements IMsgHandlerFace{

    private static IMsgHandlerFace instance;

    public static IMsgHandlerFace getInstance(){
        if(null == instance){
            synchronized (CommonMsgHandler.class){
                if(null == instance){
                    instance = new CommonMsgHandler();
                }
            }
        }
        return instance;
    }

    @Override
    public String textMsgHandle(Core core, BaseMsg msg) {
        if (!msg.isGroupMsg()) { // 群消息不处理
            String result = "";
            String text = msg.getText();
            Map<String, String> paramMap = new HashMap<String, String>();
            paramMap.put("key", "be58ae6fbc144b3bb1c6089d4d66a643");
            paramMap.put("info", text);
            paramMap.put("userid", "222654");
            String paramStr = JSON.toJSONString(paramMap);
            try {
                HttpEntity entity = HttpClient.doPost("http://www.tuling123.com/openapi/api", paramStr);
                result = EntityUtils.toString(entity, "UTF-8");
                JSONObject obj = JSON.parseObject(result);
                if (obj.getString("code").equals("100000")) {
                    result = obj.getString("text");
                } else {
                    result = "处理有误";
                }
                return result;
            } catch (Exception e) {
               LogUtil.error("",e);
            }
        }
        return "";
    }

    @Override
    public String picMsgHandle(BaseMsg msg) {
        throw new UnsupportedOperationException();
    }

    @Override
    public String voiceMsgHandle(BaseMsg msg) {
        throw new UnsupportedOperationException();
    }

    @Override
    public String viedoMsgHandle(BaseMsg msg) {
        throw new UnsupportedOperationException();
    }

    @Override
    public String nameCardMsgHandle(BaseMsg msg) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void sysMsgHandle(BaseMsg msg) { // 收到系统消息
        throw new UnsupportedOperationException();
    }

    @Override
    public String verifyAddFriendMsgHandle(BaseMsg msg) {
        throw new UnsupportedOperationException();
    }

    @Override
    public String mediaMsgHandle(BaseMsg msg) {
        throw new UnsupportedOperationException();
    }
}
