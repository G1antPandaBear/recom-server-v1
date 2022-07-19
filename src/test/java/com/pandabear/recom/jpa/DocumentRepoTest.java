package com.pandabear.recom.jpa;

import com.pandabear.recom.domain.document.entity.Document;
import com.pandabear.recom.domain.document.repository.DocumentRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;

import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@TestPropertySource(properties = "classpath:application-test.properties")
public class DocumentRepoTest {

    @Autowired
    private DocumentRepository documentRepository;

    private String randomString() {
        return new Random().ints(48, 123)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >=97))
                .limit(1024)
                .collect(
                        StringBuilder::new,
                        StringBuilder::appendCodePoint,
                        StringBuilder::append
                ).toString();
    }

    @DisplayName("문서 저장 테스트")
    @Test
    void saveDocument() {
        // given
        String content = randomString();
        Document document = new Document(1L, content);
    
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
        Document document = new Document(null, randomString());
        documentRepository.save(document);
    
        // when
        Document findDocument = documentRepository.findById(1L)
                .orElseThrow();
        
        // then
        assertThat(findDocument).isNotNull();
        assertThat(findDocument.getId()).isEqualTo(1L);
    }
}
