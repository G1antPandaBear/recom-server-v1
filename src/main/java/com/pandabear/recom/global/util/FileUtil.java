package com.pandabear.recom.global.util;

import com.pandabear.recom.domain.document.entity.Document;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;

public interface FileUtil {

    void parseFileInfo(Document document) throws IOException;

    static void createDir(File file) {
        if (!file.exists()) {
            file.mkdirs();
        }
    }

    default SimpleDateFormat simpleDateFormat() {
        return new SimpleDateFormat("yyyyMMdd");
    }

    static String getAbsolutePath() {
        return new File("").getAbsolutePath() + "\\";
    }

    static String getPath(String path, String fileName) {
        return getAbsolutePath() + path + "/" + fileName;
    }

    static String randomName(String contentType) {
        return System.nanoTime() + contentType;
    }
}
