package com.tp.accessguard_web.repository;

import com.tp.accessguard_web.model.Permission;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface PermissionRepository extends JpaRepository<Permission, Long> {

    Optional<Permission> findByPersonIdAndSectorId(Long personId, Long sectorId);

    // permisos vigentes para validar acceso en un periodo de tiempo
    Optional<Permission> findFirstByPersonIdAndSectorIdAndValidFromLessThanEqualAndValidToGreaterThanEqual(
            Long personId, Long sectorId, LocalDateTime ts1, LocalDateTime ts2
            );

    // permisos sin vencimiento (valid_to null)
    Optional<Permission> findFirstByPersonIdAndSectorIdAndValidFromLessThanEqualAndValidToIsNull(
            Long personId, Long sectorId, LocalDateTime ts
    );

    List<Permission> findAllByPersonId(Long personId);

    List<Permission> findAllBySectorId(Long sectorId);
}
