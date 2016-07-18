import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * @Author Gude.
 * @Date 2016/7/17.
 */
public class SingleTest {

    @Test
    public void demo1() {
        Jedis jedis = new Jedis("192.168.2.200", 6379);
        jedis.set("key1", "jedis Test");
        String val = jedis.get("key1");
        System.out.println(val);
        jedis.close();
    }


    /**
     * 使用连接池
     */
    @Test
    public void testJedisPool() {
        JedisPool jedisPool = new JedisPool("192.168.2.200", 6379);
        Jedis jedis = jedisPool.getResource();
        String val = jedis.get("key1");
        System.out.println(val);
        jedis.close();
        jedisPool.close();
    }

    @Test
    public void testSpring(){
        ApplicationContext context=new  ClassPathXmlApplicationContext("classpath:spring/spring-jedis.xml");
        JedisPool jedisPool= (JedisPool) context.getBean("jedisClient");
        Jedis jedis = jedisPool.getResource();
        String val = jedis.get("key1");
        System.out.println(val);
        jedis.close();
        jedisPool.close();
    }
}
