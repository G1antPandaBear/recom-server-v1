package com.pandabear.recom.global.redis.service;

import com.pandabear.recom.global.exception.BusinessException;
import com.pandabear.recom.global.redis.entity.DocAccessCode;
import com.pandabear.recom.global.redis.repository.DocAccessCodeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
public class AccessCodeService {

    private final DocAccessCodeRepository docAccessCodeRepository;

    public DocAccessCode findById(String id) {

        DocAccessCode docAccessCode = docAccessCodeRepository.findById(id)
                .orElseThrow(() -> new BusinessException(HttpStatus.NOT_FOUND, "문서 코드를 찾을 수 없습니다."));

        return docAccessCode;
    }

    public DocAccessCode create(Long documentId) {

        DocAccessCode docAccessCode = new DocAccessCode(null, documentId);
        return docAccessCodeRepository.save(docAccessCode);
    }

}
