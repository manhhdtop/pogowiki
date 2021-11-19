package com.pogowiki.controller;

import com.pogowiki.model.request.CrawRequest;
import com.pogowiki.model.response.ApiBaseResponse;
import com.pogowiki.service.CrawService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/craw")
@Slf4j
public class CrawController {
    @Autowired
    private CrawService crawService;

    @GetMapping
    public ResponseEntity<?> getListCraw(CrawRequest request) throws Exception {
        return ResponseEntity.ok(ApiBaseResponse.success(crawService.getListCraw(request)));
    }

    @PostMapping
    public ResponseEntity<?> addCraw() {
        return ResponseEntity.ok(ApiBaseResponse.success());
    }
}
