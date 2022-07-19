package com.pandabear.recom.global.redis.entity;

import com.pandabear.recom.global.exception.BusinessException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;
import org.springframework.http.HttpStatus;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

@Getter
@RedisHash
@AllArgsConstructor
public class DocAccessCode implements Serializable {

    @Id @Indexed
    private String id;

    @Column(unique = true, nullable = false)
    private long documentId;

    public static class NotExistedException extends BusinessException {
        public NotExistedException() {
            super(HttpStatus.NOT_FOUND, "문서 코드를 찾을 수 없습니다.");
        }
    }
}
