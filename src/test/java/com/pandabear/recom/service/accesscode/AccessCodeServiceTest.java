package com.pandabear.recom.service.accesscode;

import com.pandabear.recom.domain.document.ro.DocumentIdRO;
import com.pandabear.recom.global.redis.entity.DocAccessCode;
import com.pandabear.recom.global.redis.repository.DocAccessCodeRepository;
import com.pandabear.recom.global.redis.service.AccessCodeService;
import com.pandabear.recom.util.TestUtil;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.annotation.DirtiesContext;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class AccessCodeServiceTest {

    @Mock
    private DocAccessCodeRepository docAccessCodeRepository;

    @InjectMocks
    private AccessCodeService accessCodeService;

    @DisplayName("문서 아이디, 키 값으로 찾기 실패")
    @Test
    void findByKeyFailed() {
        // given


        // when
        when(docAccessCodeRepository.findById(anyString()))
                .thenThrow(new DocAccessCode.NotExistedException());

        // then
        assertThrows(DocAccessCode.NotExistedException.class,
                () -> accessCodeService.findByKey(anyString()));
    }

    @DisplayName("문서 아이디 키 값으로 찾기 성공")
    @Test
    void findByKeySuccess() {
        // given
        String key = TestUtil.randomString(255);
        DocAccessCode docAccessCode = new DocAccessCode(key, 0);

        when(docAccessCodeRepository.findById(anyString()))
                .thenReturn(Optional.of(docAccessCode));

        // when
        DocumentIdRO idRO = accessCodeService.findByKey(key);

        // then
        assertThat(idRO).isNotNull();
        assertThat(idRO.getDocumentId()).isEqualTo(0);
    }

    @DisplayName("문서 접근 코드 생성 성공")
    @Test
    void saveDocAccessCodeSuccess() {
        // given
        String key =TestUtil.randomString(255);
        DocAccessCode docAccessCode = new DocAccessCode(key, 0);
        when(docAccessCodeRepository.save(any(DocAccessCode.class)))
                .thenReturn(docAccessCode);

        // when
        DocAccessCode saveAccessCode = accessCodeService.create(0);

        // then
        assertThat(saveAccessCode).isNotNull();
        assertThat(saveAccessCode.getDocumentId()).isEqualTo(0);
    }
}
