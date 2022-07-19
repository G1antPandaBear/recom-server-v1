package com.pandabear.recom.global.util;

import com.pandabear.recom.domain.document.entity.Document;
import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.Date;

@Component
@Qualifier("docxFileUtil")
public class DocxFileUtil implements FileUtil {

    @Override
    public void parseFileInfo(Document document) throws IOException {
        String path = "docx/" + simpleDateFormat().format(new Date());

        try {
            WordprocessingMLPackage wordprocessingMLPackage
                    = WordprocessingMLPackage.createPackage();
            wordprocessingMLPackage.getMainDocumentPart()
                    .addParagraphOfText(document.getContent());
            wordprocessingMLPackage.save(new File("C:\\Users\\DGSW\\Desktop\\out.docx"));
        } catch (Docx4JException e) {
            e.printStackTrace();
        }
    }
}
