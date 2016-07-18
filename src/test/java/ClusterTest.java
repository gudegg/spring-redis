import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPool;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

/**
 * @Author Gude.
 * @Date 2016/7/17.
 */
public class ClusterTest {
    @Test
    public void cluster() throws IOException {
        Set<HostAndPort> nodes = new HashSet<HostAndPort>();
        nodes.add(new HostAndPort("192.168.2.200", 7001));
        nodes.add(new HostAndPort("192.168.2.200", 7002));
        nodes.add(new HostAndPort("192.168.2.200", 7003));
        nodes.add(new HostAndPort("192.168.2.200", 7004));
        nodes.add(new HostAndPort("192.168.2.200", 7005));
        nodes.add(new HostAndPort("192.168.2.200", 7006));

        JedisCluster cluster = new JedisCluster(nodes);
        cluster.set("key1", "1000");
        String val = cluster.get("key1");
        System.out.println(val);
        cluster.close();
    }

    @Test
    public void testSpringCluster() throws IOException {

        ApplicationContext context = new ClassPathXmlApplicationContext("classpath:spring/spring-jedis.xml");
        JedisCluster cluster = (JedisCluster) context.getBean("jedisClientCluster");
        String val = cluster.get("key1");
        System.out.println(val);
        cluster.close();

    }
}
