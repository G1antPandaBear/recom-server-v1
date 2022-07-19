package com.pandabear.recom.domain.document.service;

import com.pandabear.recom.domain.document.entity.Document;
import com.pandabear.recom.domain.document.repository.DocumentRepository;
import com.pandabear.recom.domain.document.ro.DocumentRo;
import com.pandabear.recom.global.exception.BusinessException;
import com.pandabear.recom.global.redis.entity.DocAccessCode;
import com.pandabear.recom.global.redis.service.AccessCodeService;
import com.pandabear.recom.global.util.FileUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@Transactional
@RequiredArgsConstructor
public class DocumentService {

    private final DocumentRepository documentRepository;
    private final AccessCodeService accessCodeService;
    private final FileUtil fileUtil;

    public DocumentRo findByCode(String code) {
        DocAccessCode docAccessCode = accessCodeService.findById(code);
        Document document = documentRepository.findById(docAccessCode.getDocumentId())
                .orElseThrow(() -> new BusinessException(HttpStatus.NOT_FOUND, "문서를 찾을 수 없습니다."));

        return new DocumentRo(code, document.getContent(), document.getRecordFileName());
    }

    public DocumentRo create(String content, MultipartFile file) {
        Document document = new Document(null, content, file.getOriginalFilename());

        try {
            saveFile(document, file);
        } catch (IOException e) {
            throw new BusinessException(HttpStatus.INTERNAL_SERVER_ERROR, "파일 업로드 중 오류가 발생했습니다.");
        }

        documentRepository.save(document);
        DocAccessCode createdDocAccessCode = accessCodeService.create(document.getId());

        return new DocumentRo(createdDocAccessCode.getId(), content, document.getRecordFileName());
    }

    private void saveFile(Document document, MultipartFile file) throws IOException {
        fileUtil.parseFileInfo(document, file);
    }
}
