package com.pandabear.recom.domain.document.service;

import com.pandabear.recom.domain.document.dto.ContentDto;
import com.pandabear.recom.domain.document.entity.Document;
import com.pandabear.recom.domain.document.repository.DocumentRepository;
import com.pandabear.recom.domain.document.ro.ContentRO;
import com.pandabear.recom.domain.document.ro.DocumentRO;
import com.pandabear.recom.domain.document.type.ExportType;
import com.pandabear.recom.global.config.AppProperties;
import com.pandabear.recom.global.redis.entity.DocAccessCode;
import com.pandabear.recom.global.redis.service.AccessCodeService;
import com.pandabear.recom.global.util.DocumentUtil;
import com.pandabear.recom.global.util.FileUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
@Transactional
@RequiredArgsConstructor
public class DocumentService {

    private final DocumentRepository documentRepository;
    private final AccessCodeService accessCodeService;
    private final DocumentUtil documentUtil;
    private final AppProperties appProperties;

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
        Document document = new Document(null, dto.getTitle(), dto.getContent(), dto.getAddress(), LocalDateTime.now());
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

    public void download(String code, ExportType exportType,
                         HttpServletResponse response) {
        long documentId = accessCodeService.findByKey(code).getDocumentId();
        Document document = documentRepository.findById(documentId)
                .orElseThrow(Document.NotExistedException::new);
        FileUtil fileUtil = FileUtil.getFileUtil(exportType, appProperties, documentUtil);
        try {
            String path = fileUtil.parseFileInfo(document);
            File file = new File(path);
            String extensionName = file.getName().split("\\.")[1];
            response.setHeader("Content-Disposition", "attachment;filename=" + code + "." + extensionName);

            FileInputStream fileInputStream = new FileInputStream(path);
            OutputStream outputStream = response.getOutputStream();

            int read = 0;
            byte[] buffer = new byte[1024];
            while ((read = fileInputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, read);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
