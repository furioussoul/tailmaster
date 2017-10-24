package esform.page.resource;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import esform.domain.Page;
import esform.page.request.OperatePageRequest;
import esform.page.request.QueryPageRequest;
import esform.dao.PageDao;
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
 * @email:200765821@qq.com on 2017/10/14.
 */
@Controller
@RequestMapping("page")
public class PageResource {
    @Autowired
    private PageDao pageDao;

    @PostMapping("add")
    @ResponseBody
    public Response add(@RequestBody OperatePageRequest request) {
        Page domain = request.getDomain();
        pageDao.add(domain);
        return Response.ok(domain);
    }

    @GetMapping("del/{id}")
    @ResponseBody
    public Response del(@PathVariable("id") Long id) {
        pageDao.del(id);
        return Response.ok();
    }

    @PostMapping("update")
    @ResponseBody
    public Response update(@RequestBody OperatePageRequest request) {
        Page domain = request.getDomain();
        pageDao.update(domain);
        return Response.ok();
    }

    @PostMapping("pageList")
    @ResponseBody
    public Response pageList(@RequestBody QueryPageRequest request) {
        Page page = new Page(request.getName());
        page.setAppId(request.getAppId());
        List<Page> pages = pageDao.selectByExample(page);
        return Response.ok(pages);
    }

    @PostMapping("tablePageList")
    @ResponseBody
    public Response tablePageList(@RequestBody QueryPageRequest request) {
        PageInfo<Page> pages = PageHelper
                .startPage(request.getPageNum(), request.getPageSize())
                .doSelectPageInfo(() -> {
                    pageDao.select(request);
                });
        return Response.ok(pages);
    }

    @GetMapping("richPage/{id}")
    @ResponseBody
    public Response richPage(@PathVariable("id") Long id) {
        List<Page> pages = pageDao.selectByExample(new Page(id));
        if (!CollectionUtils.isEmpty(pages)) {
            return Response.ok(pages.get(0));
        }
        return Response.ok();
    }
}
