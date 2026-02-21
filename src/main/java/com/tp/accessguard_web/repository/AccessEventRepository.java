package com.tp.accessguard_web.repository;

import com.tp.accessguard_web.model.AccessEvent;
import com.tp.accessguard_web.model.enums.EventResult;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;


import org.springframework.data.domain.Pageable;
import java.time.LocalDateTime;
import java.util.List;

public interface AccessEventRepository extends JpaRepository<AccessEvent, Long> {

    List<AccessEvent> findAllBySector_IdAndTsBetweenOrderByTsDesc(Long sectorId, LocalDateTime from, LocalDateTime to);

    List<AccessEvent> findAllByPerson_IdOrderByTsDesc(Long personId);

    Page<AccessEvent> findByPersonId(Long personId, Pageable pageable);

    Page<AccessEvent> findBySectorId(Long sectorId, Pageable pageable);

    Page<AccessEvent> findByResult(EventResult result, Pageable pageable);
}
