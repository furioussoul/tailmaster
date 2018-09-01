import esform.Application;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by
 *
 * @name:孙证杰
 * @email:200765821@qq.com on 2017/9/17.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BaseTest {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    public void testFreeMarkerTemplate() throws Exception {
        ResponseEntity<String> entity = this.testRestTemplate.getForEntity("http://47.94.2.0:9090/esview/page/richPage/512",
                String.class);
        Thread.sleep(100);
    }
}