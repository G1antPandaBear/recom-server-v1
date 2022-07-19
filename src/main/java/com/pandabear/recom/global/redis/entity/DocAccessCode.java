package com.pandabear.recom.global.redis.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

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
}
