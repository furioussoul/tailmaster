package esform.dao;

import esform.domain.App;
import esform.request.BaseRequest;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by
 *
 * @name:孙证杰
 * @email:200765821@qq.com on 2017/10/24.
 */
public interface AppDao {
    int add(App app);

    int del(@Param("id") Long id);

    int update(App app);

    List<App> select(BaseRequest request);

    List<App> selectByExample(App example);
}
