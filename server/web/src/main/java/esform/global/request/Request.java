package esform.global.request;

/**
 * Created by admin on 2018/9/1.
 */
public interface Request {
    Long getId();

    void process() throws InterruptedException;
}
