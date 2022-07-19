package com.pandabear.recom.domain.document.controller;

import com.pandabear.recom.domain.document.dto.ContentDto;
import com.pandabear.recom.domain.document.ro.DocumentRo;
import com.pandabear.recom.domain.document.service.DocumentService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
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
    public DocumentRo create(@RequestBody ContentDto contentDto) {
        return documentService.create(contentDto.getContent());
    }

    @GetMapping("/{code}")
    @Cacheable(value = "codeCaching", key = "#code")
    public String getByCode(Model model, @PathVariable String code) {
        DocumentRo documentRo = documentService.findByCode(code);
        model.addAttribute("content", documentRo.getContent());
        model.addAttribute("code", documentRo.getCode());
        return "document";
    }

    @PatchMapping("/{code}")
    @CachePut(value = "codeCaching", key = "#code")
    public String updateByCode(Model model, @PathVariable String code, @RequestBody ContentDto contentDto) {
        DocumentRo documentRo = documentService.update(code, contentDto.getContent());
        model.addAttribute("code", documentRo.getCode());
        model.addAttribute("content", documentRo.getContent());
        return "document";
    }
}
