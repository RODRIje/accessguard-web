package com.tp.accessguard_web.controller;

import com.tp.accessguard_web.dto.PermissionRequestDTO;
import com.tp.accessguard_web.dto.PermissionResponseDTO;
import com.tp.accessguard_web.service.PermissionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/permission")
public class PermissionController {

    private final PermissionService permissionService;

    public PermissionController(PermissionService permissionService){
        this.permissionService = permissionService;
    }

    @PostMapping
    public ResponseEntity<PermissionResponseDTO> create(
            @RequestBody PermissionRequestDTO dto) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(permissionService.createPermission(dto));
    }

    @PutMapping("/{id}/expire")
    public ResponseEntity<Void> expire(@PathVariable Long id) {
        permissionService.expirePermission(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/person/{personId}")
    public ResponseEntity<List<PermissionResponseDTO>> getByPerson(
            @PathVariable Long personId) {

        return ResponseEntity.ok(permissionService.getByPerson(personId));
    }

    @GetMapping("/sector/{sectorId}")
    public ResponseEntity<List<PermissionResponseDTO>> getBySector(
            @PathVariable Long sectorId) {

        return ResponseEntity.ok(permissionService.getBySector(sectorId));
    }
}
