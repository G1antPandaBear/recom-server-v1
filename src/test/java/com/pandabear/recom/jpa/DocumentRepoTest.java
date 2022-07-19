package com.pandabear.recom.jpa;

import com.pandabear.recom.domain.document.entity.Document;
import com.pandabear.recom.domain.document.repository.DocumentRepository;
import com.pandabear.recom.util.TestUtil;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@TestPropertySource(properties = "classpath:application-test.properties")
public class DocumentRepoTest {

    @Autowired
    private DocumentRepository documentRepository;

    @DisplayName("문서 저장 테스트")
    @Test
    void saveDocument() {
        // given
        String content = TestUtil.randomString(1024);
        Document document = new Document(1L, content, null);
    
        // when
        documentRepository.save(document);
        
        // then
        assertThat(document).isNotNull();
        assertThat(document.getId()).isEqualTo(1L);
        assertThat(document.getContent()).isEqualTo(content);
    }
    
    @DisplayName("문서 조회 테스트")
    @Test
    void findDocument() {
        // given
        Document document = new Document(null, TestUtil.randomString(1024), null);
        documentRepository.save(document);
    
        // when
        Document findDocument = documentRepository.findById(1L)
                .orElseThrow();
        
        // then
        assertThat(findDocument).isNotNull();
        assertThat(findDocument.getId()).isEqualTo(1L);
    }
}
