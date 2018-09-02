package esform.espage.resource;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import esform.dao.PageDao;
import esform.domain.Page;
import esform.espage.PageServiceImpl;
import esform.espage.request.QueryPageRequest;
import esform.espage.request.OperatePageRequest;
import esform.global.request.RequestAsyncProcessHandler;
import esform.response.Response;
import esform.global.cache.RedisUtils;
import esform.util.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private final static Logger LOGGER = LoggerFactory.getLogger(PageResource.class);

    @Autowired
    private PageDao pageDao;

    @Autowired
    private PageServiceImpl pageService;

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
        OperatePageRequest request = new OperatePageRequest();
        request.setPageService(pageService);
        request.setId(id);
        request.setRowStatus(0);
        RequestAsyncProcessHandler requestAsyncProcessHandler = new RequestAsyncProcessHandler();
        requestAsyncProcessHandler.process(request);
        return Response.ok();
    }

    @PostMapping("update")
    @ResponseBody
    public Response update(@RequestBody OperatePageRequest request) {
        request.setPageService(pageService);
        RequestAsyncProcessHandler requestAsyncProcessHandler = new RequestAsyncProcessHandler();
        requestAsyncProcessHandler.process(request);
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
    public Response tablePageList(@RequestBody QueryPageRequest request) throws InterruptedException {
        Thread.sleep(1000);
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
    public Response richPage(@PathVariable("id") Long id) throws InterruptedException {
        QueryPageRequest request = new QueryPageRequest();
        request.setPageId(id);
        request.setPageService(pageService);
        RequestAsyncProcessHandler requestAsyncProcessHandler = new RequestAsyncProcessHandler();
        requestAsyncProcessHandler.process(request);

        long beginTime = System.currentTimeMillis();
        while (true){
            if(System.currentTimeMillis() - beginTime > 200){
                break;
            }

            Page example = new Page(id);
            Page localCache = pageService.getLocalCache(example);
            if(localCache != null){
                return Response.ok(localCache);
            }

            String resultStr = RedisUtils.REDIS_POOL.getResource().get("page$id:" + id);

            if(resultStr != null){
                return  Response.ok(JSON.toJSON(resultStr));
            }
            Thread.sleep(20);

        }

        List<Page> pages = pageDao.selectByExample(new Page(id));
        if (!CollectionUtils.isEmpty(pages)) {
            return Response.ok(pages.get(0));
        }
        return Response.ok();
    }
}
