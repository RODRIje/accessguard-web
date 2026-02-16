package com.tp.accessguard_web.service;

import com.tp.accessguard_web.dto.PersonRequestDTO;
import com.tp.accessguard_web.dto.PersonResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PersonService {

    PersonResponseDTO create(PersonRequestDTO dto);

    PersonResponseDTO update(Long id, PersonRequestDTO dto);

    PersonResponseDTO getById(Long id);

    Page<PersonResponseDTO> getAll(Pageable pageable);

    void desactivate(Long id);
}
