package esform.util;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by szj46941 on 2018/2/27.
 */
public class ThreadUtil {

    private static volatile ExecutorService EXECUTOR;

    private static ExecutorService getExecutor(){

        if (EXECUTOR == null){
            synchronized (ExecutorService.class) {
                if (EXECUTOR == null){
                    EXECUTOR = Executors.newCachedThreadPool();
                }
            }
        }
        return EXECUTOR;
    }


    public static void addTask(Runnable thread){
        getExecutor().submit(thread);
    }
}
