package esform.domain;

/**
 * Created by
 *
 * @name:孙证杰
 * @email:200765821@qq.com on 2017/10/18.
 */
public class Clazz extends BaseDomain {
    private String name;
    private Integer type;
    private Integer sort;

    public Clazz(String name) {
        this.name = name;
    }

    public Clazz() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }
}
