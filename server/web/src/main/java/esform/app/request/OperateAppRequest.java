package esform.app.request;

import esform.domain.App;

/**
 * Created by
 *
 * @name:孙证杰
 * @email:20076581@qq.com on 2017/10/14.
 */
public class OperateAppRequest {

    private Long id;
    private String name;
    private String pageSoul;

    public App getDomain() {
        App app = new App();
        app.setId(id);
        app.setName(name);
        app.setPageSoul(pageSoul);
        return app;
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
