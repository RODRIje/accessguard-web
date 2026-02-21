package com.tp.accessguard_web.service;

import com.tp.accessguard_web.dto.PermissionRequestDTO;
import com.tp.accessguard_web.dto.PermissionResponseDTO;

import java.util.List;

public interface PermissionService {

    PermissionResponseDTO createPermission(PermissionRequestDTO dto);

    void expirePermission(Long id);

    List<PermissionResponseDTO> getByPerson(Long personId);

    List<PermissionResponseDTO> getBySector(Long sectorId);

    boolean hasValidPermission(Long personId, Long sectorId);
}
