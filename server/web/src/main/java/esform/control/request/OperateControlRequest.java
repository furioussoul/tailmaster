package esform.control.request;

import esform.domain.Control;

/**
 * Created by
 *
 * @name:孙证杰
 * @email:20076581@qq.com on 2017/9/17.
 */
public class OperateControlRequest {

    private Long id;
    private String name;
    private String nickname;
    private String code;

    public Control getDomain() {
        Control control = new Control();
        control.setId(id);
        control.setName(name);
        control.setNickname(nickname);
        control.setCode(code);
        return control;
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

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
