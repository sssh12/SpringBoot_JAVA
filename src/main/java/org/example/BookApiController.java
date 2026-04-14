package org.example;

import lombok.RequiredArgsConstructor;
import org.example.service.book.BookService;
import org.example.web.dto.BookResponseDto;
import org.example.web.dto.BookSaveRequestDto;
import org.example.web.dto.BookUpdateRequestDto;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController // 이 컨트롤러는 JSON을 반환하는 컨트롤러임을 명시
public class BookApiController {

    private final BookService bookService;

    @PostMapping("/api/v1/book")
    public Long save(@RequestBody BookSaveRequestDto requestDto) {
        return bookService.save(requestDto);
    }

    // @PathVariable: URL 경로에 포함된 값(id)을 메소드의 파라미터로 가져옴
    @PutMapping("/api/v1/book/{id}")
    public Long update(@PathVariable Long id, @RequestBody BookUpdateRequestDto requestDto) {
        return bookService.update(id, requestDto);
    }

    @GetMapping("/api/v1/book/{id}")
    public BookResponseDto findById(@PathVariable Long id) {
        return bookService.findById(id);
    }

    @DeleteMapping("/api/v1/posts/{id}")
    public Long delete(@PathVariable Long id) {
        bookService.delete(id);
        return id;
    }
}
