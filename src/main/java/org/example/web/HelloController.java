package org.example.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
// @RestController는 @Controller + @ResponseBody의 역할을 함.
// 즉, 메서드가 반환하는 문자열을 http 응답 본문에 직접 작성하도록 지시함.

@RestController // json을 반환하는 컨트롤러로 지정
public class HelloController {
    // "/hello" 경로로 GET 요청이 오면 이 메서드를 실행함.
    @GetMapping("/hello")
    public String hello() {
        // 정확히 "hello"라는 문자열을 반환해야 테스트가 성공함.
        return "hello"; // return 값이 다르면 오류 발생
    }
}
