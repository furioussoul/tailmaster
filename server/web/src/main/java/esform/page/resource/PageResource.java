package esform.page.resource;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import esform.dao.PageDao;
import esform.domain.Page;
import esform.page.request.OperatePageRequest;
import esform.page.request.QueryPageRequest;
import esform.response.Response;
import esform.util.Util;
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
        Util.trace(domain, true);
        pageDao.add(domain);
        return Response.ok(domain);
    }

    @GetMapping("del/{id}")
    @ResponseBody
    public Response del(@PathVariable("id") Long id) {
        Page domain = new Page(id);
        Util.trace(domain, false);
        Util.trace(domain, true);
        pageDao.del(domain);
        return Response.ok();
    }

    @PostMapping("update")
    @ResponseBody
    public Response update(@RequestBody OperatePageRequest request) {
        Page domain = request.getDomain();
        Util.trace(domain, false);
        Util.trace(domain, true);
        pageDao.update(domain);
        return Response.ok();
    }

    @PostMapping("pageList")
    @ResponseBody
    public Response pageList(@RequestBody QueryPageRequest request) {
        Page page = new Page(request.getName());
        page.setAppName(request.getAppName());
        page.setName(request.getName());
        List<Page> pages = pageDao.selectByExample(page);

        pages.forEach(page1 -> {
            page1.setPageSoul("");
        });

        return Response.ok(pages);
    }

    @PostMapping("tablePageList")
    @ResponseBody
    public Response tablePageList(@RequestBody QueryPageRequest request) {

        if (request.getAll()) {
            return Response.ok(pageDao.select(request));
        }

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
