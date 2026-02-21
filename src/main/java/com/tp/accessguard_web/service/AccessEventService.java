package com.tp.accessguard_web.service;

import com.tp.accessguard_web.dto.AccessCheckRequestDTO;
import com.tp.accessguard_web.dto.AccessEventResponseDTO;
import com.tp.accessguard_web.model.enums.EventResult;
import org.springframework.data.domain.Page;

import org.springframework.data.domain.Pageable;

public interface AccessEventService {

    AccessEventResponseDTO registerAccess(AccessCheckRequestDTO dto);

    Page<AccessEventResponseDTO> getAll(Pageable pageable);

    Page<AccessEventResponseDTO> getByResult(EventResult result, Pageable pageable);

    Page<AccessEventResponseDTO> getByPerson(Long personId, Pageable pageable);

    Page<AccessEventResponseDTO> getBySector(Long sectorId, Pageable pageable);
}
