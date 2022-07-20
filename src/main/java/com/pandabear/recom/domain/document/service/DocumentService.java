package com.pandabear.recom.domain.document.service;

import com.pandabear.recom.domain.document.dto.ContentDto;
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

import java.time.format.DateTimeFormatter;

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
        String createdDateString = document.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy년 MM월 dd일 HH:mm:ss"));
        return new ContentRO(documentUtil.parseDocuments(document), document.getTitle(), createdDateString, document.getAddress());
    }

    public DocumentRO create(ContentDto dto) {
        Document document = new Document(null, dto.getTitle(), dto.getContent(), dto.getAdddress(), null);
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
        String createdDateString = document.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy년 MM월 dd일 HH:mm:ss"));
        return new ContentRO(documentUtil.parseDocuments(document), document.getTitle(), createdDateString, document.getAddress());
    }
}
