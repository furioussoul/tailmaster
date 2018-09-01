package esform.global.thread;

import esform.global.request.RequestQueue;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by admin on 2018/9/1.
 */
public class RequestProcessorThreadPool {

    private ExecutorService threadPool = Executors.newFixedThreadPool(10);

    public RequestProcessorThreadPool() {
        RequestQueue requestQueue = RequestQueue.getInstance();
        for (int i = 0;i< 10;i++){
            ArrayBlockingQueue queue = new ArrayBlockingQueue(100);
            requestQueue.addQueue(queue);
            threadPool.submit(new WorkThread(queue));
        }
    }

    private static class Instance{
        private static RequestProcessorThreadPool INSTANCE;

        static {
            INSTANCE = new RequestProcessorThreadPool();
        }

        public static RequestProcessorThreadPool getInstance(){
            return INSTANCE;
        }
    }

    public static RequestProcessorThreadPool getInstance(){
        return Instance.getInstance();
    }
}
