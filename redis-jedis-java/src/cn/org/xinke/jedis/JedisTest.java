package cn.org.xinke.jedis;

import org.junit.Test;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * Created by cinco on 2018-3-16.
 * jedis 单元测试.
 */
public class JedisTest {

    /**
     * 方式一：单实例连接
     */
    @Test
    public void test1 () {
        // 创建 jedis 连接对象
        Jedis jedis = new Jedis("47.100.183.227", 6379 );
        // 存值
        jedis.set( "name", "张三" );
        // 取值
        String name = jedis.get("name");
        // 释放资源
        jedis.close();

        System.out.println(name);
    }

    /**
     * 方式二：连接池连接
     */
    @Test
    public void test2 () {
        JedisPool jedisPool = null;
        Jedis jedis = null;
        try {
            // 创建 jedis 连接池配置对象
            JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
            // 设置最大连接数
            jedisPoolConfig.setMaxTotal(30);
            // 设置最大空闲连接数
            jedisPoolConfig.setMaxIdle(10);

            // 获取 jedis 连接池对象
            jedisPool = new JedisPool( jedisPoolConfig, "47.100.183.227", 6379 );

            // 获取 jedis 核心对象
            jedis = jedisPool.getResource();
            // 存取值
            jedis.set( "key1", "value1" );
            String key1 = jedis.get("key1");

            System.out.println(key1);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 释放资源
            if (jedis != null && !jedis.isConnected()) {
                jedis.close();
            }
            if (jedisPool != null && !jedisPool.isClosed()) {
                jedisPool.close();
            }
        }
    }
}
