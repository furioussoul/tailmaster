package esform.util.pool;

import java.util.concurrent.*;

/**
 * Created by szj46941 on 2018/2/27.
 */
public class BoundedBlockingPool<T> extends AbstractPool<T> implements BlockingPool<T> {

    private int size;
    private BlockingQueue<T> objects;
    private Validator<T> validator;
    private ObjectFactory<T> objectFactory;
    private ExecutorService executor = Executors.newCachedThreadPool();
    private volatile boolean shutdownCalled;

    public BoundedBlockingPool(int size, Validator validator, ObjectFactory objectFactory) {
        this.size = size;
        this.validator = validator;
        this.objectFactory = objectFactory;
    }

    //一直等待直到取到对象
    @Override
    public T get() {
        if (shutdownCalled) {
            throw new IllegalStateException("Object pool is already shutdown");
        }

        try {
            return objects.take();
        } catch (InterruptedException ie) {
            Thread.currentThread().interrupt();
        }
        return null;
    }

    //一直等待直到timeOut，timeOut时取到null
    @Override
    public T get(long timeOut, TimeUnit unit) throws InterruptedException {
        if (shutdownCalled) {
            throw new IllegalStateException("Object pool is already shutdown");
        }

        try {
            return objects.poll(timeOut, unit);
        } catch (InterruptedException ie) {
            Thread.currentThread().interrupt();
        }
        return null;
    }

    @Override
    public void shutdown() {
        shutdownCalled = true;
        executor.shutdownNow();
        clearResources();
    }

    @Override
    protected void handleInvalidReturn(T t) {

    }

    @Override
    protected void returnToPool(T t) {
        if (validator.isValid(t)) {
            executor.submit(new ObjectReturner(objects, t));
        }
    }

    @Override
    protected boolean isValid(T t) {
        return validator.isValid(t);
    }

    private void initializeObjects() {
        for (int i = 0; i < size; i++) {
            objects.add(objectFactory.createNew());
        }
    }

    private void clearResources() {
        for (T t : objects) {
            validator.invalidate(t);
        }
    }

    private class ObjectReturner implements Runnable {
        private BlockingQueue<T> queue;
        private T e;

        public ObjectReturner(BlockingQueue queue, T e) {
            this.queue = queue;
            this.e = e;
        }

        @Override
        public void run() {
            try {
                queue.put(e);
            } catch (InterruptedException ie) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
