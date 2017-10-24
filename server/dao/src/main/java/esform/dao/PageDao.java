package esform.dao;

import esform.domain.Page;
import esform.request.BaseRequest;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by
 *
 * @name:孙证杰
 * @email:200765821@qq.com on 2017/10/14.
 */
@Mapper
public interface PageDao {

    int add(Page control);

    int del(@Param("id") Long id);

    int update(Page control);

    List<Page> select(BaseRequest request);

    List<Page> selectByExample(Page example);
}
