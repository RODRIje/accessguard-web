package com.tp.accessguard_web.repository;

import com.tp.accessguard_web.model.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

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

    List<Permission> findByPersonId(Long personId);

    List<Permission> findBySectorId(Long sectorId);

    @Query("""
            SELECT p FROM Permission p
            WHERE p.person.id = :personId
            AND p.sector.id = :sectorId
            AND p.validFrom <= :now
            AND (p.validTo IS NULL OR p.validTo >= :now)
            """)
    Optional<Permission> findValidPermission(Long personId, Long sectorId, LocalDateTime now);
}
