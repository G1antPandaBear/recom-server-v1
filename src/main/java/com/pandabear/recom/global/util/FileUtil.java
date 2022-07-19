package com.pandabear.recom.global.util;

import com.pandabear.recom.domain.document.entity.Document;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

public interface FileUtil {

    void parseFileInfo(Document document, MultipartFile multipartFile)
            throws IOException;

    static void addFile(Document document, String fileName) {
        document.addRecordFile(fileName);
    }

    static void createDir(File file) {
        if (!file.exists()) {
            file.mkdirs();
        }
    }

    static String randomName(String contentType) {
        return System.nanoTime() + contentType;
    }
}
