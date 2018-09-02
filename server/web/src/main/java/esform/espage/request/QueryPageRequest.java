package esform.espage.request;

import com.alibaba.fastjson.TypeReference;
import esform.domain.Page;
import esform.espage.PageServiceImpl;
import esform.global.request.Request;
import esform.request.BaseRequest;
import esform.global.cache.RedisUtils;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * Created by
 *
 * @name:孙证杰
 * @email:200765821@qq.com on 2017/10/20.
 */
public class QueryPageRequest extends BaseRequest implements Request{

    private String name;
    private String appName;
    private Long appId;
    private Long pageId;
    private boolean all;

    private PageServiceImpl pageService;

    public void setPageService(PageServiceImpl pageService) {
        this.pageService = pageService;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public Long getAppId() {
        return appId;
    }

    public void setAppId(Long appId) {
        this.appId = appId;
    }

    public Long getPageId() {
        return pageId;
    }

    public void setPageId(Long pageId) {
        this.pageId = pageId;
    }

    public boolean getAll() {
        return all;
    }

    public void setAll(boolean all) {
        this.all = all;
    }

    @Override
    public Long getId() {
        return pageId;
    }

    @Override
    public void process() {
        try {
            Page example = new Page(pageId);
            RedisUtils.get(() -> {
                return pageService.get(example);
            }, new TypeReference<Page>() {
            }, "page$id:" + pageId, 3600000);
        } catch (Exception ex){
            ex.printStackTrace();
        }
    }
}
