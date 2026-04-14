package org.example.web.dto;
import org.example.domain.book.Book;
import lombok.Getter;
import java.time.LocalDateTime;

@Getter
public class BookListResponseDto {
    private Long id;
    private String isbn;
    private String title;
    private String author;
    private String publisher;
    private int price;
    private LocalDateTime modifiedDate;

    public BookListResponseDto(Book entity) {
        this.id = entity.getId();
        this.isbn = entity.getIsbn();
        this.title = entity.getTitle();
        this.author = entity.getAuthor();
        this.publisher = entity.getPublisher();
        this.price = entity.getPrice();
        this.modifiedDate = entity.getModifiedDate();

    }

}