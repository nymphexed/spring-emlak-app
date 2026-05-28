package com.proje.odevi.emlak.repository;
import com.proje.odevi.emlak.model.Kullanici;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface KullaniciRepository extends JpaRepository<Kullanici, Long> {

    Optional<Kullanici> findByEmail(String email);

}