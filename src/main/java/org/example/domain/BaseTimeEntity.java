package org.example.domain;

import lombok.Getter;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import java.time.LocalDateTime;

@Getter // 1. Getter 메서드 자동 생성 (테스트 오류 해결)
@MappedSuperclass // 2. 상속 받는 엔터티의 칼럼으로 포함
@EntityListeners(AuditingEntityListener.class) // Auditing 기능 포함
public class BaseTimeEntity {

    @CreatedDate // 4. 엔터티 생성 시 시간 자동 저장
    private LocalDateTime createdDate;

    @LastModifiedDate // 5. 엔터티 수정 시 시간 자동 저장
    private LocalDateTime modifiedDate;
}
