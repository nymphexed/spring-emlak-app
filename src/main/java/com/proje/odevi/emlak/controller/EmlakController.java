package com.proje.odevi.emlak.controller;

import com.proje.odevi.emlak.dto.EmlakDTO;
import com.proje.odevi.emlak.mapper.EmlakMapper;
import com.proje.odevi.emlak.model.Emlak;
import com.proje.odevi.emlak.model.Ilce;
import com.proje.odevi.emlak.service.EmlakService;
import com.proje.odevi.emlak.service.FavoriService;
import com.proje.odevi.emlak.service.IlService;
import com.proje.odevi.emlak.service.KategoriService;
import com.proje.odevi.emlak.service.KullaniciService;
import com.proje.odevi.emlak.service.IlceService;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/emlak")
public class EmlakController {

    private final EmlakService service;
    private final IlService ilService;
    private final KategoriService kategoriService;
    private final FavoriService favoriService;
    private final KullaniciService kullaniciService;
    private final IlceService ilceService;

    public EmlakController(EmlakService service,
            IlService ilService,
            KategoriService kategoriService,
            FavoriService favoriService,
            KullaniciService kullaniciService,
            IlceService ilceService) {
        this.service = service;
        this.ilceService = ilceService;
        this.ilService = ilService;
        this.kategoriService = kategoriService;
        this.favoriService = favoriService;
        this.kullaniciService = kullaniciService;
    }

    @GetMapping("/")
    public String anasayfa() {
        return "index";
    }

    @GetMapping
    public String liste(Model model,
            @RequestParam(required = false) String q,
            @RequestParam(required = false) String filtre, Principal principal) {

        List<EmlakDTO> liste;

        if (q != null && !q.isEmpty()) {
            liste = service.search(q)
                    .stream()
                    .map(EmlakMapper::toDTO)
                    .toList();
        } else {
            liste = service.listele()
                    .stream()
                    .map(EmlakMapper::toDTO)
                    .toList();
        }

        liste = filtreleDTO(principal, liste);

        model.addAttribute("liste", liste);
        model.addAttribute("iller", ilService.listele());
        model.addAttribute("kategoriler", kategoriService.findAll());
        model.addAttribute("filtreAc", "ac".equals(filtre));

        return "liste";
    }

    @GetMapping("/detay/{id}")
    public String detay(@PathVariable Long id, Model model, Principal principal) {

        Emlak emlak = service.getir(id);

        EmlakDTO dto = EmlakMapper.toDTO(emlak);
        model.addAttribute("emlak", dto);

        boolean favorideMi = false;

        if (principal != null) {
            Long kullaniciId = kullaniciService.findByEmail(principal.getName()).getId();
            favorideMi = favoriService.isFavori(kullaniciId, id);
        }

        model.addAttribute("favorideMi", favorideMi);

        return "detay";
    }

    @GetMapping("/ekle")
    public String ekleForm(Model model) {

        model.addAttribute("emlak", new Emlak());
        model.addAttribute("iller", ilService.listele());
        model.addAttribute("kategoriler", kategoriService.findAll());

        return "emlak-ekle";
    }

    @GetMapping("/arama-sonuc-json")
    @ResponseBody
    public List<EmlakDTO> aramaSonucJson(
            @RequestParam(required = false) Long ilId,
            @RequestParam(required = false) Long ilceId,
            @RequestParam(required = false) Long kategoriId,
            @RequestParam(required = false) Integer odaSayisi,
            @RequestParam(required = false) Integer minMetrekare,
            @RequestParam(required = false) Integer maxMetrekare,
            @RequestParam(required = false) BigDecimal minFiyat,
            @RequestParam(required = false) BigDecimal maxFiyat,
            @RequestParam(required = false) Integer binaYasi,
            @RequestParam(required = false) Integer kat,
            @RequestParam(required = false) Integer esyali,
            @RequestParam(required = false) String isitmaTipi) {

        return service.ara(
                ilId, ilceId, kategoriId, odaSayisi,
                minMetrekare, maxMetrekare,
                minFiyat, maxFiyat,
                binaYasi, kat, esyali, isitmaTipi)
                .stream()
                .map(EmlakMapper::toDTO)
                .toList();
    }

    @GetMapping("/arama-sonuc")
    public String aramaSonuc(
            @RequestParam(required = false) Long ilId,
            @RequestParam(required = false) Long ilceId,
            @RequestParam(required = false) Long kategoriId,
            @RequestParam(required = false) Integer odaSayisi,
            @RequestParam(required = false) Integer minMetrekare,
            @RequestParam(required = false) Integer maxMetrekare,
            @RequestParam(required = false) BigDecimal minFiyat,
            @RequestParam(required = false) BigDecimal maxFiyat,
            @RequestParam(required = false) Integer binaYasi,
            @RequestParam(required = false) Integer kat,
            @RequestParam(required = false) Integer esyali,
            @RequestParam(required = false) String isitmaTipi,
            Model model, Principal principal) {

        List<Emlak> sonuc = service.ara(
                ilId, ilceId, kategoriId, odaSayisi,
                minMetrekare, maxMetrekare,
                minFiyat, maxFiyat,
                binaYasi, kat, esyali, isitmaTipi);

        sonuc = filtreleEntity(principal, sonuc);

        model.addAttribute("liste", sonuc);
        model.addAttribute("iller", ilService.findAll());
        model.addAttribute("ilceler", ilceService.findAll());
        model.addAttribute("kategoriler", kategoriService.findAll());

        return "tum-ilanlar";
    }

    @GetMapping("/ilceler")
    @ResponseBody
    public List<Ilce> ilceleriGetir(@RequestParam Long ilId) {
        return ilceService.findByIlId(ilId);
    }

    @GetMapping("/yazdir")
    public String yazdirSonuc(
            @RequestParam(required = false) Long ilId,
            @RequestParam(required = false) Long ilceId,
            @RequestParam(required = false) Long kategoriId,
            @RequestParam(required = false) Integer odaSayisi,
            @RequestParam(required = false) Integer minMetrekare,
            @RequestParam(required = false) Integer maxMetrekare,
            @RequestParam(required = false) BigDecimal minFiyat,
            @RequestParam(required = false) BigDecimal maxFiyat,
            @RequestParam(required = false) Integer binaYasi,
            @RequestParam(required = false) Integer kat,
            @RequestParam(required = false) Integer esyali,
            @RequestParam(required = false) String isitmaTipi,
            Model model, Principal principal) {

        List<Emlak> liste = service.ara(
                ilId, ilceId, kategoriId,
                odaSayisi,
                minMetrekare, maxMetrekare,
                minFiyat, maxFiyat,
                binaYasi, kat, esyali,
                isitmaTipi);

        liste = filtreleEntity(principal, liste);

        model.addAttribute("liste", liste);
        return "yazdir";
    }

    // EmlakDTO listesi için filtre
    private List<EmlakDTO> filtreleDTO(Principal principal, List<EmlakDTO> liste) {
        if (principal == null)
            return liste;

        var k = kullaniciService.findByEmail(principal.getName());

        if (k.isSeller())
            return liste;

        return liste.stream()
                .filter(e -> !e.getKullaniciId().equals(k.getId()))
                .toList();
    }

    // Emlak entity listesi için filtre
    private List<Emlak> filtreleEntity(Principal principal, List<Emlak> liste) {
        if (principal == null)
            return liste;

        var k = kullaniciService.findByEmail(principal.getName());

        if (k.isSeller())
            return liste;

        return liste.stream()
                .filter(e -> !e.getKullanici().getId().equals(k.getId()))
                .toList();
    }

}
