package esform.control.resource;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import esform.control.request.OperateControlRequest;
import esform.dao.ControlDao;
import esform.domain.Control;
import esform.request.BaseRequest;
import esform.response.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by
 *
 * @name:孙证杰
 * @email:20076581@qq.com on 2017/9/17.
 */
@Controller
@RequestMapping("control")
public class ControlResource {
    @Autowired
    private ControlDao controlDao;

    @PostMapping("add")
    @ResponseBody
    public Response add(@RequestBody OperateControlRequest request) {
        Control domain = request.getDomain();
        controlDao.add(domain);
        return Response.ok();
    }

    @GetMapping("del/{id}")
    @ResponseBody
    public Response del(@PathVariable("id") Long id) {
        controlDao.del(id);
        return Response.ok();
    }

    @PostMapping("updateProps")
    @ResponseBody
    public Response updateProps(@RequestBody OperateControlRequest request) {
        //// TODO: 2017/10/18  
        Control domain = request.getDomain();
        controlDao.update(domain);
        return Response.ok();
    }

    @PostMapping("update")
    @ResponseBody
    public Response update(@RequestBody OperateControlRequest request) {
        Control domain = request.getDomain();
        controlDao.update(domain);
        return Response.ok();
    }

    @PostMapping("tableControlList")
    @ResponseBody
    public Response tableControlList(@RequestBody BaseRequest request) {
        PageInfo<Control> controls = PageHelper
                .startPage(request.getPageNum(), request.getPageSize())
                .doSelectPageInfo(() -> {
                    controlDao.select(request);
                });
        return Response.ok(controls);
    }

    @PostMapping("controlList")
    @ResponseBody
    public Response controlList() {
        return Response.ok(controlDao.selectByExample(new Control()));
    }

    @GetMapping("richControl/{id}")
    @ResponseBody
    public Response richControl(@PathVariable("id") Long id) {
        List<Control> controls = controlDao.selectByExample(new Control(id));
        if (!CollectionUtils.isEmpty(controls)) {
            return Response.ok(controls.get(0));
        }
        return Response.ok();
    }
}
