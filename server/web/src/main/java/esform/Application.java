package esform;

import esform.global.filter.LoggerFilter;
import esform.global.filter.OauthFilter;
import esform.global.listener.InitListener;
import esform.util.RedisUtils;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * Created by
 *
 * @name:孙证杰
 * @email:200765821@qq.com on 2017/9/17.
 */
@SpringBootApplication
@EnableAutoConfiguration
@EnableTransactionManagement
@PropertySource(value = {"classpath:application.properties"})
@MapperScan(basePackages = "esform.dao")
public class Application {

    @Value("${redis-master1.ip}")
    private  String redisMaster1Ip;
    @Value("${redis-master1.port}")
    private int redisMaster1Port;

    @Autowired
    private LoggerFilter loggerFilter;
    @Autowired
    private OauthFilter oauthFilter;
    private final static Logger LOGGER = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
        LOGGER.info("--启动了--");
    }

    @Bean
    public FilterRegistrationBean<LoggerFilter> logFilter() {
        FilterRegistrationBean<LoggerFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(loggerFilter);
        registrationBean.setOrder(1);
        return registrationBean;
    }

    @Bean
    public FilterRegistrationBean<OauthFilter> authFilter() {
        FilterRegistrationBean<OauthFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(oauthFilter);
        registrationBean.setOrder(2);
        return registrationBean;
    }

    @Bean
    public JedisPool redisFactory(){
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxIdle(5);
        config.setMaxTotal(100);
        config.setMaxWaitMillis(1000);
        config.setTestOnBorrow(false);
        return new JedisPool(config, redisMaster1Ip, redisMaster1Port, 10000);
    }

    @Bean
    public ServletListenerRegistrationBean servletListenerRegistration(){
        ServletListenerRegistrationBean<InitListener> servletListenerRegistrationBean = new ServletListenerRegistrationBean<>();
        servletListenerRegistrationBean.setListener(new InitListener());
        return servletListenerRegistrationBean;
    }
}
