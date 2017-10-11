package esform.response;

/**
 * 统一接口返回值状态
 * return Response.ok(Status.ok);
 *
 * @author 孙证杰
 * @version v0.0.1: Status.java, v 0.1 2017年3月22日 下午2:25:19 孙证杰 Exp $
 */
public enum Status {

    ok(10000, "ok"),
    fail(20000, "fail"),
    unAuthenticated(405, "unAuthenticated");

    private int code;

    private String msg;

    Status(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
