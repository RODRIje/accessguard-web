package com.tp.accessguard_web.controller;

import com.tp.accessguard_web.dto.SectorRequestDTO;
import com.tp.accessguard_web.dto.SectorResponseDTO;
import com.tp.accessguard_web.service.SectorService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.data.domain.Pageable;

@RestController
@RequestMapping("/api/sectors")
public class SectorController {

        private final SectorService sectorService;

        public SectorController(SectorService sectorService) {
            this.sectorService = sectorService;
        }

        @PostMapping
        public ResponseEntity<SectorResponseDTO> create(@RequestBody SectorRequestDTO dto) {
            return ResponseEntity.ok(sectorService.create(dto));
        }

        @GetMapping("/{id}")
        public ResponseEntity<SectorResponseDTO> getById(@PathVariable Long id) {
            return ResponseEntity.ok(sectorService.getById(id));
        }

        @GetMapping
        public ResponseEntity<Page<SectorResponseDTO>> getAll(Pageable pageable) {
            return ResponseEntity.ok(sectorService.getAll(pageable));
        }

        @PutMapping("/{id}")
        public ResponseEntity<SectorResponseDTO> update(
                @PathVariable Long id,
                @RequestBody SectorRequestDTO dto) {

            return ResponseEntity.ok(sectorService.update(id, dto));
        }

        @DeleteMapping("/{id}")
        public ResponseEntity<Void> deactivate(@PathVariable Long id) {
            sectorService.desactivate(id);
            return ResponseEntity.noContent().build();
        }
}
