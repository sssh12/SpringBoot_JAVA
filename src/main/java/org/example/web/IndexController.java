package org.example.web;

import lombok.RequiredArgsConstructor;
import org.example.service.book.BookService;
import org.example.web.dto.BookResponseDto;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;


@RequiredArgsConstructor
@Controller // @RestController가 아닌 @Controller임에 주의!
public class IndexController {
    private final BookService bookService;

    @GetMapping("/")
    public String index(Model model) { // @LoginUser를 잠시 제거
        model.addAttribute("book", bookService.findAllDesc());
        return "index";
    }

    @GetMapping("/book/save")
    public String bookSave() {
        return "book-save";
    }

    @GetMapping("/book/update/{id}")
    public String bookUpdate(@PathVariable Long id, Model model) {
        BookResponseDto dto = bookService.findById(id);
        model.addAttribute("book", dto);
        return "book-update";

    }
}