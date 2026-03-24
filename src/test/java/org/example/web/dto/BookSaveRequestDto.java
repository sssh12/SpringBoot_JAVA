package org.example.web.dto;

import lombok.AllArgsConstructor;
import org.example.domain.book.Book;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor // 모든 필드를 인자로 받는 생성자 생성
@Builder // 빌더 패턴 사용 가능하게 함
public class BookSaveRequestDto {
    private String isbn;
    private String title;
    private String author;
    private String publisher;
    private int price;

    @Builder
    public BookSaveRequestDto(String title, String content, String author) {
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.publisher = publisher;
        this.price = price;
    }

    public Book toEntity() {
        return Book.builder().isbn(isbn).title(title).author(author).publisher(publisher).price(price).build();
    }
}
