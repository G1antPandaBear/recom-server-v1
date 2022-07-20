package com.pandabear.recom.global.util;

import com.pandabear.recom.domain.document.entity.Document;
import com.pandabear.recom.domain.document.ro.Content;
import com.pandabear.recom.global.config.AppProperties;
import kr.dogfoot.hwplib.object.HWPFile;
import kr.dogfoot.hwplib.object.bodytext.Section;
import kr.dogfoot.hwplib.object.bodytext.paragraph.Paragraph;
import kr.dogfoot.hwplib.tool.blankfilemaker.BlankFileMaker;
import kr.dogfoot.hwplib.writer.HWPWriter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class HwpFileUtil implements FileUtil {

    private final AppProperties appProperties;
    private final DocumentUtil documentUtil;

    @Override
    public String parseFileInfo(Document document) {
        String path = appProperties.getUploadUri() + "/out.hwp";

        try {
            HWPFile hwpFile = BlankFileMaker.make();
            Section s = hwpFile.getBodyText().getSectionList()
                    .get(0);
            Paragraph paragraph = s.getParagraph(0);
            List<Content> contents = documentUtil.parseDocuments(document);
            for (Content content : contents) {
                paragraph.getText().addString(content.getContent() + " ");
            }
            HWPWriter.toFile(hwpFile, path);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return path;
    }
}
