package esform.wx;

import com.alibaba.fastjson.JSON;
import esform.enums.URLEnum;
import esform.util.HttpClient;
import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 消息处理类
 */
public class MessageTools {
	private static Logger LOG = LoggerFactory.getLogger(MessageTools.class);

	/**
	 * 根据UserName发送文本消息
	 */
	private static void sendMsg(Core core, String text, String toUserName) {
		if (text == null) {
			return;
		}
		LOG.info(String.format("发送消息 %s: %s", toUserName, text));
		webWxSendMsg(core,1, text, toUserName);
	}

	/**
	 * 根据ID发送文本消息
	 */
	public static void sendMsgById(Core core,String text, String id) {
		if (text == null) {
			return;
		}
		sendMsg(core,text, id);
	}

	/**
	 * 根据NickName发送文本消息
	 */
	public static boolean sendMsgByNickName(Core core,String text, String nickName) {
		if (nickName != null) {
			String toUserName = WechatTools.getUserNameByNickName(core,nickName);
			if (toUserName != null) {
				webWxSendMsg(core,1, text, toUserName);
				return true;
			}
		}
		return false;

	}

	/**
	 * 消息发送
	 */
	public static void webWxSendMsg(Core core, int msgType, String content, String toUserName) {
		String url = String.format(URLEnum.WEB_WX_SEND_MSG.url(), core.getLoginInfo().get("url"));
		Map<String, Object> msgMap = new HashMap<String, Object>();
		msgMap.put("Type", msgType);
		msgMap.put("Content", content);
		msgMap.put("FromUserName", core.getUserName());
		msgMap.put("ToUserName", toUserName == null ? core.getUserName() : toUserName);
		msgMap.put("LocalID", new Date().getTime() * 10);
		msgMap.put("ClientMsgId", new Date().getTime() * 10);
		Map<String, Object> paramMap = core.getParamMap();
		paramMap.put("Msg", msgMap);
		paramMap.put("Scene", 0);
		try {
			String paramStr = JSON.toJSONString(paramMap);
			HttpEntity entity = HttpClient.doPost(url, paramStr);
			EntityUtils.toString(entity, Consts.UTF_8);
		} catch (Exception e) {
			LOG.error("webWxSendMsg", e);
		}
	}
}
