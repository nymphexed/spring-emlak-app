package com.proje.odevi.emlak.repository;

import com.proje.odevi.emlak.model.Emlak;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.util.List;

public interface EmlakRepository extends JpaRepository<Emlak, Long> {
    List<Emlak> findByKullaniciId(Long kullaniciId);

    List<Emlak> findByBaslikContainingIgnoreCaseOrAciklamaContainingIgnoreCaseOrAdresContainingIgnoreCase(
            String baslik, String aciklama, String adres);

    @Query("""
              SELECT e FROM Emlak e
              WHERE (:ilId IS NULL OR e.il.id = :ilId)
                AND (:ilceId IS NULL OR e.ilce.id = :ilceId)
                AND (:kategoriId IS NULL OR e.kategori.id = :kategoriId)
                AND (:odaSayisi IS NULL OR e.odaSayisi = :odaSayisi)
                AND (:minMetrekare IS NULL OR e.metrekare >= :minMetrekare)
                AND (:maxMetrekare IS NULL OR e.metrekare <= :maxMetrekare)
                AND (:minFiyat IS NULL OR e.fiyat >= :minFiyat)
                AND (:maxFiyat IS NULL OR e.fiyat <= :maxFiyat)
                AND (:binaYasi IS NULL OR e.binaYasi = :binaYasi)
                AND (:kat IS NULL OR e.kat = :kat)
                AND (:esyali IS NULL OR e.esyali = :esyali)
                AND (:isitmaTipi IS NULL OR LOWER(e.isitmaTipi) LIKE LOWER(CONCAT('%', :isitmaTipi, '%')))
            """)
    List<Emlak> ara(
            Long ilId,
            Long ilceId,
            Long kategoriId,
            Integer odaSayisi,
            Integer minMetrekare,
            Integer maxMetrekare,
            BigDecimal minFiyat,
            BigDecimal maxFiyat,
            Integer binaYasi,
            Integer kat,
            Integer esyali,
            String isitmaTipi);
}