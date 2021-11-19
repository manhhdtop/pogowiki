package com.pogowiki.controller.admin;

import com.pogowiki.model.request.ListActionRequest;
import com.pogowiki.model.response.ApiBaseResponse;
import com.pogowiki.service.ActionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("${admin-endpoint:/admin}/action")
@Slf4j
public class ActionController {
    @Autowired
    private ActionService actionService;

    @GetMapping
    public ResponseEntity<?> getListAction(ListActionRequest request) {
        return ResponseEntity.ok(ApiBaseResponse.success(actionService.getListAction(request)));
    }

    @GetMapping("/active")
    public ResponseEntity<?> getActiveAction() {
        return ResponseEntity.ok(ApiBaseResponse.success(actionService.getActiveAction()));
    }
}
