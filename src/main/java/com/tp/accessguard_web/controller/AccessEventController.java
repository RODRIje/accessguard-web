package com.tp.accessguard_web.controller;

import com.tp.accessguard_web.dto.AccessCheckRequestDTO;
import com.tp.accessguard_web.dto.AccessEventResponseDTO;
import com.tp.accessguard_web.model.enums.EventResult;
import com.tp.accessguard_web.service.AccessEventService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.data.domain.Pageable;

@RestController
@RequestMapping("/api/access-events")
public class AccessEventController {

    private final AccessEventService service;

    public AccessEventController(AccessEventService service) {
        this.service = service;
    }

    @PostMapping("/check")
    public ResponseEntity<AccessEventResponseDTO> checkAccess(
            @RequestBody AccessCheckRequestDTO dto) {

        return ResponseEntity.ok(service.registerAccess(dto));
    }

    @GetMapping
    public ResponseEntity<Page<AccessEventResponseDTO>> getAll(
            @RequestParam(required = false) EventResult result,
            Pageable pageable) {

        if (result != null) {
            return ResponseEntity.ok(service.getByResult(result, pageable));
        }

        return ResponseEntity.ok(service.getAll(pageable));
    }

    @GetMapping("/person/{personId}")
    public ResponseEntity<Page<AccessEventResponseDTO>> getByPerson(
            @PathVariable Long personId,
            Pageable pageable) {

        return ResponseEntity.ok(service.getByPerson(personId, pageable));
    }

    @GetMapping("/sector/{sectorId}")
    public ResponseEntity<Page<AccessEventResponseDTO>> getBySector(
            @PathVariable Long sectorId,
            Pageable pageable) {

        return ResponseEntity.ok(service.getBySector(sectorId, pageable));
    }
}
