package com.tp.accessguard_web.repository;

import com.tp.accessguard_web.model.Sector;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SectorRepository extends JpaRepository<Sector, Long> {

    Optional<Sector> findByCode(String code);

    //Metodo para validar acceso solo a sectores activos
    Optional<Sector> findByCodeAndActiveTrue(String code);
}
