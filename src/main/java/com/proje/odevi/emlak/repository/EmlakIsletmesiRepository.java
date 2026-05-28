package com.proje.odevi.emlak.repository;

import com.proje.odevi.emlak.model.EmlakIsletmesi;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmlakIsletmesiRepository extends JpaRepository<EmlakIsletmesi, Long> {

    
    EmlakIsletmesi findByKullaniciId(Long kullaniciId);
}
