package esform.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by
 *
 * @name:孙证杰
 * @email:20076581@qq.com on 2017/9/17.
 */
@Component
public class SAuthFilter implements Filter {

    private static Logger LOGGER = LoggerFactory.getLogger(SAuthFilter.class);
    private static ThreadLocal<Map<String, Object>> LOCAL_VARS = new ThreadLocal<Map<String, Object>>();

    private static void setVar(String key, Object obj) {
        Map<String, Object> stringObjectMap = LOCAL_VARS.get();
        if (null == stringObjectMap) {
            stringObjectMap = new HashMap<>();
            LOCAL_VARS.set(stringObjectMap);
        }
        stringObjectMap.put(key, obj);
    }

    public static Object getVar(String key) {
        Map<String, Object> stringObjectMap = LOCAL_VARS.get();
        return null != stringObjectMap ? stringObjectMap.get(key) : null;
    }

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
        if (uri.contains("reset_pwd")) {
            chain.doFilter(request, response);
            return;
        }
//
//        User auth = null;
//        String credential = ((HttpServletRequest) request).getHeader("X-AUTH-TOKEN");
//        if (StringUtils.isEmpty(credential)) {
//            LOGGER.warn("用户没有凭证");
//            PrintWriter writer = response.getWriter();
//            writer.print(JSON.toJSON(Response.unAuthenticated()));
//            writer.flush();
//            return;
//        }
//            User userConfirmed = userService.checkUserCredential(auth);
//            if (userConfirmed == null) {
//                LOGGER.warn("用户没有凭证");
//                throw new RuntimeException("用户没有凭证");
//            }

//        auth = new ObjectMapper().readValue(credential, User.class);
//        setVar("auth", auth);
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }
}
