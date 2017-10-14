package esform.dao;

import esform.domain.App;
import esform.request.BaseRequest;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by
 *
 * @name:孙证杰
 * @email:20076581@qq.com on 2017/10/14.
 */
@Mapper
public interface AppDao {

    int add(App control);

    int del(@Param("id") Long id);

    int update(App control);

    List<App> select(BaseRequest request);

    List<App> selectByExample(App example);
}
