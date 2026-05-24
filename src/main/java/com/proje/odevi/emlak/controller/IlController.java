package com.proje.odevi.emlak.controller;

import com.proje.odevi.emlak.model.Il;
import com.proje.odevi.emlak.service.IlService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/il")
public class IlController {

    private final IlService ilService;

    public IlController(IlService ilService) {
        this.ilService = ilService;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("iller", ilService.findAll());
        return "il/list"; // il/list.html
    }

    @GetMapping("/ekle")
    public String ekleForm(Model model) {
        model.addAttribute("il", new Il());
        return "il/ekle"; // il/ekle.html
    }

    @PostMapping("/kaydet")
    public String kaydet(@ModelAttribute Il il) {
        ilService.save(il);
        return "redirect:/il";
    }
}
