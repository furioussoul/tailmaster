package esform.page.request;

import esform.domain.Page;

/**
 * Created by
 *
 * @name:孙证杰
 * @email:200765821@qq.com on 2017/10/14.
 */
public class OperatePageRequest {

    private Long id;
    private String name;
    private String pageSoul;

    public Page getDomain() {
        Page page = new Page();
        page.setId(id);
        page.setName(name);
        page.setPageSoul(pageSoul);
        return page;
    }

    public Long getId() {
        return id;
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
}
