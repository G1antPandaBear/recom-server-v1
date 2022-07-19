package com.pandabear.recom.domain.document.entity;

import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "document")
@Getter
public class Document {

    @Id
    private String id;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;
}
