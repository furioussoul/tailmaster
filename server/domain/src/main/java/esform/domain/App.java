package esform.domain;

/**
 * Created by
 *
 * @name:孙证杰
 * @email:20076581@qq.com on 2017/10/14.
 */
public class App extends BaseDomain{
    private String name;
    private String pageSoul;

    public App(Long id) {
        setId(id);
    }

    public App() {
    }

    public App(String name) {
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
