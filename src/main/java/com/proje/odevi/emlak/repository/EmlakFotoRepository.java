package com.proje.odevi.emlak.repository;

import com.proje.odevi.emlak.model.EmlakFoto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmlakFotoRepository extends JpaRepository<EmlakFoto, Long> {

}
