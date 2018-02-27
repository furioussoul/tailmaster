package esform.util.pool;

/**
 * Created by szj46941 on 2018/2/27.
 */
public interface ObjectFactory<T> {
    T createNew();
}
