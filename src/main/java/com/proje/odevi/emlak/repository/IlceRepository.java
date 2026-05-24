package com.proje.odevi.emlak.repository;

import com.proje.odevi.emlak.model.Ilce;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface IlceRepository extends JpaRepository<Ilce, Long> {
    List<Ilce> findByIlId(Long ilId);
}