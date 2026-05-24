package com.proje.odevi.emlak.controller;

import com.proje.odevi.emlak.model.Emlak;
import com.proje.odevi.emlak.service.EmlakService;
import com.proje.odevi.emlak.service.IlService;
import com.proje.odevi.emlak.service.KategoriService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/emlak")
public class EmlakController {

    private final EmlakService service;
    private final IlService ilService;
    private final KategoriService kategoriService;

    public EmlakController(EmlakService service, IlService ilService, KategoriService kategoriService) {
        this.service = service;
        this.ilService = ilService;
        this.kategoriService = kategoriService;
    }

    @GetMapping("/")
    public String anasayfa() {
        return "index";
    }

    @GetMapping
    public String liste(Model model, @RequestParam(required = false) String q) {
        if (q != null && !q.isEmpty()) {
            model.addAttribute("liste", service.search(q));
        } else {
            model.addAttribute("liste", service.listele());
        }
        return "liste";
    }

    @GetMapping("/detay/{id}")
    public String detay(@PathVariable Long id, Model model) {
        model.addAttribute("emlak", service.getir(id));
        return "detay";
    }

    // ⭐ EMLAK EKLEME FORMU
    @GetMapping("/ekle")
    public String ekleForm(Model model) {

        model.addAttribute("emlak", new Emlak());

        // İl dropdown
        model.addAttribute("iller", ilService.listele());

        // ⭐ Kategori dropdown
        model.addAttribute("kategoriler", kategoriService.findAll());

        return "emlak-ekle"; 
    }
}
