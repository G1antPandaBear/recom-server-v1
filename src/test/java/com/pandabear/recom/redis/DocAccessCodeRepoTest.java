package com.pandabear.recom.redis;

import com.pandabear.recom.global.redis.config.RedisConfig;
import com.pandabear.recom.global.redis.entity.DocAccessCode;
import com.pandabear.recom.global.redis.repository.DocAccessCodeRepository;
import com.pandabear.recom.util.TestUtil;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.redis.DataRedisTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.DirtiesContext;

import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;

@Import({RedisConfig.class})
@DataRedisTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class DocAccessCodeRepoTest {

    @Autowired
    private DocAccessCodeRepository docAccessCodeRepository;

    private long randomLong() {
        return new Random().nextLong();
    }

    @AfterEach
    void deleteAll() {
        docAccessCodeRepository.deleteAll();
    }

    @DisplayName("doc 코드 저장 및 조회")
    @RepeatedTest(value = 20)
    void saveAndFind() {
        // given
        String code = TestUtil.randomString(255);
        long docId = randomLong();
        DocAccessCode docAccessCode = new DocAccessCode(code, docId);
        docAccessCodeRepository.save(docAccessCode);

        // when
        DocAccessCode findAccessCode = docAccessCodeRepository.findById(code)
                .orElseThrow();

        // then
        assertThat(findAccessCode).isNotNull();
        assertThat(findAccessCode.getId()).isEqualTo(code);
        assertThat(findAccessCode.getDocumentId()).isEqualTo(docId);
    }
}
