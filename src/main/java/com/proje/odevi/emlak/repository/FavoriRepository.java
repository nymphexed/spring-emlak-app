package com.proje.odevi.emlak.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.proje.odevi.emlak.model.Favori;

import jakarta.transaction.Transactional;

@Repository
public interface FavoriRepository extends JpaRepository<Favori, Long> {
    boolean existsByKullanici_IdAndEmlak_Id(Long kullaniciId, Long emlakId);

    @Transactional
    @Modifying
    @Query("delete from Favori f where f.kullanici.id = :kullaniciId and f.emlak.id = :ilanId")
    void deleteByKullanici_IdAndEmlak_Id(@Param("kullaniciId") Long kullaniciId, @Param("ilanId") Long emlakId);

    List<Favori> findByKullanici_Id(Long kullaniciId);

}