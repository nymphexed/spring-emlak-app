package com.proje.odevi.emlak.controller;

import com.proje.odevi.emlak.model.Emlak;
import com.proje.odevi.emlak.model.Kullanici;
import com.proje.odevi.emlak.service.EmlakService;
import com.proje.odevi.emlak.service.IlService;
import com.proje.odevi.emlak.service.KategoriService;
import com.proje.odevi.emlak.repository.KullaniciRepository;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/satici")
public class SaticiController {

    private final EmlakService emlakService;
    private final IlService ilService;
    private final KullaniciRepository kullaniciRepository;
    private final KategoriService kategoriService;

    public SaticiController(EmlakService emlakService,
            IlService ilService,
            KullaniciRepository kullaniciRepository,
            KategoriService kategoriService) {
        this.emlakService = emlakService;
        this.ilService = ilService;
        this.kullaniciRepository = kullaniciRepository;
        this.kategoriService = kategoriService;
    }

    @GetMapping("/ilanlarim")
    public String ilanlarim(Model model, Principal principal) {

        Kullanici satici = kullaniciRepository.findByEmail(principal.getName())
                .orElseThrow();

        if (!satici.isSeller())
            return "redirect:/access-denied";

        model.addAttribute("ilanlar", emlakService.findBySaticiId(satici.getId()));
        return "ilanlarim";
    }

    @GetMapping("/yeni")
    public String yeniForm(Model model, Principal principal) {

        Kullanici satici = kullaniciRepository.findByEmail(principal.getName())
                .orElseThrow();

        if (!satici.isSeller())
            return "redirect:/access-denied";

        model.addAttribute("emlak", new Emlak());
        model.addAttribute("iller", ilService.findAll());
        model.addAttribute("kategoriler", kategoriService.findAll());

        return "form";
    }

    @PostMapping("/kaydet")
    public String kaydet(Emlak emlak,
            @RequestParam("dosyalar") List<MultipartFile> dosyalar,
            Principal principal) throws IOException {

        Kullanici satici = kullaniciRepository.findByEmail(principal.getName())
                .orElseThrow();

        if (!satici.isSeller())
            return "redirect:/access-denied";

        emlakService.kaydetIlan(emlak, dosyalar, satici);

        return "redirect:/satici/ilanlarim";
    }

    @GetMapping("/duzenle/{id}")
    public String duzenleForm(@PathVariable Long id, Principal principal, Model model) {

        Kullanici satici = kullaniciRepository.findByEmail(principal.getName())
                .orElseThrow();

        if (!satici.isSeller())
            return "redirect:/access-denied";

        Emlak emlak = emlakService.getir(id);

        if (!emlak.getKullanici().getId().equals(satici.getId()))
            return "redirect:/satici/ilanlarim";

        model.addAttribute("emlak", emlak);
        model.addAttribute("iller", ilService.findAll());
        model.addAttribute("ilçeler", ilService.findById(emlak.getIl().getId()).getIlceler());
        model.addAttribute("kategoriler", kategoriService.findAll());

        return "form";
    }

    @DeleteMapping("/foto-sil/{fotoId}")
    @ResponseBody
    public String fotoSil(@PathVariable Long fotoId, Principal principal) {

        Kullanici satici = kullaniciRepository.findByEmail(principal.getName())
                .orElseThrow();

        return emlakService.fotoSilYetkili(fotoId, satici);
    }

    @GetMapping("/sil/{id}")
    public String sil(@PathVariable Long id, Principal principal) {

        Kullanici satici = kullaniciRepository.findByEmail(principal.getName())
                .orElseThrow();

        emlakService.silYetkili(id, satici);

        return "redirect:/satici/ilanlarim";
    }
}
