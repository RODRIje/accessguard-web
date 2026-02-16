package com.tp.accessguard_web.controller;

import com.tp.accessguard_web.dto.AccessCheckRequest;
import com.tp.accessguard_web.dto.AccessCheckResponse;
import com.tp.accessguard_web.dto.AccessEventResponse;
import com.tp.accessguard_web.service.AccessService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("/events")
    public ResponseEntity<List<AccessEventResponse>> getEventsByPerson(@RequestParam String badgeId){

        List<AccessEventResponse> events = accessService.getEventsByPerson(badgeId);

        return ResponseEntity.ok(events);
    }
}
