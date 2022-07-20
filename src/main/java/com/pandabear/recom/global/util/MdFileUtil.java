package com.pandabear.recom.global.util;

import com.pandabear.recom.domain.document.entity.Document;
import com.pandabear.recom.domain.document.ro.Content;
import com.pandabear.recom.global.config.AppProperties;
import lombok.RequiredArgsConstructor;
import net.steppschuh.markdowngenerator.text.Text;
import net.steppschuh.markdowngenerator.text.emphasis.BoldText;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

@RequiredArgsConstructor
public class MdFileUtil implements FileUtil {

    private final AppProperties appProperties;
    private final DocumentUtil documentUtil;

    @Override
    public String parseFileInfo(Document document) throws IOException {
        String path = appProperties.getUploadUri() + "/out.md";
        StringBuilder stringBuilder = new StringBuilder();
        List<Content> contents = documentUtil.parseDocuments(document);
        for (Content content : contents) {
            if (content.isBold()) {
                stringBuilder.append(new BoldText(content.getContent()))
                        .append("\n");
            }
            else {
                stringBuilder.append(new Text(content.getContent()))
                        .append("\n");
            }
        }

        try (final BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(
                new FileOutputStream(path)
        )) {
            bufferedOutputStream.write(stringBuilder.toString().getBytes(StandardCharsets.UTF_8));
        }
        return path;
    }
}
