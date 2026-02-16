package com.tp.accessguard_web.service.impl;

import com.tp.accessguard_web.dto.PersonRequestDTO;
import com.tp.accessguard_web.dto.PersonResponseDTO;
import com.tp.accessguard_web.exception.BadRequestException;
import com.tp.accessguard_web.exception.ResourceNotFoundException;
import com.tp.accessguard_web.model.Person;
import com.tp.accessguard_web.model.enums.PersonStatus;
import com.tp.accessguard_web.repository.PersonRepository;
import com.tp.accessguard_web.service.PersonService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class PersonServiceImpl implements PersonService {

    private final PersonRepository personRepo;

    public PersonServiceImpl(PersonRepository personRepo) {
        this.personRepo = personRepo;
    }

    @Override
    public PersonResponseDTO create(PersonRequestDTO dto) {
        if (personRepo.existsByBadgeId(dto.getBadgeId())){
            throw new BadRequestException("Ya existe una persona con ese badgeId");
        }

        Person person = new Person();
        person.setFullName(dto.getName());
        person.setBadgeId(dto.getBadgeId());
        person.setDocumentId(dto.getDocument());
        person.setStatus(PersonStatus.ACTIVE);

        return mapToResponse(personRepo.save(person));
    }

    @Override
    public PersonResponseDTO update(Long id, PersonRequestDTO dto) {
        Person person = personRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Persona no encontrada"));

        person.setFullName(dto.getName());
        person.setDocumentId(dto.getDocument());

        return mapToResponse(personRepo.save(person));
    }

    @Override
    public PersonResponseDTO getById(Long id) {
        Person person = personRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Persona no encontrada"));

        return mapToResponse(person);
    }

    @Override
    public Page<PersonResponseDTO> getAll(Pageable pageable) {
        return personRepo.findAll(pageable)
                .map(this::mapToResponse);
    }

    @Override
    public void desactivate(Long id) {
        Person person = personRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Persona no encontrada"));

        person.setStatus(PersonStatus.BLOQUED);
        personRepo.save(person);


    }

    private PersonResponseDTO mapToResponse(Person person) {
        PersonResponseDTO dto = new PersonResponseDTO();
        dto.setId(person.getId());
        dto.setName(person.getFullName());
        dto.setBadgeId(person.getBadgeId());
        dto.setDocument(person.getDocumentId());
        dto.setStatus(person.getStatus().name());
        return dto;
    }
}
