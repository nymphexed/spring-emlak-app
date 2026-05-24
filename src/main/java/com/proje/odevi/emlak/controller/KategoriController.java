package com.proje.odevi.emlak.controller;

import com.proje.odevi.emlak.model.Kategori;
import com.proje.odevi.emlak.service.KategoriService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/kategori")
public class KategoriController {

    private final KategoriService kategoriService;

    public KategoriController(KategoriService kategoriService) {
        this.kategoriService = kategoriService;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("kategoriler", kategoriService.findAll());
        return "kategori/list";
    }

    @GetMapping("/ekle")
    public String ekleForm(Model model) {
        model.addAttribute("kategori", new Kategori());
        return "kategori/ekle";
    }

    @PostMapping("/kaydet")
    public String kaydet(@ModelAttribute Kategori kategori) {
        kategoriService.save(kategori);
        return "redirect:/kategori";
    }
}
