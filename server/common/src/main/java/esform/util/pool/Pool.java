package esform.util.pool;

/**
 * Created by szj46941 on 2018/2/27.
 */
public interface Pool<T> {
    T get();
    //释放资源，扔回池子
    void release(T t);
    //关闭池子,释放所有资源
    void shutdown();
}
