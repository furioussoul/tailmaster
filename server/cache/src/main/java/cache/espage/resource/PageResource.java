package cache.espage.resource;

import esform.domain.Page;
import esform.espage.PageServiceImpl;
import esform.response.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by
 *
 * @name:孙证杰
 * @email:200765821@qq.com on 2017/10/14.
 */
@Controller
@RequestMapping("cache_page")
public class PageResource {

    private final static Logger LOGGER = LoggerFactory.getLogger(PageResource.class);

    @Autowired
    private PageServiceImpl pageService;

    @RequestMapping("/testPutCache")
    @ResponseBody
    public Response testPutCache(Page page) {
        pageService.putLocalCache(page);
        return Response.ok();
    }

    @RequestMapping("/testGetCache")
    @ResponseBody
    public Response testGetCache(Long id) {
        Page example = new Page(id);
        return Response.ok(pageService.get(example));
    }
}
