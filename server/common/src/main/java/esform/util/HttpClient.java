package esform.util;

import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 模拟浏览器的http请求
 * Created by szj46941 on 2018/2/27.
 */
public class HttpClient {

    private static CookieStore COOKIE_STORE;//存放模拟浏览器的cookie
    private static CloseableHttpClient HTTP_CLIENT;

    static {
        COOKIE_STORE = new BasicCookieStore();
        HTTP_CLIENT = HttpClients.custom().setDefaultCookieStore(COOKIE_STORE).build();
    }

    public static HttpEntity doGet(String url, List<BasicNameValuePair> params, boolean redirect,
                            Map<String, String> headerMap) {
        HttpEntity entity = null;
        HttpGet httpGet = new HttpGet();

        try {
            if (params != null) {
                String paramStr = EntityUtils.toString(new UrlEncodedFormEntity(params, Consts.UTF_8));
                httpGet = new HttpGet(url + "?" + paramStr);
            } else {
                httpGet = new HttpGet(url);
            }
            if (!redirect) {
                httpGet.setConfig(RequestConfig.custom().setRedirectsEnabled(false).build()); // 禁止重定向
            }
            //模拟浏览器的请求
            httpGet.setHeader("User-Agent", Config.USER_AGENT);
            if (headerMap != null) {
                Set<Map.Entry<String, String>> entries = headerMap.entrySet();
                for (Map.Entry<String, String> entry : entries) {
                    httpGet.setHeader(entry.getKey(), entry.getValue());
                }
            }

            CloseableHttpResponse response = HTTP_CLIENT.execute(httpGet);
            entity = response.getEntity();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return entity;
    }

    public static HttpEntity doPost(String url, String paramsStr) {
        HttpEntity entity = null;
        HttpPost httpPost = new HttpPost();
        try {
            StringEntity params = new StringEntity(paramsStr, Consts.UTF_8);
            httpPost = new HttpPost(url);
            httpPost.setEntity(params);
            httpPost.setHeader("Content-type", "application/json; charset=utf-8");
            httpPost.setHeader("User-Agent", Config.USER_AGENT);
            CloseableHttpResponse response = HTTP_CLIENT.execute(httpPost);
            entity = response.getEntity();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return entity;
    }
}
