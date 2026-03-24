package org.example.web.dto;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.test.web.servlet.MockMvc;
import org.example.domain.book.Book;
import org.example.domain.book.BookRepository;
import org.example.web.dto.BookSaveRequestDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment =  SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc

public class BookApiControllerTest {
    @LocalServerPort
    private int port;
    @Autowired
    private MockMvc mvc;
    @Autowired
    private BookRepository bookRepository;

    @Test
    public void Book_insert() throws Exception {
        // given
        String isbn = "9791156640127";
        String title = "데이터베이스 개론 및 실습";
        String author = "박우창 등";
        String publisher = "한빛아카데미";
        int price = 30000;
        BookSaveRequestDto requestDto = BookSaveRequestDto.builder().isbn(isbn).title(title).author(author).publisher(publisher).price(price).build();

        String url = "http://localhost:" + port + "/api/v1/book";

        // when
        mvc.perform(post(url).contentType(MediaType.APPLICATION_JSON).content(new ObjectMapper().writeValueAsString(requestDto))).andExpect(status().isOk());

        // then
        List<Book> all = bookRepository.findAll();
        assertThat(all.get(0).getTitle()).isEqualTo(title);
        assertThat(all.get(0).getIsbn()).isEqualTo(isbn);
        assertThat(all.get(0).getAuthor()).isEqualTo(author);
        assertThat(all.get(0).getPublisher()).isEqualTo(publisher);
        assertThat(all.get(0).getPrice()).isEqualTo(price);

    }

    @Test
    public void Book_update() throws Exception {
        // 1. Given: 테스트를 위해 기존 데이터를 먼저 저장합니다.
        Book savedBook = bookRepository.save(Book.builder()
                .isbn("12345")
                .title("제목")
                .author("저자")
                .publisher("출판사")
                .price(1000)
                .build());

        Long updateId = savedBook.getId(); // 저장된 데이터의 ID를 가져옵니다.

        // 수정할 내용 설정
        String expectedTitle = "수정된 제목";
        String expectedAuthor = "수정된 저자";
        int expectedPrice = 50000;

        BookUpdateRequestDto requestDto = BookUpdateRequestDto.builder()
                .isbn(savedBook.getIsbn()) // 기존 값 유지
                .title(expectedTitle)
                .author(expectedAuthor)
                .publisher(savedBook.getPublisher())
                .price(expectedPrice)
                .build();

        String url = "http://localhost:" + port + "/api/v1/book/" + updateId;

        // 2. When: MockMvc를 사용하여 PUT 요청을 보냅니다.
        mvc.perform(put(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(requestDto)))
                .andExpect(status().isOk());

        // 3. Then: 데이터베이스에 수정된 값이 잘 반영되었는지 확인합니다.
        List<Book> all = bookRepository.findAll();
        // 방금 저장/수정한 데이터가 리스트에 있는지 확인 (ID로 찾아도 됩니다)
        Book updatedBook = all.stream()
                .filter(b -> b.getId().equals(updateId))
                .findFirst()
                .orElseThrow();

        assertThat(updatedBook.getTitle()).isEqualTo(expectedTitle);
        assertThat(updatedBook.getAuthor()).isEqualTo(expectedAuthor);
        assertThat(updatedBook.getPrice()).isEqualTo(expectedPrice);
    }
}
