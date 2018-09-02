package esform.global.request;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;

/**
 * Created by admin on 2018/9/1.
 */
public class RequestQueue {
    private List<ArrayBlockingQueue<Request>> queues = new ArrayList<>();

    private static class Instance {
        private static RequestQueue INSTANCE;

        static {
            INSTANCE = new RequestQueue();
        }

        private static RequestQueue getInstance() {
            return INSTANCE;
        }
    }


    public static RequestQueue getInstance() {
        return RequestQueue.Instance.getInstance();
    }

    public void addQueue(ArrayBlockingQueue<Request> queue) {
        queues.add(queue);
    }

    public ArrayBlockingQueue<Request> getQueue(int index) {
        return queues.get(index);
    }

    public int size() {
        return queues.size();
    }
}
