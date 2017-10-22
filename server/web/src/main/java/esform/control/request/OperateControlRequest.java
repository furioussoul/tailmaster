package esform.control.request;

import esform.domain.Control;

/**
 * Created by
 *
 * @name:孙证杰
 * @email:200765821@qq.com on 2017/9/17.
 */
public class OperateControlRequest {

    private Long id;
    private String name;
    private String nickname;
    private String code;
    private Long clazzId;
    private Integer sort;

    public Control getDomain() {
        Control control = new Control();
        control.setId(id);
        control.setName(name);
        control.setNickname(nickname);
        control.setCode(code);
        control.setClazzId(clazzId != null ? clazzId : 6 );
        control.setSort(sort != null ? sort : -100);
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

    public Long getClazzId() {
        return clazzId;
    }

    public void setClazzId(Long clazzId) {
        this.clazzId = clazzId;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }
}
