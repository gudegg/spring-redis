package com.gude.redis;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.cache.interceptor.SimpleKeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisSentinelConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisShardInfo;

import javax.annotation.Resource;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * @Author Gude
 * @Date 2015/12/27.
 * 通过代码配置缓存
 */
//@Configuration
//@EnableCaching
//@PropertySource("classpath:/redis.properties")
public class RedisCacheConfig extends CachingConfigurerSupport {
    @Resource
    public Environment env;

    //    @Bean
    //    public JedisConnectionFactory redisConnectionFactory() {
    //        JedisPoolConfig config = new JedisPoolConfig();
    //        config.setMaxWaitMillis(Long.parseLong(env.getProperty("redis.pool.maxWait")));
    //        config.setMaxIdle(Integer.parseInt(env.getProperty("redis.pool.maxIdle")));
    //        config.setMaxTotal(Integer.parseInt(env.getProperty("redis.pool.maxActive")));
    //
    //        config.setTestOnBorrow(Boolean.parseBoolean(env.getProperty("redis.pool.testOnBorrow")));
    //        config.setTestOnReturn(Boolean.parseBoolean(env.getProperty("redis.pool.testOnReturn")));
    //        JedisPool pool = new JedisPool(config, env.getProperty("redis.host1"),
    //                Integer.valueOf(env.getProperty("redis.port1")),6000,"199412");
    //        //*****************************************************************************
    //        JedisConnectionFactory redisConnectionFactory = new JedisConnectionFactory();
    //        redisConnectionFactory.setHostName(env.getProperty("redis.host2"));
    //        redisConnectionFactory.setPort(Integer.parseInt(env.getProperty("redis.port2")));
    //        redisConnectionFactory.setPassword("199412");
    //        redisConnectionFactory.setPoolConfig(config);
    //        redisConnectionFactory.setUsePool(true);
    //
    ////        RedisSentinelConfiguration redisSentinelConfiguration=new RedisSentinelConfiguration().sentinel("192.168.2.100",6380).sentinel("192.168.2.100",6381)
    ////                .sentinel("192.168.2.100",6382);
    //
    //        return redisConnectionFactory;
    //       // return new JedisConnectionFactory(redisSentinelConfiguration);
    //    }
    List<String> clusterNodes = Arrays.asList("127.0.0.1:30001", "127.0.0.1:30002", "127.0.0.1:30003");

    @Bean
    public JedisConnectionFactory redisConnectionFactory() {
        return new JedisConnectionFactory(new RedisClusterConfiguration(clusterNodes));
    }

    @Bean
    public KeyGenerator customKeyGenerator() {
        //      return    new KeyGenerator() {
        //            public Object generate(Object target, Method method, Object... params) {
        //                StringBuilder sb = new StringBuilder();
        //                sb.append(params.getClass().getName());
        //                sb.append(method.getName());
        //                for (Object obj : params) {
        //                    sb.append(obj.toString());
        //                }
        //                return sb.toString();
        //            }
        //        };
        return new SimpleKeyGenerator();
    }

    @Bean
    public RedisTemplate<String, String> redisTemplate(RedisConnectionFactory cf) {
        RedisTemplate<String, String> redisTemplate = new RedisTemplate<String, String>();
        redisTemplate.setConnectionFactory(cf);
        return redisTemplate;
    }

    @Bean
    public CacheManager cacheManager(RedisTemplate redisTemplate) {

        RedisCacheManager cacheManager = new RedisCacheManager(redisTemplate);

        // Number of seconds before expiration. Defaults to unlimited (0)
        cacheManager.setDefaultExpiration(300);
        return cacheManager;
    }
}
