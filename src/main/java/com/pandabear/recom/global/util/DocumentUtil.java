package com.pandabear.recom.global.util;

import com.pandabear.recom.domain.document.entity.Document;
import com.pandabear.recom.domain.document.ro.Content;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;

@Component
public class DocumentUtil {

    public List<Content> parseDocuments(Document document) {
        List<Content> contents = new LinkedList<>();
        String[] splitContents = document.getContent().split("\\\\");
        for (String content : splitContents) {
            if (content.startsWith("#")) {
                content = content.replace("#", "");
                contents.add(new Content(content, true));
            }
            else {
                contents.add(new Content(content, false));
            }
        }

        return contents;
    }
}
