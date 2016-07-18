import org.junit.Test;
import redis.clients.jedis.*;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @Author Gude
 * @Date 2016/1/5.
 */
public class RedisTest {
    private static final Jedis jedis;

    static {
        jedis = new Jedis("zgdgude.cn", 6379);
        jedis.auth("199412");
    }

    /**
     * redis管道技术
     * http://blog.csdn.net/zhu_tianwei/article/details/44894391
     */
    @Test
    public void demo1() {
        long start = System.currentTimeMillis();
        usePipeline();
        long end = System.currentTimeMillis();
        System.out.println("usePipeline:" + (end - start));

        start = System.currentTimeMillis();
        withoutPipeline();
        end = System.currentTimeMillis();
        System.out.println("withoutPipeline:" + (end - start));


    }

    private static void withoutPipeline() {
        try {

            for (int i = 0; i < 1000; i++) {
                jedis.incr("test2");
            }
            jedis.disconnect();
        } catch (Exception e) {
        }
    }

    private static void usePipeline() {
        try {

            Pipeline pipeline = jedis.pipelined();
            for (int i = 0; i < 1000; i++) {
                pipeline.incr("test2");
            }
            pipeline.sync();
            jedis.disconnect();
        } catch (Exception e) {
        }
    }

    @Test
    public void demo2() throws InterruptedException {
        //System.nanoTime();
        Transaction t = jedis.multi();
//        t.set("gude4", "gudeaaaa1");
//
//        Thread.sleep(1000);
//        t.set("gude5","gude33322");
//        String ret = (String) t.exec().get(0);
//        System.out.println(ret);
        t.set("key1", "aadsadsa");
        Thread.sleep(1000);
        t.set("key2", "bdsadsadbb");
         List oList = t.exec();
        System.out.println(oList);

        jedis.watch("key1");
        t = jedis.multi();
        t.set("key2", "ccc");
        t.set("key3", "ddd");
        t.get("key3");
        oList = t.exec();
        System.out.println(oList);
    }
    @Test
    public void demo3(){
       long gude55= jedis.setnx("gude55","gude55");
        long gude66=jedis.setnx("gude66","gude66");
        System.out.println(gude55+"--"+gude66);
        synchronized (RedisTest.class){

        }
    }
    @Test
    public void demo4(){

        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(100);
        config.setMaxIdle(100);
        config.setMinIdle(100);
        config.setMaxWaitMillis(6 * 1000);
        config.setTestOnBorrow(true);

        Set<HostAndPort> jedisClusterNodes = new HashSet<HostAndPort>();
        jedisClusterNodes.add(new HostAndPort("192.168.2.100", 6380));
        jedisClusterNodes.add(new HostAndPort("192.168.2.100", 6381));
        jedisClusterNodes.add(new HostAndPort("192.168.2.100", 6382));

        jedisClusterNodes.add(new HostAndPort("192.168.2.101", 6380));
        jedisClusterNodes.add(new HostAndPort("192.168.2.101", 6381));
        jedisClusterNodes.add(new HostAndPort("192.168.2.101", 6382));

        JedisCluster jedisCluster = new JedisCluster(jedisClusterNodes, 2000, 2, config);

        try {
            for (int i = 0; i < 1000; i++) {
                long t1 = System.currentTimeMillis();
                jedisCluster.set("" + i, "" + i);
                long t2 = System.currentTimeMillis();
                String value = jedisCluster.get("" + i);
                long t3 = System.currentTimeMillis();
                System.out.println("" + value);
                System.out.println((t2 - t1) + "mills");
                System.out.println((t3 - t2) + "mills");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                jedisCluster.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
