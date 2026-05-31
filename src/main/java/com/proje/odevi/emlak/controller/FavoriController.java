package com.proje.odevi.emlak.controller;

import java.security.Principal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.proje.odevi.emlak.mapper.EmlakMapper;
import com.proje.odevi.emlak.model.Kullanici;
import com.proje.odevi.emlak.repository.KullaniciRepository;
import com.proje.odevi.emlak.service.FavoriService;

@Controller
@RequestMapping("/favori")
public class FavoriController {

    private final FavoriService favoriService;
    private final KullaniciRepository kullaniciRepository;

    public FavoriController(FavoriService favoriService, KullaniciRepository kullaniciRepository) {
        this.favoriService = favoriService;
        this.kullaniciRepository = kullaniciRepository;
    }

    @GetMapping("/liste")
    public String favorilerim(Model model, Principal principal) {
        Kullanici kullanici = kullaniciRepository.findByEmail(principal.getName())
                .orElseThrow(() -> new RuntimeException("Kullanıcı bulunamadı"));

        // Kullanıcının favorileri
        var favoriler = favoriService.kullaniciFavorileri(kullanici.getId());

        // Favorilerdeki ilanları DTO’ya çevir
        var dtoList = favoriler.stream()
                .map(f -> EmlakMapper.toDTO(f.getEmlak()))
                .toList();

        model.addAttribute("liste", dtoList);

        return "favorilerim";
    }

    @PostMapping("/toggle/{ilanId}")
    public String toggleFavori(@PathVariable Long ilanId, Principal principal) {

        Kullanici kullanici = kullaniciRepository.findByEmail(principal.getName())
                .orElseThrow(() -> new RuntimeException("Kullanıcı bulunamadı"));

        Long kullaniciId = kullanici.getId();

        boolean favorideMi = favoriService.isFavori(kullaniciId, ilanId);

        if (favorideMi) {
            favoriService.favoridenCikar(kullaniciId, ilanId);
        } else {
            favoriService.favoriyeEkle(kullaniciId, ilanId);
        }
        System.out.println("FAVORI CONTROLLER ÇALIŞTI");
        return "redirect:/emlak/detay/" + ilanId;
    }
}
