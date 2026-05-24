package com.proje.odevi.emlak.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.proje.odevi.emlak.model.Kullanici;
import com.proje.odevi.emlak.repository.KullaniciRepository;

@Controller
public class HomeController {

    @Autowired
    private KullaniciRepository repository;

    @GetMapping("/")
    public String home(Model model, Principal principal) {
        if (principal != null) {
            Kullanici k = repository.findByEmail(principal.getName()).get();
            model.addAttribute("adSoyad", k.getAd() + " " + k.getSoyad());
        }
        return "index";
    }
}