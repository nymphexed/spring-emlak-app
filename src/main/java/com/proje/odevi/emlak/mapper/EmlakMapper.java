package com.proje.odevi.emlak.mapper;

import java.util.stream.Collectors;

import com.proje.odevi.emlak.dto.EmlakDTO;
import com.proje.odevi.emlak.model.Emlak;

public class EmlakMapper {

    public static EmlakDTO toDTO(Emlak e) {

        EmlakDTO dto = new EmlakDTO();

        dto.id = e.getId();
        dto.baslik = e.getBaslik();
        dto.adres = e.getAdres();
        dto.binaYasi = e.getBinaYasi();
        dto.esyali = e.getEsyali();
        dto.fiyat = e.getFiyat();
        dto.isitmaTipi = e.getIsitmaTipi();
        dto.kat = e.getKat();
        dto.metrekare = e.getMetrekare();
        dto.odaSayisi = e.getOdaSayisi();
        dto.aciklama = e.getAciklama();

        dto.il = e.getIl() != null ? e.getIl().getAd() : null;
        dto.ilce = e.getIlce() != null ? e.getIlce().getAd() : null;

        dto.fotolar = e.getFotolar()
                .stream()
                .map(f -> f.getUrl()) // EmlakFoto içinde getUrl() olduğunu varsayıyorum
                .collect(Collectors.toList());

        return dto;
    }
}
