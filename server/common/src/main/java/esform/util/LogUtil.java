package esform.util;

/**
 * Created by szj46941 on 2018/3/1.
 */
public class LogUtil {
    public static void info(String info){
        System.out.println(info);
    }
    public static void error(String info, Exception error){
        System.out.println(info);
        error.printStackTrace();
    }
}
