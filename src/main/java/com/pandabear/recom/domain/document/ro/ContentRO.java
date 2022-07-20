package com.pandabear.recom.domain.document.ro;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class ContentRO {

    private List<Content> contents;
    private String title;
    private String createdAt;
    private String address;

}
