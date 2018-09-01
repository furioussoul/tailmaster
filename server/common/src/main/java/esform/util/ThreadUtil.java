package esform.util;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * Created by szj46941 on 2018/2/27.
 */
public class ThreadUtil {

    private static volatile ExecutorService EXECUTOR;

    private static ExecutorService getExecutor() {

        if (EXECUTOR == null) {
            synchronized (ExecutorService.class) {
                if (EXECUTOR == null) {
                    EXECUTOR = Executors.newFixedThreadPool(60);
                }
            }
        }
        return EXECUTOR;
    }


    public static void addTask(Runnable thread) {
        int threadCount = ((ThreadPoolExecutor) ThreadUtil.getExecutor()).getActiveCount();
        System.out.println("the approximate number of threads is:" + threadCount);
        getExecutor().submit(thread);
    }
}
