package com.pandabear.recom.domain.document.service;

import com.pandabear.recom.domain.document.entity.Document;
import com.pandabear.recom.domain.document.repository.DocumentRepository;
import com.pandabear.recom.domain.document.ro.DocumentRo;
import com.pandabear.recom.global.redis.entity.DocAccessCode;
import com.pandabear.recom.global.redis.service.AccessCodeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class DocumentService {

    private final DocumentRepository documentRepository;
    private final AccessCodeService accessCodeService;

    @Transactional(readOnly = true)
    public DocumentRo findByCode(String code) {
        long documentId = accessCodeService.findByKey(code).getDocumentId();
        System.out.println(documentId);
        Document document = documentRepository.findById(documentId)
                .orElseThrow(Document.NotExistedException::new);
        System.out.println(document.getContent());
        return new DocumentRo(code, document.getContent());
    }

    public DocumentRo create(String content) {
        Document document = new Document(null, content);
        documentRepository.save(document);
        DocAccessCode createdDocAccessCode = accessCodeService.create(document.getId());
        return new DocumentRo(createdDocAccessCode.getId(), content);
    }

    public DocumentRo update(String code, String content) {
        long documentId = accessCodeService.findByKey(code).getDocumentId();
        Document document = documentRepository.findById(documentId)
                .orElseThrow(Document.NotExistedException::new);

        document.updateContent(content);
        documentRepository.save(document);

        return new DocumentRo(code, content);
    }
}
