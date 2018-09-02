package esform.global.request;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ArrayBlockingQueue;

/**
 * Created by admin on 2018/9/1.
 */
public class RequestAsyncProcessHandler {

    private final static Logger LOGGER = LoggerFactory.getLogger(RequestAsyncProcessHandler.class);

    private ArrayBlockingQueue<Request> getRoutingQueue(Long id) {

        if (id == null) {
            throw new RuntimeException("id can not be empty");
        }

        RequestQueue queue = RequestQueue.getInstance();
        String key = id.toString();
        int h;
        int hash = (h = key.hashCode()) ^ (h >>> 16);
        int index = (queue.size() - 1) & hash;

        LOGGER.debug("CACHE | id:" + id + ", index:" + index);

        return queue.getQueue(index);
    }

    public void process(Request request) {
        ArrayBlockingQueue<Request> queue = getRoutingQueue(request.getId());
        queue.add(request);
    }
}
