package org.example.service.book;

import org.example.domain.book.Book;
import org.example.domain.book.BookRepository;
import org.example.web.dto.BookListResponseDto;
import org.example.web.dto.BookResponseDto;
import org.example.web.dto.BookSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.example.web.dto.BookUpdateRequestDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor // Lombok 어노테이션. final이 선언된 모든 필드를 인자로 하는 생성자를 자동으로 생성
@Service
public class BookService {
    private final BookRepository bookRepository;

    @Transactional // 원자성 보장.
    public Long save(BookSaveRequestDto requestDto) {
        return bookRepository.save(requestDto.toEntity()).getId();
    }

    @Transactional
    public Long update(Long id, BookUpdateRequestDto requestDto) {
        Book book = bookRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 도서가 없습니다 id=" + id));
        book.update(requestDto.getIsbn(), requestDto.getTitle(), requestDto.getAuthor(), requestDto.getPublisher(), requestDto.getPrice()); // 객체 값만 변경하면 테이블도 같이 변경

        return id;
    }

    @Transactional(readOnly = true)
    public BookResponseDto findById(Long id) {
        Book entity = bookRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 도서가 없습니다 id=" + id));

        return new BookResponseDto(entity);
    }

    @Transactional(readOnly = true)
    public List<BookListResponseDto> findAllDesc() {
        return bookRepository.findAllDesc().stream()
                .map(BookListResponseDto:: new)
                .collect(Collectors.toList());

    }



    @Transactional
    public void delete (Long id) {
        Book posts = bookRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 도서가 없습니다. id=" +
                        id));

        bookRepository.delete(posts);
    }
}
