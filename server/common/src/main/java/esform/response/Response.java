package esform.response;

import java.util.LinkedList;

/**
 * 统一接口返回值
 * return Response.ok();
 *
 * @author 孙证杰
 * @version v0.0.1: Response.java, v 0.1 2017年3月22日 下午2:25:19 孙证杰 Exp $
 */
public class Response {

    private int code;

    private Object msg;

    private Object data;

    private Response(int code, Object msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    private Response(int code, Object msg) {
        this(code, msg, null);
    }

    private Response() {

    }

    public static Response unAuthenticated(String msg) {
        return ok(Status.unAuthenticated, msg);
    }

    public static Response fail() {
        return ok(Status.fail);
    }

    public static Response fail(Object data) {
        return ok(Status.fail, data);
    }

    public static Response ok() {
        return new Response(Status.ok.getCode(), Status.ok.getMsg());
    }

    public static Response ok(Object data) {
        return ok(Status.ok, data);
    }

    public static Response ok(Status status) {
        return new Response(status.getCode(), status.getMsg());
    }

    public static Response ok(Status status, String msg) {
        return new Response(status.getCode(), msg);
    }

    public static Response ok(Status status, Object data) {
        return new Response(status.getCode(), status.getMsg(), data);
    }

    public Object getData() {
        return data != null ? data : new LinkedList<>();
    }

    public void setData(Object data) {
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public Object getMsg() {
        return msg;
    }

    public void setMsg(Object msg) {
        this.msg = msg;
    }
}
