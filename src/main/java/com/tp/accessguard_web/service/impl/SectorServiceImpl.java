package com.tp.accessguard_web.service.impl;

import com.tp.accessguard_web.dto.SectorRequestDTO;
import com.tp.accessguard_web.dto.SectorResponseDTO;
import com.tp.accessguard_web.exception.BadRequestException;
import com.tp.accessguard_web.exception.ResourceNotFoundException;
import com.tp.accessguard_web.model.Sector;
import com.tp.accessguard_web.repository.SectorRepository;
import com.tp.accessguard_web.service.SectorService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class SectorServiceImpl implements SectorService {

    private final SectorRepository sectorRepo;

    public SectorServiceImpl(SectorRepository sectorRepo){
        this.sectorRepo = sectorRepo;
    }

    @Override
    public SectorResponseDTO create(SectorRequestDTO dto) {
        if (sectorRepo.existsByCode(dto.getCode())){
            throw new BadRequestException("Ya existe un sector con este codigo");
        }

        Sector sector = new Sector();
        sector.setName(dto.getName());
        sector.setCode(dto.getCode());
        sector.setActive(true);

        return mapToResponse(sectorRepo.save(sector));
    }

    @Override
    public SectorResponseDTO update(Long id, SectorRequestDTO dto) {
        Sector sector = sectorRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Sector no encontrado"));

        sector.setName(dto.getName());
        sector.setCode(dto.getCode());

        return mapToResponse(sectorRepo.save(sector));
    }

    @Override
    public SectorResponseDTO getById(Long id) {
        Sector sector = sectorRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Sector no encontrado"));

        return mapToResponse(sector);
    }

    @Override
    public Page<SectorResponseDTO> getAll(Pageable pageable) {
        return sectorRepo.findAll(pageable)
                .map(this::mapToResponse);
    }

    @Override
    public void desactivate(Long id) {
        Sector sector = sectorRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Sector no encontrado"));

        sector.setActive(false);
        sectorRepo.save(sector);

    }

    private SectorResponseDTO mapToResponse(Sector sector){
        SectorResponseDTO dto = new SectorResponseDTO();
        dto.setId(sector.getId());
        dto.setName(sector.getName());
        dto.setCode(sector.getCode());
        dto.setActive(sector.isActive());
        return dto;
    }
}
