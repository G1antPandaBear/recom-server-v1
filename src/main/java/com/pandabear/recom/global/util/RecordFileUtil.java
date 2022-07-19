package com.pandabear.recom.global.util;

import com.pandabear.recom.domain.document.entity.Document;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
@Qualifier("recordFileUtil")
public class RecordFileUtil implements FileUtil {

    @Override
    public void parseFileInfo(Document document, MultipartFile multipartFile)
            throws IOException {
        if (multipartFile.isEmpty()) {
            return;
        }

        String currentDate = simpleDateFormat().format(new Date());
        String absolutePath = new File("").getAbsolutePath() + "\\";

        String path = "records/" + currentDate;
        File file = new File(path);
        FileUtil.createDir(file);
        String newFileName = convertFrom(multipartFile);
        if (newFileName.equals("")) {
            return;
        }

        FileUtil.addFile(document, newFileName);
        file = new File(absolutePath + path + "/" + newFileName);
        multipartFile.transferTo(file);
    }

    private SimpleDateFormat simpleDateFormat() {
        return new SimpleDateFormat("yyyyMMdd");
    }

    private String convertFrom(MultipartFile multipartFile) {
        String contentType = multipartFile.getContentType();
        if (ObjectUtils.isEmpty(contentType)) {
            return "";
        }
        else if (contentType.contains("audio/mpeg")) {
            return FileUtil.randomName(".mp3");
        }
        return "";
    }
}
