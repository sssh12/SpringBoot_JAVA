package org.example.domain.book;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import jakarta.persistence.Entity; // @Entity
import jakarta.persistence.Id; // @Id
import jakarta.persistence.GeneratedValue; // @GeneratedValue
import jakarta.persistence.GenerationType;
import jakarta.persistence.Column; // @Column
import org.example.domain.BaseTimeEntity;

@Getter // 클래스 내 모든 필드의 Getter 메서드를 자동 생성
@NoArgsConstructor // 기본 생성자 자동 추가
@AllArgsConstructor // 기본 생성자 자동 추가
@Entity // 테이블과 링크될 클래스임을 나타냄
public class Book extends BaseTimeEntity { // BaseTimeEntity를 상속
    @Id // 해당 테이블의 pk 필드
    @GeneratedValue(strategy = GenerationType.IDENTITY) // pk의 생성 규칙을 나타냄
    private Long id;
    @Column(length = 13, nullable = false) // @Column 테이블의 컬럼, 컬럼이 됨
    private String isbn;
    @Column(length = 250, nullable = false) // 모든 필드, 컬럼이 됨
    private String title;
    @Column(length = 100, nullable = false) // 모든 ㅍ필드, 컬럼이 됨
    private String author;
    @Column(length = 100, nullable = false) // 모든 필드, 컬럼이 됨
    private String publisher;
    private int price;

    @Builder // 어느 필드에 어떤 값을 채워야 할지 명확하게 해 줌
    public Book(String isbn, String title, String author, String publisher, int price) {
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.publisher = publisher;
        this.price = price;
    }

    public void update(String isbn, String title, String author, String publisher, int price) {
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.publisher = publisher;
        this.price = price;
    }
}
