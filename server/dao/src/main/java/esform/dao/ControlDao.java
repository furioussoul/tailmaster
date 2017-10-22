package esform.dao;

import esform.domain.Control;
import esform.request.BaseRequest;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by
 *
 * @name:孙证杰
 * @email:200765821@qq.com on 2017/9/17.
 */
@Mapper
public interface ControlDao {

    int add(Control control);

    int del(@Param("id") Long id);

    int update(Control control);

    List<Control> select(BaseRequest request);

    List<Control> selectByExample(Control example);
}
