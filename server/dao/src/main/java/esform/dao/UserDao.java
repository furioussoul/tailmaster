package esform.dao;

import esform.domain.User;
import org.apache.ibatis.annotations.Param;

/**
 * Created by
 *
 * @name:孙证杰
 * @email:20076581@qq.com on 2017/9/17.
 */
public interface UserDao {
    int add(User user);

    int addUserRole(@Param("userId") Long userId, @Param("roleId") Long roleId);

    User select(User example);
}
