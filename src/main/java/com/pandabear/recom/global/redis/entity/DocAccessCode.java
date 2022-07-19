package com.pandabear.recom.global.redis.entity;

import lombok.Getter;
import org.springframework.data.redis.core.RedisHash;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "doc_access_code")
@RedisHash
@Getter
public class DocAccessCode {

    @Id
    private String id;

    @Column(length = 255, unique = true, nullable = false)
    private String code;
}
