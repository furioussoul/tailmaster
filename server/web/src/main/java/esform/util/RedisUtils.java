package esform.util; /**
 * Created by admin on 2018/8/31.
 */

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import javax.annotation.PostConstruct;

/**
 * redis工具类
 *
 * @author 孙证杰
 * @version v1.0.0: RedisUtils.java, v 0.1 2018年8月24日 下午16:25:19 孙证杰 Exp $
 */
@Component
public class RedisUtils {
    private final static Logger LOGGER = LoggerFactory.getLogger(RedisUtils.class);
    public static JedisPool REDIS_POOL;

    @Autowired
    private JedisPool jedisPool;

    @PostConstruct
    public void initRedisClient(){
        REDIS_POOL = jedisPool;
    }

    public interface DaoCommand {
        Object execute();
    }

    /**
     * Cache Aside Pattern, 有双写不一致问题
     *
     * @param command       命令
     * @param key           键
     * @param expiredTime   过期时间（毫秒）
     * @param typeReference 返回值类型，与fastjson的一样
     * @return <R> 获取的数据
     */
    @SuppressWarnings("unchecked")
    public static <R> R get(DaoCommand command, TypeReference<R> typeReference, String key, long expiredTime) {

        if (StringUtils.isEmpty(key)) {
            throw new RuntimeException("key is empty");
        }
        if (expiredTime < 1) {
            throw new RuntimeException("expiredTime is less than 0");
        }

        R result = null;

        String r = REDIS_POOL.getResource().get(key);
        if (r != null) {
            LOGGER.debug("CACHE | get value from redis key:" + key + ",value:" + r);
            return JSON.parseObject(r, typeReference);
        } else {
            result = (R) command.execute();
            LOGGER.debug("CACHE | get value from mysql key:" + key + ",value:" + JSON.toJSONString(result));
        }

        R finalResult = result;
        ThreadUtil.addTask(() -> REDIS_POOL.getResource().set(key, JSON.toJSONString(finalResult),"NX","EX", expiredTime));
        return result;
    }


    public static void remove(DaoCommand command, String key) throws InterruptedException {
        LOGGER.debug("CACHE | remove from redis key:" + key);
        ThreadUtil.addTask(()-> REDIS_POOL.getResource().del(key));

        Thread.sleep(10000);// 测试 Cache aside pattern 双写不一致

        LOGGER.debug("CACHE | remove from mysql key:" + key);
        command.execute();
    }


    public static void main(String[] a) {
        Jedis jedis = new Jedis("47.97.220.227", 6379);
        jedis.set("k1","v1");
        System.out.print(jedis.get("k1"));
    }
}
