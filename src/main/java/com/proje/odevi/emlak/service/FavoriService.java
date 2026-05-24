package com.proje.odevi.emlak.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.proje.odevi.emlak.model.Emlak;
import com.proje.odevi.emlak.model.Kullanici;
import com.proje.odevi.emlak.repository.EmlakRepository;
import com.proje.odevi.emlak.repository.KullaniciRepository;

@Service
public class FavoriService {

    @Autowired
    private KullaniciRepository kullaniciRepository;

    @Autowired
    private EmlakRepository emlakRepository;

    public void favoriyeEkle(Long aliciId, Long ilanId) {

        Kullanici alici = kullaniciRepository.findById(aliciId)
                .orElseThrow(() -> new RuntimeException("Alıcı bulunamadı"));

        Emlak ilan = emlakRepository.findById(ilanId)
                .orElseThrow(() -> new RuntimeException("İlan bulunamadı"));

        
        if (alici.getFavoriler() == null) {
            alici.setFavoriler(new ArrayList<>());
        }

        // Aynı ilanı iki kez eklemeyi engelle
        boolean zatenFavoride = alici.getFavoriler().stream()
                .anyMatch(f -> f.getId().equals(ilanId));

        if (!zatenFavoride) {
            alici.getFavoriler().add(ilan);
            kullaniciRepository.save(alici);
        }
    }
}
