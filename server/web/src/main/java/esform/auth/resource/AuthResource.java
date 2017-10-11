package esform.auth.resource;

import esform.auth.request.AuthRequest;
import esform.dao.RoleDao;
import esform.dao.UserDao;
import esform.domain.Role;
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

import java.util.ArrayList;
import java.util.List;

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
    @Autowired
    private RoleDao roleDao;

    @PostMapping("register")
    @ResponseBody
    @Transactional
    public Response register(@RequestBody AuthRequest request) {
        User user = request.getDomain();
        userDao.add(user);
        userDao.addUserRole(user.getId(), 1L);//1是管理员
        List<Role> roleList = new ArrayList<>();
        Role role = new Role();
        role.setRoleName("游客");
        role.setId(1L);
        roleList.add(role);
        user.setRoleList(roleList);
        return Response.ok(user);
    }

    @PostMapping("login")
    @ResponseBody
    public Response login(@RequestBody AuthRequest request) {
        User user = request.getDomain();
        User select = userDao.select(user);
        if (!StringUtils.equals(select.getPassword(), user.getPassword())) {
            return Response.unAuthenticated();
        }
        return Response.ok(select);
    }
}

