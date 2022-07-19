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

    private String randomString(int limit) {
        return new Random().ints(48, 123)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >=97))
                .limit(limit)
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
        String code = randomString(255);
        String content = randomString(1024);
        Document document = new Document(code, content);
    
        // when
        documentRepository.save(document);
        
        // then
        assertThat(document).isNotNull();
        assertThat(document.getId()).isEqualTo(code);
        assertThat(document.getContent()).isEqualTo(content);
    }
    
    @DisplayName("문서 조회 테스트")
    @Test
    void findDocument() {
        // given
        String code = randomString(255);
        Document document = new Document(code, randomString(1024));
        documentRepository.save(document);
    
        // when
        Document findDocument = documentRepository.findById(code)
                .orElseThrow();
        
        // then
        assertThat(findDocument).isNotNull();
        assertThat(findDocument.getId()).isEqualTo(code);
    }
}
