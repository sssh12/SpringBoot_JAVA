package org.example.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.web.dto.BookUpdateRequestDto;
import org.springframework.test.web.servlet.MockMvc;
import org.example.domain.book.Book;
import org.example.domain.book.BookRepository;
import org.example.web.dto.BookSaveRequestDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
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
//given
        String isbn = "9791156640127";
        String title = "데이터베이스 개론 및 실습";
        String author = "박우창 등";
        String publisher = "한빛아카데미";
        int price = 30000;
        BookSaveRequestDto requestDto = BookSaveRequestDto.builder()
                .isbn(isbn)
                .title(title)
                .author(author)
                .publisher(publisher)
                .price(price)
                .build();

        String url = "http://localhost:" + port + "/api/v1/book";

//when
        mvc.perform(post(url)
// .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(requestDto)))
                .andExpect(status().isOk());
//then
        List<Book> all = bookRepository.findAll();
        assertThat(all.get(0).getTitle()).isEqualTo(title);
        assertThat(all.get(0).getIsbn()).isEqualTo(isbn);
        assertThat(all.get(0).getAuthor()).isEqualTo(author);
        assertThat(all.get(0).getPublisher()).isEqualTo(publisher);
        assertThat(all.get(0).getPrice()).isEqualTo(price);

    }

    @Test
    @WithMockUser(roles = "USER")
    public void Book_update() throws Exception {
//given
        Book savedBook = bookRepository.save(Book.builder()
                .isbn("ISBN")
                .title("title")
                .author("author")
                .publisher("publisher")
                .price(30000)
                .build());

        Long updateId = savedBook.getId();
        String expectedIsbn = "ISBN2";
        String expectedTitle = "수정 테스트";
        String expectedAuthor = "창보기";
        String expectedPublisher = "9CH";
        int expectedPrice = 33000;

        BookUpdateRequestDto requestDto = BookUpdateRequestDto.builder()
                .isbn(expectedIsbn)
                .title(expectedTitle)
                .author(expectedAuthor)
                .publisher(expectedPublisher)
                .price(expectedPrice)
                .build();

        String url = "http://localhost:" + port + "/api/v1/book/" + updateId;

// when
        mvc.perform(put(url)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(requestDto)))
                .andExpect(status().isOk());

// then
        Book resultBook = bookRepository.findById(updateId)
                .orElseThrow(() -> new IllegalArgumentException("해당 도서가 없습니다. id=" + updateId));

        System.out.println("▶ DB에서 조회된 ISBN: " + resultBook.getIsbn());
        System.out.println("▶ DB에서 조회된 Title: " + resultBook.getTitle());
        System.out.println("▶ DB에서 조회된 Author: " + resultBook.getAuthor());
        System.out.println("▶ DB에서 조회된 Publisher: " + resultBook.getPublisher());
        System.out.println("▶ DB에서 조회된 Price: " + resultBook.getPrice());
        assertThat(resultBook.getIsbn()).isEqualTo(expectedIsbn);
        assertThat(resultBook.getTitle()).isEqualTo(expectedTitle);
        assertThat(resultBook.getAuthor()).isEqualTo(expectedAuthor);
        assertThat(resultBook.getPublisher()).isEqualTo(expectedPublisher);
        assertThat(resultBook.getPrice()).isEqualTo(expectedPrice);

    }
}