package com.pandabear.recom.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class MultiPartFileTest {

    private MockMultipartFile mockMultipartFile(String fileName,
                                            String contentType,
                                            String filePath) throws IOException {
        return new MockMultipartFile(
                fileName, fileName + "." + contentType,
                contentType, this.getClass().getResourceAsStream(filePath));
    }
    
    @DisplayName("MockMultipartFile 작동 테스트")
    @Test
    void mockMultipartFile() throws Exception {
        // given
        String fileName = "test";
        String contentType = ".mp3";
        String filePath = "test.mp3";

        // when
        MockMultipartFile mockMultipartFile = mockMultipartFile(fileName, contentType, filePath);
        String getFileName = mockMultipartFile.getOriginalFilename().toLowerCase();
        
        // then
        assertThat(getFileName).isEqualTo(fileName.toLowerCase() + "." + contentType);
        assertThat(mockMultipartFile.getContentType()).isEqualTo(contentType);
    }
}
