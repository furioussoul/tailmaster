package esform.wx;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import esform.enums.param.StorageLoginInfoEnum;
import esform.enums.URLEnum;
import esform.util.HttpClient;
import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * 微信小工具，如获好友列表等
 * 
 * @author https://github.com/yaphone
 * @date 创建时间：2017年5月4日 下午10:49:16
 * @version 1.0
 *
 */
public class WechatTools {
	private static Logger LOG = LoggerFactory.getLogger(WechatTools.class);

	/**
	 * 根据用户名发送文本消息
	 */
	public static void sendMsgByUserName(Core core, String msg, String toUserName) {
		MessageTools.sendMsgById(core, msg, toUserName);
	}

	/**
	 * <p>
	 * 通过RealName获取本次UserName
	 * </p>
	 * <p>
	 * 如NickName为"yaphone"，则获取UserName=
	 * "@1212d3356aea8285e5bbe7b91229936bc183780a8ffa469f2d638bf0d2e4fc63"，
	 * 可通过UserName发送消息
	 * </p>
	 */
	public static String getUserNameByNickName(Core core,String nickName) {
		for (JSONObject o : core.getContactList()) {
			if (o.getString("NickName").equals(nickName)) {
				return o.getString("UserName");
			}
		}
		return null;
	}

	/**
	 * 返回好友昵称列表
	 * 
	 * @author https://github.com/yaphone
	 * @date 2017年5月4日 下午11:37:20
	 * @return
	 */
	public static List<String> getContactNickNameList(Core core) {
		List<String> contactNickNameList = new ArrayList<String>();
		for (JSONObject o : core.getContactList()) {
			contactNickNameList.add(o.getString("NickName"));
		}
		return contactNickNameList;
	}

	/**
	 * 返回好友完整信息列表
	 */
	public static List<JSONObject> getContactList(Core core) {
		return core.getContactList();
	}

	/**
	 * 返回群列表
	 */
	public static List<JSONObject> getGroupList(Core core) {
		return core.getGroupList();
	}

	/**
	 * 获取群ID列表
	 * 
	 * @date 2017年6月21日 下午11:42:56
	 * @return
	 */
	public static List<String> getGroupIdList(Core core) {
		return core.getGroupIdList();
	}

	/**
	 * 获取群NickName列表
	 */
	public static List<String> getGroupNickNameList(Core core) {
		return core.getGroupNickNameList();
	}

	/**
	 * 根据groupIdList返回群成员列表
	 */
	public static JSONArray getMemberListByGroupId(Core core, String groupId) {
		return core.getGroupMemberMap().get(groupId);
	}

	/**
	 * 退出微信
	 */
	public static void logout(Core core) {
		webWxLogout(core);
	}

	private static boolean webWxLogout(Core core) {
		String url = String.format(URLEnum.WEB_WX_LOGOUT.url(),
				core.getLoginInfo().get(StorageLoginInfoEnum.URL.key()));
		List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
		params.add(new BasicNameValuePair("redirect", "1"));
		params.add(new BasicNameValuePair("type", "1"));
		params.add(
				new BasicNameValuePair("skey", (String) core.getLoginInfo().get(StorageLoginInfoEnum.S_KEY.key())));
		try {
			HttpClient.doGet(url, params, false, null);
			return true;
		} catch (Exception e) {
			LOG.debug(e.getMessage());
		}
		return false;
	}

	public static void setUserInfo(Core core) {
		for (JSONObject o : core.getContactList()) {
			core.getUserInfoMap().put(o.getString("NickName"), o);
			core.getUserInfoMap().put(o.getString("UserName"), o);
		}
	}

	/**
	 * 获取微信在线状态
	 */
	public static boolean getWechatStatus(Core core) {
		return core.isAlive();
	}
}
