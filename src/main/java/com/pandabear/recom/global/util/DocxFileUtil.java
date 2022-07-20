package com.pandabear.recom.global.util;

import com.pandabear.recom.domain.document.entity.Document;
import com.pandabear.recom.domain.document.ro.Content;
import com.pandabear.recom.global.config.AppProperties;
import lombok.RequiredArgsConstructor;
import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;

import java.io.File;
import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
public class DocxFileUtil implements FileUtil {

    private final AppProperties appProperties;
    private final DocumentUtil documentUtil;

    @Override
    public String parseFileInfo(Document document) throws IOException {
        String path = appProperties.getUploadUri() + "/out.docx";

        try {
            WordprocessingMLPackage wordprocessingMLPackage
                    = WordprocessingMLPackage.createPackage();
            List<Content> contents = documentUtil.parseDocuments(document);
            for (Content content : contents) {
                wordprocessingMLPackage.getMainDocumentPart()
                        .addParagraphOfText(content.getContent());
            }
            wordprocessingMLPackage.save(new File(path));
        } catch (Docx4JException e) {
            e.printStackTrace();
        }
        return path;
    }
}
