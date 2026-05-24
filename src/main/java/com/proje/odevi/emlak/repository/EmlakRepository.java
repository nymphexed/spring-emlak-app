package com.proje.odevi.emlak.repository;

import com.proje.odevi.emlak.model.Emlak;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface EmlakRepository extends JpaRepository<Emlak, Long> {
     List<Emlak> findByKullaniciId(Long kullaniciId);
     List<Emlak> findByBaslikContainingIgnoreCaseOrAciklamaContainingIgnoreCaseOrAdresContainingIgnoreCase(
            String baslik, String aciklama, String adres);
}