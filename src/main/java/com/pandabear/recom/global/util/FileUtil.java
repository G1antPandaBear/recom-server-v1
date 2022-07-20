package com.pandabear.recom.global.util;

import com.pandabear.recom.domain.document.entity.Document;
import com.pandabear.recom.domain.document.type.ExportType;
import com.pandabear.recom.global.config.AppProperties;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;

public interface FileUtil {

    String parseFileInfo(Document document) throws IOException;

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

    static FileUtil getFileUtil(ExportType exportType,
                                AppProperties appProperties, DocumentUtil documentUtil) {
        if (exportType == ExportType.MD) {
            return new MdFileUtil(appProperties, documentUtil);
        }
        else if (exportType== ExportType.HWP) {
            return new HwpFileUtil(appProperties, documentUtil);
        }
        else {
            return new DocxFileUtil(appProperties, documentUtil);
        }
    }

}
