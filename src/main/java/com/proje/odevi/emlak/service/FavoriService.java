package com.proje.odevi.emlak.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.proje.odevi.emlak.model.Emlak;
import com.proje.odevi.emlak.model.Favori;
import com.proje.odevi.emlak.model.Kullanici;
import com.proje.odevi.emlak.repository.FavoriRepository;


@Service
public class FavoriService {

    @Autowired
    private FavoriRepository favoriRepository;
  

    public boolean isFavori(Long kullaniciId, Long ilanId) {
        return favoriRepository.existsByKullanici_IdAndEmlak_Id(kullaniciId, ilanId);
    }

    public void favoriyeEkle(Long kullaniciId, Long ilanId) {
        Favori favori = new Favori();
        favori.setKullanici(new Kullanici(kullaniciId));
        favori.setEmlak(new Emlak(ilanId));
        favoriRepository.save(favori);
    }

    public void favoridenCikar(Long kullaniciId, Long ilanId) {
        favoriRepository.deleteByKullanici_IdAndEmlak_Id(kullaniciId, ilanId);
    }
}
