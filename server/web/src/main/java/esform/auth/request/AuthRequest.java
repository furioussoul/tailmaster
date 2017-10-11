package esform.auth.request;

import esform.domain.User;
import org.hibernate.validator.constraints.NotBlank;

/**
 * Created by
 *
 * @name:孙证杰
 * @email:20076581@qq.com on 2017/9/17.
 */
public class AuthRequest {

    private Long id;
    @NotBlank
    private String userName;
    @NotBlank
    private String password;
    private String realName;
    private String phone;
    private String email;
    private String profession;

    public User getDomain() {
        User user = new User();
        user.setId(id);
        user.setUserName(userName);
        user.setPassword(password);
        user.setRealName(realName);
        user.setPhone(phone);
        user.setEmail(email);
        user.setProfession(profession);
        return user;
    }
}