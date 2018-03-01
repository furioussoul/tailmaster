package esform.wx.msgHandler;

import esform.domain.wx.BaseMsg;
import esform.util.LogUtil;
import esform.wx.Core;

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
            LogUtil.info(msg.getText());
            return msg.getText();
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
