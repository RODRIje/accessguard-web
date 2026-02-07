package com.tp.accessguard_web.repository;

import com.tp.accessguard_web.model.AccessEvent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface AccessEventRepository extends JpaRepository<AccessEvent, Long> {

    List<AccessEvent> findAllBySectorIdAndTsBetweenOrderByTsDesc(Long sectorId, LocalDateTime from, LocalDateTime to);

    List<AccessEvent> findAllByPersonIdOrderByTsDesc(Long personId);
}
