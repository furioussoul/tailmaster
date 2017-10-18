package esform.domain;

import com.sun.org.apache.xpath.internal.operations.String;

/**
 * Created by
 *
 * @name:孙证杰
 * @email:20076581@qq.com on 2017/10/18.
 */
public class Clazz extends BaseDomain{
    private String name;
    private Integer type;

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
}
