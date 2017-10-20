package esform.app.resource;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import esform.app.request.OperateAppRequest;
import esform.app.request.QueryAppRequest;
import esform.dao.AppDao;
import esform.domain.App;
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
 * @email:20076581@qq.com on 2017/10/14.
 */
@Controller
@RequestMapping("app")
public class AppResource {
    @Autowired
    private AppDao appDao;

    @PostMapping("add")
    @ResponseBody
    public Response add(@RequestBody OperateAppRequest request) {
        App domain = request.getDomain();
        appDao.add(domain);
        return Response.ok();
    }

    @GetMapping("del/{id}")
    @ResponseBody
    public Response del(@PathVariable("id") Long id) {
        appDao.del(id);
        return Response.ok();
    }

    @PostMapping("update")
    @ResponseBody
    public Response update(@RequestBody OperateAppRequest request) {
        App domain = request.getDomain();
        appDao.update(domain);
        return Response.ok();
    }

    @PostMapping("appList")
    @ResponseBody
    public Response appList(@RequestBody OperateAppRequest request) {
        List<App> apps = appDao.selectByExample(new App(request.getName()));
        if (!CollectionUtils.isEmpty(apps)) {
            return Response.ok(apps.get(0));
        }
        return Response.ok();
    }

    @PostMapping("tableAppList")
    @ResponseBody
    public Response tableAppList(@RequestBody QueryAppRequest request) {
        PageInfo<App> apps = PageHelper
                .startPage(request.getPageNum(), request.getPageSize())
                .doSelectPageInfo(() -> {
                    appDao.select(request);
                });
        return Response.ok(apps);
    }

    @GetMapping("richApp/{id}")
    @ResponseBody
    public Response richApp(@PathVariable("id") Long id) {
        List<App> apps = appDao.selectByExample(new App(id));
        if (!CollectionUtils.isEmpty(apps)) {
            return Response.ok(apps.get(0));
        }
        return Response.ok();
    }
}
