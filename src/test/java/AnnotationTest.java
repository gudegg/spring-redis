import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @Author Gude.
 * @Date 2016/7/17.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/spring-*.xml"})
//@PropertySource(value = "classpath*:redis.properties")
//@Configuration
public class AnnotationTest {
    @Value("${redis.host1}")
    private String host;

    @Test
    public void demo1(){
        System.out.println(host);
    }

}
