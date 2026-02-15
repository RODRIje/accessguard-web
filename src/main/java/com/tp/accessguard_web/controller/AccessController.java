package com.tp.accessguard_web.controller;

import com.tp.accessguard_web.dto.AccessCheckRequest;
import com.tp.accessguard_web.dto.AccessCheckResponse;
import com.tp.accessguard_web.service.AccessService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/access")
public class AccessController {

    private final AccessService accessService;

    public AccessController(AccessService accessService) {
        this.accessService = accessService;
    }

    @PostMapping("/check")
    public ResponseEntity<AccessCheckResponse> checkAccess (@RequestBody AccessCheckRequest request) {
        AccessCheckResponse response = accessService.checkAccess(request);
        return ResponseEntity.ok(response);
    }
}
