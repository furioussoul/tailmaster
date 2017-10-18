package esform.clazz.resource;

import esform.clazz.request.OperateClazzRequest;
import esform.dao.ClazzDao;
import esform.domain.Clazz;
import esform.response.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

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
    public Response classes(@RequestBody OperateClazzRequest request) {
        List<Clazz> classes = clazzDao.selectByExample(new Clazz(request.getName()));
        if (!CollectionUtils.isEmpty(classes)) {
            return Response.ok(classes.get(0));
        }
        return Response.ok();
    }
}