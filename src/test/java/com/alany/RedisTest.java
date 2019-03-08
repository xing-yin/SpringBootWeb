package com.alany;

import org.junit.Test;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * @author yinxing
 * @date 2019/3/8
 */

public class RedisTest {

    @Test
    public void jedisClient() {
        // Jedis
        Jedis jedis = new Jedis("127.0.0.1", 6379);
        // 通过redis赋值
        jedis.set("s2", "2222");
        // 通过redis取值
        String result = jedis.get("s2");
        System.out.println(result);
        // 关闭jedis
        jedis.close();
    }

    @Test
    public void jedisPool() {
        // JedisPool
        JedisPool jedisPool = new JedisPool("127.0.0.1", 6379);
        // 通过连接池获取 jedis 对象
        Jedis jedis = jedisPool.getResource();

        jedis.set("s3", "3333");
        String result = jedis.get("s3");
        System.out.println(result);

        // 关闭jedis客户端
        jedis.close();

        // 关闭连接池
        jedisPool.close();
    }
}
