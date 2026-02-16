package com.tp.accessguard_web.controller;

import com.tp.accessguard_web.dto.PersonRequestDTO;
import com.tp.accessguard_web.dto.PersonResponseDTO;
import com.tp.accessguard_web.service.PersonService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.data.domain.Pageable;

@RestController
@RequestMapping("/api/persons")
public class PersonController {

    private final PersonService personService;

    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @PostMapping
    public ResponseEntity<PersonResponseDTO> create(@RequestBody PersonRequestDTO dto){
        return ResponseEntity.ok(personService.create(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PersonResponseDTO> getPersonById(@PathVariable Long id){
        return ResponseEntity.ok(personService.getById(id));
    }

    @GetMapping
    public ResponseEntity<Page<PersonResponseDTO>> getAll(Pageable pageable) {
        return ResponseEntity.ok(personService.getAll((org.springframework.data.domain.Pageable) pageable));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PersonResponseDTO> update(
            @PathVariable Long id,
            @RequestBody PersonRequestDTO dto) {
        return ResponseEntity.ok(personService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deactivate(@PathVariable Long id) {
        personService.desactivate(id);
        return ResponseEntity.noContent().build();
    }
}
