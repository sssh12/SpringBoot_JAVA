package org.example.domain.book;

import org.example.domain.book.Book;
import org.example.domain.book.BookRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class BookRepositoryTest {

    @Autowired
    BookRepository bookRepository;
    @AfterEach
    public void cleanup() {
        bookRepository.deleteAll();
    }

    @Test
    public void book_list() {
        // given
        String isbn = "9791156640127";
        String title = "데이터베이스 개론 및 실습";
        String author = "박우창 등";
        String publisher = "한빛아카데미";
        int price = 30000;
        bookRepository.save(Book.builder().isbn(isbn).title(title).author(author).publisher(publisher).price(price).build());
        // when
        List<Book> bookList = bookRepository.findAll();
        // then
        Book book = bookList.get(0);
        assertThat(book.getIsbn()).isEqualTo(isbn);
        assertThat(book.getTitle()).isEqualTo(title);
        assertThat(book.getAuthor()).isEqualTo(author);
        assertThat(book.getPrice()).isEqualTo(price);
    }
}
