package esform.util;

import esform.domain.BaseDomain;
import esform.domain.User;
import esform.filter.OauthFilter;

import java.util.Date;

/**
 * Created by
 *
 * @name:孙证杰
 * @email:200765821@qq.com on 2017/10/28.
 */
public class Util {

    public static void trace(BaseDomain domain, boolean isCreate){
        User user = OauthFilter.getUser();
        if(null == user){
            throw new RuntimeException("not authorized");
        }
        Date now = new Date();
        if(isCreate){
            domain.setCreateBy(user.getUserName());
            domain.setCreateDt(now);
        }
        domain.setUpdateBy(user.getUserName());
        domain.setUpdateDt(now);
    }
}
