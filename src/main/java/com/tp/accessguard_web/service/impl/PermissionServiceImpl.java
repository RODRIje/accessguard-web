package com.tp.accessguard_web.service.impl;

import com.tp.accessguard_web.dto.PermissionRequestDTO;
import com.tp.accessguard_web.dto.PermissionResponseDTO;
import com.tp.accessguard_web.exception.BadRequestException;
import com.tp.accessguard_web.exception.ResourceNotFoundException;
import com.tp.accessguard_web.model.Permission;
import com.tp.accessguard_web.model.Person;
import com.tp.accessguard_web.model.Sector;
import com.tp.accessguard_web.repository.PermissionRepository;
import com.tp.accessguard_web.repository.PersonRepository;
import com.tp.accessguard_web.repository.SectorRepository;
import com.tp.accessguard_web.service.PermissionService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PermissionServiceImpl implements PermissionService {

    private final PermissionRepository permissionRepo;
    private final PersonRepository personRepo;
    private final SectorRepository sectorRepo;

    public PermissionServiceImpl(PermissionRepository permissionRepo, PersonRepository personRepo, SectorRepository sectorRepo){
        this.permissionRepo = permissionRepo;
        this.personRepo = personRepo;
        this.sectorRepo = sectorRepo;
    }

    @Override
    public PermissionResponseDTO createPermission(PermissionRequestDTO dto) {
        Person person = personRepo.findById(dto.getPersonId())
                .orElseThrow(() -> new ResourceNotFoundException("Persona no encontrada"));

        Sector sector = sectorRepo.findById(dto.getSectorId())
                .orElseThrow(() -> new ResourceNotFoundException("Sector no encontrado"));

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime validFrom = dto.getValidFrom() != null ? dto.getValidFrom() : now;
        LocalDateTime validTo = dto.getValidTo();

        if (validTo != null && validTo.isBefore(validFrom)) {
            throw new BadRequestException("La fecha de vencimiento no puede ser anterior a la fecha de inicio");
        }

        if (permissionRepo.findValidPermission(
                person.getId(), sector.getId(), now).isPresent()) {

            throw new BadRequestException(
                    "Ya existe un permiso vigente para esta persona en este sector");
        }

        Permission permission = new Permission();
        permission.setPerson(person);
        permission.setSector(sector);
        permission.setValidFrom(validFrom);
        permission.setValidTo(validTo);
        permission.setCreatedBy(dto.getCreatedBy());

        return mapToResponse(permissionRepo.save(permission));
    }

    @Override
    public void expirePermission(Long id) {

        Permission permission = permissionRepo.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Permiso no encontrado"));

        permission.setValidTo(LocalDateTime.now());
        permissionRepo.save(permission);
    }

    @Override
    public List<PermissionResponseDTO> getByPerson(Long personId) {
        return permissionRepo.findByPersonId(personId)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<PermissionResponseDTO> getBySector(Long sectorId) {
        return permissionRepo.findBySectorId(sectorId)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public boolean hasValidPermission(Long personId, Long sectorId) {
        return permissionRepo
                .findValidPermission(personId, sectorId, LocalDateTime.now())
                .isPresent();
    }

    private PermissionResponseDTO mapToResponse(Permission permission){

        PermissionResponseDTO dto = new PermissionResponseDTO();

        dto.setId(permission.getId());
        dto.setPersonId(permission.getPerson().getId());
        dto.setPersonName(permission.getPerson().getFullName());

        dto.setSectorId(permission.getSector().getId());
        dto.setSectorName(permission.getSector().getName());

        dto.setValidFrom(permission.getValidFrom());
        dto.setValidTo(permission.getValidTo());
        dto.setCreatedBy(permission.getCreatedBy());

        return dto;
    }
}
