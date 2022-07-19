package com.pandabear.recom.domain.document.service;

import com.pandabear.recom.domain.document.entity.Document;
import com.pandabear.recom.domain.document.repository.DocumentRepository;
import com.pandabear.recom.domain.document.ro.ContentRO;
import com.pandabear.recom.domain.document.ro.DocumentRO;
import com.pandabear.recom.global.redis.entity.DocAccessCode;
import com.pandabear.recom.global.redis.service.AccessCodeService;
import com.pandabear.recom.global.util.DocumentUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class DocumentService {

    private final DocumentRepository documentRepository;
    private final AccessCodeService accessCodeService;
    private final DocumentUtil documentUtil;

    @Transactional(readOnly = true)
    @Cacheable(value = "codeCaching", key = "#code")
    public ContentRO findByCode(String code) {
        long documentId = accessCodeService.findByKey(code).getDocumentId();
        Document document = documentRepository.findById(documentId)
                .orElseThrow(Document.NotExistedException::new);
        return new ContentRO(documentUtil.parseDocuments(document));
    }

    public DocumentRO create(String content) {
        Document document = new Document(null, content);
        documentRepository.save(document);
        DocAccessCode createdDocAccessCode = accessCodeService.create(document.getId());
        return new DocumentRO(createdDocAccessCode.getId());
    }

    @CachePut(value = "codeCaching", key = "#code")
    public ContentRO update(String code, String content) {
        long documentId = accessCodeService.findByKey(code).getDocumentId();
        Document document = documentRepository.findById(documentId)
                .orElseThrow(Document.NotExistedException::new);
        document.updateContent(content);
        documentRepository.save(document);
        return new ContentRO(documentUtil.parseDocuments(document));
    }
}
