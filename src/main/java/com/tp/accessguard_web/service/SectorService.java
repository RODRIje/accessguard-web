package com.tp.accessguard_web.service;

import com.tp.accessguard_web.dto.SectorRequestDTO;
import com.tp.accessguard_web.dto.SectorResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SectorService {

    SectorResponseDTO create(SectorRequestDTO dto);

    SectorResponseDTO update(Long id, SectorRequestDTO dto);

    SectorResponseDTO getById(Long id);

    Page<SectorResponseDTO> getAll(Pageable pageable);

    void desactivate(Long id);
}
