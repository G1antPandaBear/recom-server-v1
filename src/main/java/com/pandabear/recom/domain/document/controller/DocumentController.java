package com.pandabear.recom.domain.document.controller;

import com.pandabear.recom.domain.document.dto.ContentDto;
import com.pandabear.recom.domain.document.ro.ContentRO;
import com.pandabear.recom.domain.document.ro.DocumentRO;
import com.pandabear.recom.domain.document.service.DocumentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/document")
@RequiredArgsConstructor
public class DocumentController {

    private final DocumentService documentService;

    @PostMapping
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    public DocumentRO create(@RequestBody ContentDto contentDto) {
        return documentService.create(contentDto.getContent());
    }

    @GetMapping("/{code}")
    public String getByCode(Model model, @PathVariable String code) {
        ContentRO contentRO = documentService.findByCode(code);
        model.addAttribute("contents", contentRO.getContents());
        return "document";
    }

    @PatchMapping("/{code}")
    public String updateByCode(Model model, @PathVariable String code, @RequestBody ContentDto contentDto) {
        ContentRO contentRO = documentService.update(code, contentDto.getContent());
        model.addAttribute("contents", contentRO.getContents());
        return "document";
    }
}
