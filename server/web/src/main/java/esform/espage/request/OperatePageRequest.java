package esform.espage.request;

import esform.domain.Page;
import esform.espage.PageServiceImpl;
import esform.global.request.Request;
import esform.global.cache.RedisUtils;
import esform.util.Util;

/**
 * Created by
 *
 * @name:孙证杰
 * @email:200765821@qq.com on 2017/10/14.
 */
public class OperatePageRequest implements Request{

    private PageServiceImpl pageService;

    private Long id;
    private String name;
    private String pageSoul;
    private Long appId;
    private Integer rowStatus;

    public Page getDomain() {
        Page page = new Page();
        page.setId(id);
        page.setName(name);
        page.setPageSoul(pageSoul);
        page.setAppId(appId);
        page.setRowStatus(rowStatus);
        return page;
    }

    public Long getId() {
        return id;
    }

    public void setPageService(PageServiceImpl pageService) {
        this.pageService = pageService;
    }

    @Override
    public void process() throws InterruptedException {
        try{
            RedisUtils.remove(() -> {
                Page page = getDomain();
                Util.trace(page,true);
                Util.trace(page,false);
                pageService.putLocalCache(page);
                return pageService.update(page);
            },"page$id：" + id.toString());
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPageSoul() {
        return pageSoul;
    }

    public void setPageSoul(String pageSoul) {
        this.pageSoul = pageSoul;
    }

    public Long getAppId() {
        return appId;
    }

    public void setAppId(Long appId) {
        this.appId = appId;
    }

    public Integer getRowStatus() {
        return rowStatus;
    }

    public void setRowStatus(Integer rowStatus) {
        this.rowStatus = rowStatus;
    }
}
