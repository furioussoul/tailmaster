package esform.control.request;

import esform.request.BaseRequest;

/**
 * Created by
 *
 * @name:孙证杰
 * @email:20076581@qq.com on 2017/10/20.
 */
public class QueryControlRequest extends BaseRequest{
    private String controlName;
    private String controlClass;

    public String getControlName() {
        return controlName;
    }

    public void setControlName(String controlName) {
        this.controlName = controlName;
    }

    public String getControlClass() {
        return controlClass;
    }

    public void setControlClass(String controlClass) {
        this.controlClass = controlClass;
    }
}
