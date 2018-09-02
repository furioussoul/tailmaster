package esform.global.listener;

import esform.global.thread.RequestProcessorThreadPool;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * Created by admin on 2018/9/1.
 */
public class InitListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        RequestProcessorThreadPool.getInstance();
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        System.out.println("InitListener contextDestroyed");
    }
}
