package com.pandabear.recom.domain.document.ro;

import lombok.Getter;

@Getter
public class DocumentIdRO {

    private final long documentId;

    public DocumentIdRO(long documentId) {
        this.documentId = documentId;
    }
}
