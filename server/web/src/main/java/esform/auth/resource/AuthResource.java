package esform.auth.resource;

import esform.auth.request.AuthRequest;
import esform.dao.UserDao;
import esform.domain.User;
import esform.response.Response;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by
 *
 * @name:孙证杰
 * @email:20076581@qq.com on 2017/9/17.
 */
@Controller
@RequestMapping("auth")
public class AuthResource {

    @Autowired
    private UserDao userDao;

    @PostMapping("register")
    @ResponseBody
    @Transactional
    public Response register(@RequestBody AuthRequest request) {
        User user = request.getDomain();
        userDao.add(user);
        return Response.ok();
    }

    @PostMapping("login")
    @ResponseBody
    public Response login(@RequestBody AuthRequest request) {
        User user = request.getDomain();
        User select = userDao.select(user);
        if (!StringUtils.equals(select.getPassword(), user.getPassword())) {
            return Response.unAuthenticated();
        }
        return Response.ok();
    }
}

