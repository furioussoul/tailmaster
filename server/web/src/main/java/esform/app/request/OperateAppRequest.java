package esform.app.request;

import esform.domain.App;

/**
 * Created by
 *
 * @name:孙证杰
 * @email:200765821@qq.com on 2017/10/24.
 */
public class OperateAppRequest {

    private Long id;
    private String name;
    private Integer sort;

    public App getDomain() {
        App app = new App();
        app.setId(id);
        app.setName(name);
        app.setSort(sort);
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

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }
}
