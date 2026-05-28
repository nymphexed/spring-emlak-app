package com.proje.odevi.emlak.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.proje.odevi.emlak.model.Kullanici;
import com.proje.odevi.emlak.repository.KullaniciRepository;
import com.proje.odevi.emlak.service.FavoriService;

@Controller
@RequestMapping("/favori")
public class FavoriController {

    @Autowired
    private FavoriService favoriService;

    @Autowired
    private KullaniciRepository kullaniciRepository;

    @PostMapping("/ekle/{ilanId}")
    public String favoriEkle(@PathVariable Long ilanId, Principal principal) {

        Kullanici kullanici = kullaniciRepository.findByEmail(principal.getName())
                .orElseThrow(() -> new RuntimeException("Kullanıcı bulunamadı"));

        // ✔ Yeni sistem: Her kullanıcı alıcıdır → favori ekleyebilir
        // Satıcı olsa bile alıcıdır, o yüzden engel yok

        favoriService.favoriyeEkle(kullanici.getId(), ilanId);

        return "redirect:/ilan/" + ilanId;
    }
}
