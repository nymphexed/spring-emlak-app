package com.proje.odevi.emlak.controller;

import com.proje.odevi.emlak.model.Emlak;
import com.proje.odevi.emlak.service.EmlakService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/emlak")
public class EmlakController {

    private final EmlakService service;

    public EmlakController(EmlakService service) {
        this.service = service;
    }

    @GetMapping
    public String liste(Model model) {
        model.addAttribute("liste", service.listele());
        return "liste";
    }

    @GetMapping("/yeni")
    public String yeniForm(Model model) {
        model.addAttribute("emlak", new Emlak());
        return "form";
    }

    @PostMapping("/kaydet")
    public String kaydet(Emlak emlak) {
        service.kaydet(emlak);
        return "redirect:/emlak";
    }

    @GetMapping("/sil/{id}")
    public String sil(@PathVariable Long id) {
        service.sil(id);
        return "redirect:/emlak";
    }
}