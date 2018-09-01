package esform.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * Created by admin on 2018/9/1.
 */
public class InitListener implements ServletContextListener{
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        System.out.println("1");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        System.out.println("2");
    }
}
