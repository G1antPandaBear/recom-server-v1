package com.pandabear.recom.domain.document.entity;

import com.pandabear.recom.global.exception.BusinessException;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.http.HttpStatus;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@Entity(name = "document")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Document {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    public void updateContent(String content) {
        this.content = content;
    }

    public static class NotExistedException extends BusinessException {
        public NotExistedException() {
            super(HttpStatus.NOT_FOUND, "문서를 찾을 수 없습니다.");
        }
    }
}
