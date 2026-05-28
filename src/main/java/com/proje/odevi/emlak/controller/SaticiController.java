package com.proje.odevi.emlak.controller;

import com.proje.odevi.emlak.model.Emlak;
import com.proje.odevi.emlak.model.Kullanici;
import com.proje.odevi.emlak.repository.KullaniciRepository;
import com.proje.odevi.emlak.service.EmlakService;
import com.proje.odevi.emlak.service.IlService;
import com.proje.odevi.emlak.service.KategoriService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/satici")
public class SaticiController {

    private final EmlakService emlakService;
    private final IlService ilService;
    private final KullaniciRepository kullaniciRepository;
    private final KategoriService kategoriService;

    public SaticiController(EmlakService emlakService, IlService ilService, 
                            KullaniciRepository kullaniciRepository, 
                            KategoriService kategoriService) {
        this.emlakService = emlakService;
        this.ilService = ilService;
        this.kullaniciRepository = kullaniciRepository;
        this.kategoriService = kategoriService;
    }

    // SATICININ KENDİ İLANLARI
    @GetMapping("/ilanlarim")
    public String ilanlarim(Model model, Principal principal) {

        Kullanici satici = kullaniciRepository.findByEmail(principal.getName())
                .orElseThrow();

        // ✔ Yeni sistem: sadece isSeller kontrolü
        if (!satici.isSeller()) {
            return "redirect:/access-denied";
        }

        List<Emlak> ilanlar = emlakService.findBySaticiId(satici.getId());
        model.addAttribute("ilanlar", ilanlar);

        return "ilanlarim";
    }

    // YENİ İLAN FORMU
    @GetMapping("/yeni")
    public String yeniForm(Model model, Principal principal) {

        Kullanici satici = kullaniciRepository.findByEmail(principal.getName())
                .orElseThrow();

        if (!satici.isSeller()) {
            return "redirect:/access-denied";
        }

        model.addAttribute("emlak", new Emlak());
        model.addAttribute("iller", ilService.findAll());
        model.addAttribute("kategoriler", kategoriService.findAll());

        return "form";
    }

    // İLAN KAYDETME
    @PostMapping("/kaydet")
    public String kaydet(Emlak emlak, Principal principal) {

        Kullanici satici = kullaniciRepository.findByEmail(principal.getName())
                .orElseThrow();

        if (!satici.isSeller()) {
            return "redirect:/access-denied";
        }

        emlak.setKullanici(satici);
        emlakService.kaydet(emlak);

        return "redirect:/satici/ilanlarim";
    }

    // DÜZENLEME FORMU (sadece kendi ilanı)
    @GetMapping("/duzenle/{id}")
    public String duzenleForm(@PathVariable Long id, Principal principal, Model model) {

        Kullanici satici = kullaniciRepository.findByEmail(principal.getName())
                .orElseThrow();

        if (!satici.isSeller()) {
            return "redirect:/access-denied";
        }

        Emlak emlak = emlakService.getir(id);

        if (!emlak.getKullanici().getId().equals(satici.getId())) {
            return "redirect:/satici/ilanlarim";
        }

        model.addAttribute("emlak", emlak);
        model.addAttribute("iller", ilService.findAll());
        model.addAttribute("ilçeler", ilService.findById(emlak.getIl().getId()).getIlceler());
        model.addAttribute("kategoriler", kategoriService.findAll());

        return "form";
    }

    // SİLME (sadece kendi ilanı)
    @GetMapping("/sil/{id}")
    public String sil(@PathVariable Long id, Principal principal) {

        Kullanici satici = kullaniciRepository.findByEmail(principal.getName())
                .orElseThrow();

        if (!satici.isSeller()) {
            return "redirect:/access-denied";
        }

        Emlak emlak = emlakService.getir(id);

        if (!emlak.getKullanici().getId().equals(satici.getId())) {
            return "redirect:/satici/ilanlarim";
        }

        emlakService.sil(id);
        return "redirect:/satici/ilanlarim";
    }
}
