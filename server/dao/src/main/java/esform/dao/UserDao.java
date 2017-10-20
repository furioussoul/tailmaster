package esform.dao;

import esform.domain.User;

/**
 * Created by
 *
 * @name:孙证杰
 * @email:20076581@qq.com on 2017/9/17.
 */
public interface UserDao {
    int add(User user);

    User select(User example);
}
