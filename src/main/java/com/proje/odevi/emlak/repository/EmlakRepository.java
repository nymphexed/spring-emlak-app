package com.proje.odevi.emlak.repository;

import com.proje.odevi.emlak.model.Emlak;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmlakRepository extends JpaRepository<Emlak, Long> {
}