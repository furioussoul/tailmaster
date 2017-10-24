package esform.domain;

/**
 * Created by
 *
 * @name:孙证杰
 * @email:200765821@qq.com on 2017/10/14.
 */
public class Page extends BaseDomain{
    private String name;
    private String pageSoul;

    public Page(Long id) {
        setId(id);
    }

    public Page() {
    }

    public Page(String name) {
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
