package com.tp.accessguard_web.service.impl;

import com.tp.accessguard_web.dto.AccessCheckRequestDTO;
import com.tp.accessguard_web.dto.AccessEventResponseDTO;
import com.tp.accessguard_web.exception.ResourceNotFoundException;
import com.tp.accessguard_web.model.AccessEvent;
import com.tp.accessguard_web.model.Person;
import com.tp.accessguard_web.model.Sector;
import com.tp.accessguard_web.model.enums.EventResult;
import com.tp.accessguard_web.repository.AccessEventRepository;
import com.tp.accessguard_web.repository.PermissionRepository;
import com.tp.accessguard_web.repository.PersonRepository;
import com.tp.accessguard_web.repository.SectorRepository;
import com.tp.accessguard_web.service.AccessEventService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class AccessEventServiceImpl implements AccessEventService {

    private final AccessEventRepository accessEventRepo;
    private final PersonRepository personRepo;
    private final SectorRepository sectorRepo;
    private final PermissionRepository permissionRepo;

    public AccessEventServiceImpl(
            AccessEventRepository accessEventRepo,
            PersonRepository personRepo,
            SectorRepository sectorRepo,
            PermissionRepository permissionRepo) {

        this.accessEventRepo = accessEventRepo;
        this.personRepo = personRepo;
        this.sectorRepo = sectorRepo;
        this.permissionRepo = permissionRepo;
    }

    @Override
    public AccessEventResponseDTO registerAccess(AccessCheckRequestDTO dto) {

        Person person = personRepo.findById(dto.getPersonId())
                .orElseThrow(() -> new ResourceNotFoundException("Persona no encontrada"));

        Sector sector = sectorRepo.findById(dto.getSectorId())
                .orElseThrow(() -> new ResourceNotFoundException("Sector no encontrado"));

        boolean hasPermission = permissionRepo
                .findValidPermission(
                        dto.getPersonId(),
                        dto.getSectorId(),
                        LocalDateTime.now()
                )
                .isPresent();

        AccessEvent event = new AccessEvent();
        event.setPerson(person);
        event.setSector(sector);
        event.setTs(LocalDateTime.now());

        if (hasPermission) {
            event.setResult(EventResult.ALLOW);
            event.setReason("Acceso autorizado");
        } else {
            event.setResult(EventResult.DENY);
            event.setReason("No posee permiso vigente");
        }

        return mapToResponse(accessEventRepo.save(event));
    }

    @Override
    public Page<AccessEventResponseDTO> getAll(Pageable pageable) {
        return accessEventRepo.findAll(pageable)
                .map(this::mapToResponse);
    }

    @Override
    public Page<AccessEventResponseDTO> getByResult(EventResult result, Pageable pageable) {
        return accessEventRepo.findByResult(result, pageable)
                .map(this::mapToResponse);
    }

    @Override
    public Page<AccessEventResponseDTO> getByPerson(Long personId, Pageable pageable) {
        return accessEventRepo.findByPersonId(personId, pageable)
                .map(this::mapToResponse);
    }

    @Override
    public Page<AccessEventResponseDTO> getBySector(Long sectorId, Pageable pageable) {
        return accessEventRepo.findBySectorId(sectorId, pageable)
                .map(this::mapToResponse);
    }

    private AccessEventResponseDTO mapToResponse(AccessEvent event) {

        AccessEventResponseDTO dto = new AccessEventResponseDTO();

        dto.setId(event.getId());
        dto.setPersonId(event.getPerson().getId());
        dto.setPersonName(event.getPerson().getFullName());

        dto.setSectorId(event.getSector().getId());
        dto.setSectorName(event.getSector().getName());

        dto.setTs(event.getTs());
        dto.setResult(event.getResult());
        dto.setReason(event.getReason());

        return dto;
    }
}
