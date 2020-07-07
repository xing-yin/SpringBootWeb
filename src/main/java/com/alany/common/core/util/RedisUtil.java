//package com.alany.common.core.util;
//
//import org.apache.commons.lang3.StringUtils;
//import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
//import org.springframework.stereotype.Component;
//import redis.clients.jedis.*;
//import redis.clients.util.Pool;
//
//import java.util.*;
//
//
//@Component
//public class RedisUtil {
//
//    public final Logger logger = LoggerFactory.getLogger(RedisUtil.class);
//
//    private static final String LOCK_SUCCESS = "OK";
//
//    private static final String SET_IF_NOT_EXIST = "NX";
//
//    private static final String SET_WITH_EXPIRE_TIME = "PX";
//
//    private static final Long RELEASE_SUCCESS = 1L;
//
//    @Value("${environment}")
//    private String environment;
//
//    @Value("${redis.host}")
//    private String redisHost = "192.168.200.37";
//
//    @Value("${redis.port}")
//    private int redisPort = 6379;
//
//    @Value("${redis.sentinel.master}")
//    private String redisSentinelMaster = "";
//
//    @Value("${redis.sentinel.nodes}")
//    private String redisSentinelNodes = "";
//
//    @Value("${redis.password}")
//    private String redisPassword = "a9b2c3ca7fd5903bcd173fcddc60795ec6b0f1e5";
//
//    protected Map<Integer, Pool<RedisProperties.Jedis>> jedisMap = new HashMap<>();
//
//    private int timeout = 1000;
//
//    /**
//     * redis根据索引选择指定数据库；jedis连接初始化
//     *
//     * @param dbIndex
//     * @return
//     */
//    public Pool<Jedis> getJedisPool(int dbIndex) {
//        if (jedisMap == null || jedisMap.size() == 0) {
//            for (int i = 0; i < 16; i++) {
//                //线上运行
//                if (environment.equals("dev")) {
//                    jedisMap.put(i, new JedisPool(new JedisPoolConfig(), redisHost, redisPort, timeout, redisPassword, i));
//                } else {
//                    Set<String> sentinels = new HashSet<>();
//                    for (String s : redisSentinelNodes.split(",")) {
//                        if (StringUtils.isNotBlank(s)) {
//                            sentinels.add(s);
//                        }
//                    }
//                    GenericObjectPoolConfig jedisPoolConfig = new GenericObjectPoolConfig();
//                    //最大连接数
//                    jedisPoolConfig.setMaxTotal(100);
//                    // 最大空闲数
//                    jedisPoolConfig.setMaxIdle(20);
//                    // 获取连接最长等待时间
//                    jedisPoolConfig.setMaxWaitMillis(2000);
//                    // 连接耗尽时不阻塞，抛出异常
//                    jedisPoolConfig.setBlockWhenExhausted(false);
//                    jedisMap.put(i, new JedisSentinelPool(redisSentinelMaster, sentinels, jedisPoolConfig, timeout, redisPassword, i));
//                }
//
//            }
//        }
//        return jedisMap.get(dbIndex);
//    }
//
//    public String getString(int dbIndex, String key) {
//        String value = null;
//        Jedis jedis = null;
//        try {
//            jedis = getJedisPool(dbIndex).getResource();
//            value = jedis.get(key);
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            if (jedis != null) {
//                jedis.close();
//            }
//        }
//        return value;
//    }
//
//    public void setString(int dbIndex, String key, String value, int seconds) {
//        Jedis jedis = null;
//        try {
//            jedis = getJedisPool(dbIndex).getResource();
//            jedis.setex(key, seconds, value);
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            if (jedis != null) {
//                jedis.close();
//            }
//        }
//    }
//
//    public List<String> getList(int dbIndex, String key) {
//        List<String> value = new ArrayList<>();
//        Jedis jedis = null;
//        try {
//            jedis = getJedisPool(dbIndex).getResource();
//            value = jedis.lrange(key, 0, -1);
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            if (jedis != null) {
//                jedis.close();
//            }
//        }
//        return value;
//    }
//
//    public Long getListLength(int dbIndex, String key) {
//        Long length = null;
//        Jedis jedis = null;
//        try {
//            jedis = getJedisPool(dbIndex).getResource();
//            length = jedis.llen(key);
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            if (jedis != null) {
//                jedis.close();
//            }
//        }
//        return length;
//    }
//
//    public void putToList(int dbIndex, String key, String value) {
//        putToList(dbIndex, key, value, null);
//    }
//
//    public void putToList(int dbIndex, String key, String value, Integer maxLength) {
//        Jedis jedis = null;
//        try {
//            jedis = getJedisPool(dbIndex).getResource();
//            jedis.lpush(key, value);
//            if (maxLength != null) {
//                jedis.ltrim(key, 0, maxLength);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            if (jedis != null) {
//                jedis.close();
//            }
//        }
//    }
//
//    public Long getSetLength(int dbIndex, String key) {
//        Long length = null;
//        Jedis jedis = null;
//        try {
//            jedis = getJedisPool(dbIndex).getResource();
//            length = jedis.scard(key);
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            if (jedis != null) {
//                jedis.close();
//            }
//        }
//        return length;
//    }
//
//    public Boolean deleteRedisKey(int dbIndex, String key) {
//        Jedis jedis = null;
//        try {
//            jedis = getJedisPool(dbIndex).getResource();
//            Long i = jedis.del(key);
//            if (i == 1) {
//                return true;
//            } else {
//                return false;
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            if (jedis != null) {
//                jedis.close();
//            }
//        }
//        return false;
//    }
//
//    /**
//     * set 新增数据
//     *
//     * @param key
//     * @param values
//     */
//    public void putToSet(int dbIndex, String key, String... values) {
//        Jedis jedis = null;
//        try {
//            jedis = getJedisPool(dbIndex).getResource();
//            jedis.sadd(key, values);
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            if (jedis != null) {
//                jedis.close();
//            }
//        }
//    }
//
//    /**
//     * set 删除数据
//     *
//     * @param key
//     * @param values
//     */
//    public void deleteFromSet(int dbIndex, String key, String... values) {
//        Jedis jedis = null;
//        try {
//            jedis = getJedisPool(dbIndex).getResource();
//            jedis.srem(key, values);
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            if (jedis != null) {
//                jedis.close();
//            }
//        }
//    }
//
//    /**
//     * 获得 set 中的所有元素
//     *
//     * @param key
//     */
//    public Set<String> getSetAll(int dbIndex, String key) {
//        Set<String> set = new HashSet<>();
//        Jedis jedis = null;
//        try {
//            jedis = getJedisPool(dbIndex).getResource();
//            set = jedis.smembers(key);
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            if (jedis != null) {
//                jedis.close();
//            }
//        }
//        return set;
//    }
//
//    /**
//     * 插入覆盖 hash 中的所有元素
//     * @param dbIndex
//     * @param key
//     * @param dataMap
//     */
//    public void setHashMap(int dbIndex, String key, Map<String, String> dataMap) {
//        Jedis jedis = null;
//        try {
//            jedis = getJedisPool(dbIndex).getResource();
//            jedis.hmset(key, dataMap);
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            if (jedis != null) {
//                jedis.close();
//            }
//        }
//
//    }
//
//    /**
//     * 获得 hash 中的所有元素
//     *
//     * @param key
//     */
//    public Map<String, String> getHashMap(int dbIndex, String key) {
//        Map<String, String> map = new HashMap<>();
//        Jedis jedis = null;
//        try {
//            jedis = getJedisPool(dbIndex).getResource();
//            map = jedis.hgetAll(key);
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            if (jedis != null) {
//                jedis.close();
//            }
//        }
//        return map;
//    }
//
//    /**
//     * 获得 hash 中 field 的值
//     *
//     * @param key
//     */
//    public String getHashField(int dbIndex, String key, String field) {
//        String value = "";
//        Jedis jedis = null;
//        try {
//            jedis = getJedisPool(dbIndex).getResource();
//            value = jedis.hget(key, field);
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            if (jedis != null) {
//                jedis.close();
//            }
//        }
//        return value;
//    }
//
//    /**
//     * hash 中整数字段增量
//     *
//     * @param key
//     */
//    public long getHashIncrement(int dbIndex, String key, String field, long increment) {
//        long value = 0L;
//        Jedis jedis = null;
//        try {
//            jedis = getJedisPool(dbIndex).getResource();
//            value = jedis.hincrBy(key, field, increment);
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            if (jedis != null) {
//                jedis.close();
//            }
//        }
//        return value;
//    }
//
//    /**
//     * hash 中删除字段
//     *
//     * @param key
//     */
//    public long deleteHashField(int dbIndex, String key, String... field) {
//        long value = 0L;
//        Jedis jedis = null;
//        try {
//            jedis = getJedisPool(dbIndex).getResource();
//            value = jedis.hdel(key, field);
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            if (jedis != null) {
//                jedis.close();
//            }
//        }
//        return value;
//    }
//
//    /**
//     * 消息队列发布
//     */
//    public void publish(int dbIndex, String channel, String message) {
//        Jedis jedis = null;
//        try {
//            jedis = getJedisPool(dbIndex).getResource();
//            jedis.publish(channel, message);
//            logger.info(String.format("消息队列%s写入数据：%s", channel, message));
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            if (jedis != null) {
//                jedis.close();
//            }
//        }
//    }
//
//    /**
//     * 消息队列订阅
//     */
//    public void subscribe(int dbIndex, JedisPubSub subscriber, String... channel) {
//        Jedis jedis = null;
//        try {
//            jedis = getJedisPool(dbIndex).getResource();
//            jedis.subscribe(subscriber, channel);
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            if (jedis != null) {
//                jedis.close();
//            }
//        }
//    }
//
//    /**
//     * 尝试获取分布式锁
//     * @param lockKey 锁
//     * @param requestId 加锁客户端标识
//     * @param expireTime 超期时间单位(ms)
//     * @return 是否获取成功
//     */
//    public  boolean tryGetDistributedLock(String lockKey, String requestId, int expireTime) {
//        boolean lock = false;
//        Jedis jedis = null;
//        try {
//            jedis = getJedisPool(0).getResource();
//            String result = jedis.set(lockKey, requestId, SET_IF_NOT_EXIST, SET_WITH_EXPIRE_TIME, expireTime);
//            if (LOCK_SUCCESS.equals(result)) {
//                lock = true;
//            }
//        } catch (Exception e) {
//            logger.info("分布式锁释放添加成功，lockey="+lockKey + "加锁客户端标识="+requestId + "加锁时长=" + expireTime + "ms");
//            e.printStackTrace();
//        } finally {
//            if (jedis != null ) {
//                jedis.close();
//            }
//        }
//        return lock;
//    }
//
//    /**
//     * 释放分布式锁
//     * @param lockKey 锁
//     * @param requestId 加锁客户端标识
//     * @return 是否释放成功
//     */
//    public  boolean releaseDistributedLock(String lockKey, String requestId) {
//        Jedis jedis = null ;
//        boolean lock = false;
//        try {
//            jedis = getJedisPool(0).getResource();
//            String script = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";
//            Object result = jedis.eval(script, Collections.singletonList(lockKey), Collections.singletonList(requestId));
//            if (RELEASE_SUCCESS.equals(result)) {
//                lock = true;
//            }
//        } catch (Exception e) {
//            logger.error("分布式锁释放失败！");
//            e.printStackTrace();
//        } finally {
//            if (jedis != null) {
//                jedis.close();
//            }
//        }
//        return lock;
//    }
//
//    /**
//     * 历史记录写入 redis
//     */
//    public void historyPush(int dbIndex, String key, String value, int maxCount) {
//        Jedis jedis = null;
//        try {
//            jedis = getJedisPool(dbIndex).getResource();
//            jedis.lpush(key, value);
//            // 仅保留指定区间内的记录数，删除区间外的记录。下标从 0 开始，即 end 需要最大值 -1
//            jedis.ltrim(key, 0, maxCount);
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            if (jedis != null) {
//                jedis.close();
//            }
//        }
//    }
//
//    /**
//     * 从 redis 获取历史记录
//     */
//    public List<String> getHistoryList(int dbIndex, String key) {
//        // end 为 -1 表示到末尾。因为前面插入操作时，限定了存在的记录数
//        return getList(dbIndex, key);
//    }
//
//}
