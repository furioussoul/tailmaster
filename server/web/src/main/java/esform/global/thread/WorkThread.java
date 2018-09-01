package esform.global.thread;

import esform.global.exception.WebExceptionHandler;
import esform.global.request.Request;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Callable;

/**
 * Created by admin on 2018/9/1.
 */
public class WorkThread implements Callable<Boolean>{

    private static Logger LOGGER = LoggerFactory.getLogger(WorkThread.class);

    private ArrayBlockingQueue<Request> queue;

    public WorkThread(ArrayBlockingQueue queue) {
        this.queue = queue;
    }

    @SuppressWarnings("InfiniteLoopStatement")
    @Override
    public Boolean call() throws Exception {
        try{
            while(true){
                Request request = queue.take();
                request.process();
            }
        }catch (Exception ex){
            LOGGER.error("workThread call error", ex);
            return false;
        }
    }
}
