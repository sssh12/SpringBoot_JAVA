package org.example.domain.book;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {
    @Query("SELECT p FROM Book p ORDER BY p.id DESC")
    // jpa query language로 Book이라는 클래스를 p라는 객체 이름으로 사용
    List<Book> findAllDesc();
}
