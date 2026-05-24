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

    public SaticiController(EmlakService emlakService, IlService ilService, KullaniciRepository kullaniciRepository, KategoriService kategoriService) {
        this.emlakService = emlakService;
        this.ilService = ilService;
        this.kullaniciRepository = kullaniciRepository;
        this.kategoriService = kategoriService;
    }

    // SATICININ KENDİ İLANLARI
    @GetMapping("/ilanlarim")
    public String ilanlarim(Model model, Principal principal) {

        String email = principal.getName();
        Kullanici satici = kullaniciRepository.findByEmail(email).orElseThrow();

        List<Emlak> ilanlar = emlakService.findBySaticiId(satici.getId());

        model.addAttribute("ilanlar", ilanlar);
        return "ilanlarim"; // templates/ilanlarim.html
    }

    // YENİ İLAN FORMU
    @GetMapping("/yeni")
    public String yeniForm(Model model) {
        model.addAttribute("emlak", new Emlak());
        model.addAttribute("iller", ilService.findAll());
        model.addAttribute("kategoriler", kategoriService.findAll());
        return "form"; // templates/form.html
    }

    // İLAN KAYDETME
    @PostMapping("/kaydet")
    public String kaydet(Emlak emlak, Principal principal) {

        String email = principal.getName();
        Kullanici satici = kullaniciRepository.findByEmail(email).orElseThrow();

        // ilanı giriş yapan satıcıya bağla
        emlak.setKullanici(satici);

        emlakService.kaydet(emlak);
        return "redirect:/satici/ilanlarim";
    }

    // DÜZENLEME FORMU (sadece kendi ilanı)
    @GetMapping("/duzenle/{id}")
    public String duzenleForm(@PathVariable Long id, Principal principal, Model model) {

        Emlak emlak = emlakService.getir(id);
        String email = principal.getName();

        // Başkasının ilanıysa → ilanlarım sayfasına dön
        if (!emlak.getKullanici().getEmail().equals(email)) {
            return "redirect:/satici/ilanlarim";
        }

        model.addAttribute("emlak", emlak);
        model.addAttribute("iller", ilService.findAll());
        return "form";
    }

    // SİLME (sadece kendi ilanı)
    @GetMapping("/sil/{id}")
    public String sil(@PathVariable Long id, Principal principal) {

        Emlak emlak = emlakService.getir(id);
        String email = principal.getName();

        if (!emlak.getKullanici().getEmail().equals(email)) {
            return "redirect:/satici/ilanlarim";
        }

        emlakService.sil(id);
        return "redirect:/satici/ilanlarim";
    }
}
