package com.pandabear.recom.domain.document.controller;

import com.pandabear.recom.domain.document.ro.DocumentRo;
import com.pandabear.recom.domain.document.service.DocumentService;
import com.pandabear.recom.global.response.ResponseData;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

@Controller
@RequestMapping("/document")
@RequiredArgsConstructor
public class DocumentController {

    private final DocumentService documentService;

    @PostMapping("/")
    @ResponseBody
    public ResponseData<DocumentRo> create(@RequestBody String content) {
        DocumentRo documentRo = documentService.create(content);
        return new ResponseData<>(HttpStatus.CREATED, "문서 생성 성공", documentRo);
    }

    @GetMapping("/{code}")
    public String getByCode(Model model, @PathVariable String code) {
        DocumentRo documentRo = documentService.findByCode(code);
        model.addAttribute("content", documentRo.getContent());
        model.addAttribute("code", documentRo.getCode());
        return "document";
    }

    @PatchMapping("/{code}")
    public String updateByCode(Model model, @PathVariable String code, @RequestBody String content) {
        DocumentRo documentRo = documentService.update(code, content);
        model.addAttribute("code", documentRo.getCode());
        model.addAttribute("content", documentRo.getContent());
        return "document";
    }
}
