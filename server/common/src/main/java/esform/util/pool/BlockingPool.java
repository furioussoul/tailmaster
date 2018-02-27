package esform.util.pool;

import java.util.concurrent.TimeUnit;

/**
 * Created by szj46941 on 2018/2/27.
 */
public interface BlockingPool<T> extends Pool<T> {

    T get();

    /**
     * @param time amount of time to wait before giving up,
     *             in units of <tt>unit</tt>
     * @param unit a <tt>TimeUnit</tt> determining
     *             how to interpret the
     *             <tt>timeout</tt> parameter
     * @return
     * @throws InterruptedException
     */
    T get(long time, TimeUnit unit) throws InterruptedException;
}
