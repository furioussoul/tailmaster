package esform.util;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by szj46941 on 2018/2/27.
 */
public class Config {

	public static final String API_WXAPPID = "API_WXAPPID";

	public static final String picDir = "D://itchat4j";
	public static final String VERSION = "1.2.18";
	public static final String BASE_URL = "https://login.weixin.qq.com";
	public static final String OS = "";
	public static final String DIR = "";
	public static final String DEFAULT_QR = "QR.jpg";
	public static final String USER_AGENT = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_11_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.71 Safari/537.36";

	public static final ArrayList<String> API_SPECIAL_USER = new ArrayList<String>(Arrays.asList("filehelper", "weibo",
			"qqmail", "fmessage", "tmessage", "qmessage", "qqsync", "floatbottle", "lbsapp", "shakeapp", "medianote",
			"qqfriend", "readerapp", "blogapp", "facebookapp", "masssendapp", "meishiapp", "feedsapp", "voip",
			"blogappweixin", "brandsessionholder", "weixin", "weixinreminder", "officialaccounts", "wxitil",
			"notification_messages", "wxid_novlwrv3lqwv11", "gh_22b87fa7cb3c", "userexperience_alarm"));

}
