package com.pandabear.recom.global.redis.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.redis.core.RedisHash;

import javax.persistence.Column;
import javax.persistence.Id;

@RedisHash
@Getter
@AllArgsConstructor
public class DocAccessCode {

    @Id
    private String id;

    @Column(unique = true, nullable = false)
    private String code;
}
