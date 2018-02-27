package esform.util.pool;

/**
 * Created by szj46941 on 2018/2/27.
 */
public abstract class AbstractPool<T> implements Pool<T> {
    /**
     * Returns the object to the pool.
     * The method first validates the object if it is
     * re-usable and then puts returns it to the pool.
     * <p>
     * If the object validation fails,
     * some implementations
     * will try to create a new one
     * and put it into the pool; however
     * this behaviour is subject to change
     * from implementation to implementation
     */
    @Override
    public final void release(T t) {
        if (isValid(t)) {
            returnToPool(t);
        } else {
            handleInvalidReturn(t);
        }
    }

    protected abstract void handleInvalidReturn(T t);

    protected abstract void returnToPool(T t);

    protected abstract boolean isValid(T t);
}
