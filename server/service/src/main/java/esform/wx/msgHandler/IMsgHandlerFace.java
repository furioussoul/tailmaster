package esform.wx.msgHandler;

import esform.domain.wx.BaseMsg;
import esform.wx.Core;

/**
 * 消息处理接口
 */
public interface IMsgHandlerFace {

    String textMsgHandle(Core core, BaseMsg msg);

    String picMsgHandle(BaseMsg msg);

    String voiceMsgHandle(BaseMsg msg);

    String viedoMsgHandle(BaseMsg msg);

    String nameCardMsgHandle(BaseMsg msg);

    void sysMsgHandle(BaseMsg msg);

    String verifyAddFriendMsgHandle(BaseMsg msg);

    String mediaMsgHandle(BaseMsg msg);
}
