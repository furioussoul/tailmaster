package esform.app.request;

import esform.request.BaseRequest;

/**
 * Created by
 *
 * @name:孙证杰
 * @email:200765821@qq.com on 2017/10/24.
 */
public class QueryAppRequest extends BaseRequest {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
