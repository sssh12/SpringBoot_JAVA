package org.example.web;

import org.junit.jupiter.api.Test; // JUnit 5 AI8
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = RANDOM_PORT)
public class IndexControllerTest {

    @Autowired //DI 실현: 빈 들중에서 타입이 일치하는 객체를 찾아 자동으로 주입(생성자/수정자/필드)
    //private MockMvc mvc;
    private TestRestTemplate restTemplate;
    @Test
    public void view_load() {
//when
        String body = this.restTemplate.getForObject("/", String.class);
//then
        System.out.println(body); //에러가 난다면 여기에 에러 페이지 html이 찍힐 것
// Assert를 사용한 검증
        assertThat(body).isNotNull();
        assertThat(body).contains("Boki62의 스프링 부트 웹 서비스");
    }
}