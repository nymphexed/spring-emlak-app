package com.proje.odevi.emlak.controller;

import com.proje.odevi.emlak.model.Kullanici;
import com.proje.odevi.emlak.service.KullaniciService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/kullanici")
public class KullaniciController {

    private final KullaniciService kullaniciService;

    public KullaniciController(KullaniciService kullaniciService) {
        this.kullaniciService = kullaniciService;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("kullanicilar", kullaniciService.findAll());
        return "kullanici/list";
    }

    @GetMapping("/ekle")
    public String ekleForm(Model model) {
        model.addAttribute("kullanici", new Kullanici());
        return "kullanici/ekle";
    }

    @PostMapping("/kaydet")
    public String kaydet(@ModelAttribute Kullanici kullanici) {
        kullaniciService.save(kullanici);
        return "redirect:/kullanici";
    }
}