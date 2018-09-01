package esform.filter;

import com.alibaba.fastjson.JSON;
import esform.dao.UserDao;
import esform.domain.User;
import esform.response.Response;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by
 *
 * @name:孙证杰
 * @email:200765821@qq.com on 2017/9/17.
 */
@Component
public class OauthFilter implements Filter {

    private static Logger LOGGER = LoggerFactory.getLogger(OauthFilter.class);
    private static ThreadLocal<Map<String, Object>> LOCAL_VARS = new ThreadLocal<Map<String, Object>>();

    @Autowired
    private UserDao userDao;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        String uri = ((HttpServletRequest) request).getRequestURI();
        if (uri.contains("login")) {
            chain.doFilter(request, response);
            return;
        }
        if (uri.contains("register")) {
            chain.doFilter(request, response);
            return;
        }
        if (uri.contains("clear_access_token")) {
            chain.doFilter(request, response);
            return;
        }
        if (uri.contains("reset_pwd")) {
            chain.doFilter(request, response);
            return;
        }

        String accessToken = null;
        Cookie[] cookies = ((HttpServletRequest) request).getCookies();//这样便可以获取一个cookie数组
        for (Cookie cookie : cookies) {
            if (StringUtils.equals(cookie.getName(), "access_token")) {
                accessToken = cookie.getValue();
                break;
            }
        }

        if (StringUtils.isEmpty(accessToken)) {
            unAuthenticated(response);
            return;
        }

        String[] split = accessToken.split("@");

        User select = userDao.select(new User(split[0]));

        if (null == select || !StringUtils.equals(select.getPassword(), split[1])) {
            unAuthenticated(response);
            return;
        }

        setVar("user", select);
        chain.doFilter(request, response);
    }

    private void unAuthenticated(ServletResponse response) throws IOException {
        LOGGER.warn("用户没有凭证");
        PrintWriter writer = response.getWriter();
        writer.print(JSON.toJSON(Response.unAuthenticated("用户没有凭证")));
        writer.flush();
    }

    private static void setVar(String key, Object obj) {
        Map<String, Object> stringObjectMap = LOCAL_VARS.get();
        if (null == stringObjectMap) {
            stringObjectMap = new HashMap<>();
            LOCAL_VARS.set(stringObjectMap);
        }
        stringObjectMap.put(key, obj);
    }

    public static User getUser() {
        Map<String, Object> stringObjectMap = LOCAL_VARS.get();
        User user = (User) stringObjectMap.get("user");
        if (user == null) {
            throw new RuntimeException("unauthorized");
        }
        return user;
    }

    public static Object getVar(String key) {
        Map<String, Object> stringObjectMap = LOCAL_VARS.get();
        return null != stringObjectMap ? stringObjectMap.get(key) : null;
    }

    @Override
    public void destroy() {
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }
}
