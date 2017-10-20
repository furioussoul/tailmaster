package esform.clazz.resource;

import esform.dao.ClazzDao;
import esform.domain.Clazz;
import esform.response.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by
 *
 * @name:孙证杰
 * @email:20076581@qq.com on 2017/10/18.
 */
@Controller
@RequestMapping("class")
class ClassResource {
    @Autowired
    private ClazzDao clazzDao;

    @PostMapping("classList")
    @ResponseBody
    public Response classes() {
        return Response.ok(clazzDao.selectByExample(new Clazz()));
    }
}