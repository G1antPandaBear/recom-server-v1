package com.pandabear.recom.domain.document.controller;

import com.pandabear.recom.domain.document.dto.ContentDto;
import com.pandabear.recom.domain.document.ro.Content;
import com.pandabear.recom.domain.document.ro.ContentRO;
import com.pandabear.recom.domain.document.ro.DocumentRO;
import com.pandabear.recom.domain.document.service.DocumentService;
import com.pandabear.recom.domain.document.type.ExportType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/document")
@RequiredArgsConstructor
public class DocumentController {

    private final DocumentService documentService;

    @PostMapping
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    public DocumentRO create(@RequestBody ContentDto contentDto) {
        return documentService.create(contentDto);
    }

    @GetMapping("/{code}")
    public String getByCode(Model model, @PathVariable String code) {
        ContentRO contentRO = documentService.findByCode(code);
        model.addAttribute("contents", contentRO.getContents());
        model.addAttribute("title", contentRO.getTitle());
        model.addAttribute("address", contentRO.getAddress());
        model.addAttribute("createdAt", contentRO.getCreatedAt());
        model.addAttribute("code", code);
        return "document";
    }

    @GetMapping("/{code}/modify")
    public String modify(Model model, @PathVariable String code) {
        ContentRO contentRO = documentService.findByCode(code);
        model.addAttribute("code", code);
        List<Content> contentList = contentRO.getContents();
        StringBuilder stringBuilder = new StringBuilder();
        for (Content con : contentList) {
            stringBuilder.append(con.getContent());
        }

        model.addAttribute("content", stringBuilder.toString());
        return "update";
    }

    @PostMapping("/{code}")
    public String updateByCode(Model model, @PathVariable String code, @RequestParam String content) {
        ContentRO contentRO = documentService.update(code, content);
        model.addAttribute("contents", contentRO.getContents());
        model.addAttribute("title", contentRO.getTitle());
        model.addAttribute("address", contentRO.getAddress());
        model.addAttribute("createdAt", contentRO.getCreatedAt());
        model.addAttribute("code", code);
        return "document";
    }

    @GetMapping("/{code}/download")
    @ResponseBody
    public void download(@PathVariable String code,
                         @RequestParam(name = "export-type", defaultValue = "DOCX") ExportType exportType,
                         HttpServletResponse response) {
        documentService.download(code, exportType, response);
    }
}
