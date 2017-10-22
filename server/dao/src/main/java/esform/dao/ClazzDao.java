package esform.dao;

import esform.domain.Clazz;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by
 *
 * @name:孙证杰
 * @email:200765821@qq.com on 2017/10/18.
 */
@Mapper
public interface ClazzDao {

    int add(Clazz clazz);

    int del(@Param("id") Long id);

    int update(Class clazz);

    List<Clazz> selectByExample(Clazz clazz);
}
