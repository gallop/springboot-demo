import com.gallop.redis.RedisDemoApplication;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.Set;

/**
 * author gallop
 * date 2021-06-02 17:39
 * Description:
 * Modified By:
 */
@SpringBootTest(classes = RedisDemoApplication.class)
public class RedisTest {

    @Autowired
    private RedisTemplate<Object,Object> redisTemplate;

    @Test
    public void redisTest(){
        for (int i = 0; i < 10; i++) {
            this.redisTemplate.opsForValue().set("key_" + i, "value_"+i);
        }
        Set<Object> keys = this.redisTemplate.keys("key_*");
        for (Object key : keys) {
            String value = (String)this.redisTemplate.opsForValue().get(key);
            System.out.println(value);
            this.redisTemplate.delete(key);
        }
    }
}
