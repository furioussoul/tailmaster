package esform.espage.request;

import com.alibaba.fastjson.TypeReference;
import esform.dao.PageDao;
import esform.domain.Page;
import esform.global.request.Request;
import esform.request.BaseRequest;
import esform.response.Response;
import esform.util.RedisUtils;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.concurrent.Callable;

/**
 * Created by
 *
 * @name:孙证杰
 * @email:200765821@qq.com on 2017/10/20.
 */
public class QueryPageRequest extends BaseRequest implements Request{
    private PageDao pageDao;

    private String name;
    private String appName;
    private Long appId;
    private Long pageId;
    private boolean all;

    public void setPageDao(PageDao pageDao) {
        this.pageDao = pageDao;
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
            RedisUtils.get(() -> {
                List<Page> pages = pageDao.selectByExample(new Page(pageId));
                if (!CollectionUtils.isEmpty(pages)) {
                    return pages.get(0);
                }
                return null;
            }, new TypeReference<Page>() {
            }, "page$id:" + pageId, 3600000);
        } catch (Exception ex){
            ex.printStackTrace();
        }
    }
}
