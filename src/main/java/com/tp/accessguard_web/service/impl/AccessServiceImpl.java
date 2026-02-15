package com.tp.accessguard_web.service.impl;

import com.tp.accessguard_web.dto.AccessCheckRequest;
import com.tp.accessguard_web.dto.AccessCheckResponse;
import com.tp.accessguard_web.model.AccessEvent;
import com.tp.accessguard_web.model.Person;
import com.tp.accessguard_web.model.Sector;
import com.tp.accessguard_web.model.enums.PersonStatus;
import com.tp.accessguard_web.repository.AccessEventRepository;
import com.tp.accessguard_web.repository.PermissionRepository;
import com.tp.accessguard_web.repository.PersonRepository;
import com.tp.accessguard_web.repository.SectorRepository;
import com.tp.accessguard_web.service.AccessService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import com.tp.accessguard_web.model.enums.EventResult;


import java.time.LocalDateTime;
import java.util.Optional;




@Service
public class AccessServiceImpl implements AccessService {

    private final PersonRepository personRepo;
    private final SectorRepository sectorRepo;
    private final PermissionRepository permissionRepo;
    private final AccessEventRepository eventRepo;

    public AccessServiceImpl(PersonRepository personRepo,
                             SectorRepository sectorRepo,
                             PermissionRepository permissionRepo,
                             AccessEventRepository eventRepo){
        this.personRepo = personRepo;
        this.sectorRepo = sectorRepo;
        this.permissionRepo = permissionRepo;
        this.eventRepo = eventRepo;
    }


    @Override
    @Transactional
    public AccessCheckResponse checkAccess(AccessCheckRequest request) {
        String badgeId = request.getBadgeId();
        String sectorCode = request.getSectorCode();
        LocalDateTime ts = request.getTimestamp();

        if (ts == null) ts = LocalDateTime.now();

        // 1) persona
        Optional<Person> optionalPerson = personRepo.findByBadgeId(badgeId);
        if (optionalPerson.isEmpty()){
            logEvent(null, null, ts, EventResult.DENY, "Persona no encontrada");
            return new AccessCheckResponse(false, "Persona no encontrada");
        }

        Person person = optionalPerson.get();

        if (person.getStatus() == PersonStatus.BLOQUED){
            logEvent(person, null, ts, EventResult.DENY, "Persona bloqueada");
            return new AccessCheckResponse(false, "Persona bloqueada");
        }

        // 2) sector activo
        Optional<Sector> optionalSector = sectorRepo.findByCodeAndActiveTrue(sectorCode);
        if (optionalSector.isEmpty()){
            logEvent(person, null, ts, EventResult.DENY, "Sector inexistente o inactivo");
            return new AccessCheckResponse(false, "Sector inexistente o inactivo");
        }

        Sector sector = optionalSector.get();

        // 3) permiso vigente
        boolean hasPermission = hasValidPermission(person.getId(), sector.getId(), ts);

        if (!hasPermission){
            logEvent(person, sector, ts, EventResult.DENY, "Sin permiso vigente");
            return new AccessCheckResponse(false, "Sin permiso vigente");
        }

        logEvent(person, sector, ts, EventResult.ALLOW, "Acceso permitido");
        return new AccessCheckResponse(true,"Acceso permitido");
    }

    private boolean hasValidPermission(Long personId, Long sectorId, LocalDateTime ts){
        return permissionRepo
                .findFirstByPersonIdAndSectorIdAndValidFromLessThanEqualAndValidToGreaterThanEqual(
                        personId, sectorId, ts, ts
                ).isPresent()
                || permissionRepo
                .findFirstByPersonIdAndSectorIdAndValidFromLessThanEqualAndValidToIsNull(
                        personId, sectorId, ts
                ).isPresent();
    }

    private void logEvent(Person person, Sector sector, LocalDateTime ts,EventResult result,String reason){
        if (person == null || sector == null){
            return;
        }

        AccessEvent event = new AccessEvent();
        event.setPerson(person);
        event.setSector(sector);
        event.setTs(ts);
        event.setResult(result);
        event.setReason(reason);
        eventRepo.save(event);
    }
}
