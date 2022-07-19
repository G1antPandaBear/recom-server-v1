package com.pandabear.recom.service.document;

import com.pandabear.recom.domain.document.entity.Document;
import com.pandabear.recom.domain.document.repository.DocumentRepository;
import com.pandabear.recom.domain.document.ro.DocumentIdRO;
import com.pandabear.recom.domain.document.service.DocumentService;
import com.pandabear.recom.global.redis.service.AccessCodeService;
import com.pandabear.recom.global.util.FileUtil;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.annotation.DirtiesContext;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class DocumentServiceTest {

    @Mock private DocumentRepository documentRepository;
    @Mock private AccessCodeService accessCodeService;
    @Mock private FileUtil fileUtil;

    @InjectMocks
    private DocumentService documentService;

    @DisplayName("코드로 문서 조회 실패")
    @Test
    void findByCodeFailed() {
        // given
        when(accessCodeService.findByKey(anyString()))
                .thenReturn(new DocumentIdRO(1L));

        // when
        when(documentRepository.findById(anyLong()))
                .thenThrow(new Document.NotExistedException());

        // then
        assertThrows(Document.NotExistedException.class,
                () -> documentService.findByCode(anyString()));
    }

//    @DisplayName("코드로 문서 조회 성공")
//    @Test
//    void findByCodeSuccess() {
//        // given
//        Document document = new Document(1L, null, null);
//
//        // when
//
//
//        // then
//
//    }
}
