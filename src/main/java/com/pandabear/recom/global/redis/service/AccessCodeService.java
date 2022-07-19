package com.pandabear.recom.global.redis.service;

import com.pandabear.recom.domain.document.ro.DocumentIdRO;
import com.pandabear.recom.global.redis.entity.DocAccessCode;
import com.pandabear.recom.global.redis.repository.DocAccessCodeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AccessCodeService {

    private final DocAccessCodeRepository docAccessCodeRepository;

    @Transactional(readOnly = true)
    protected DocAccessCode findById(String id) {
        return docAccessCodeRepository.findById(id)
                .orElseThrow(DocAccessCode.NotExistedException::new);
    }

    public DocumentIdRO findByKey(String key) {
        return new DocumentIdRO(findById(key).getDocumentId());
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public DocAccessCode create(long documentId) {
        DocAccessCode docAccessCode = new DocAccessCode(null, documentId);
        return docAccessCodeRepository.save(docAccessCode);
    }
}
