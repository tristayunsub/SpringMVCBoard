package hello.practiceprj.web;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class WebRestControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void profile(){
        String profile = this.restTemplate.getForObject("/profile", String.class);

        Assertions.assertThat(profile).isEqualTo("local");
    }

}