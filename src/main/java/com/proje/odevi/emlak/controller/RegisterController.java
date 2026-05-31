package com.proje.odevi.emlak.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.proje.odevi.emlak.model.Kullanici;
import com.proje.odevi.emlak.repository.KullaniciRepository;

@Controller
public class RegisterController {

    @Autowired
    private KullaniciRepository kullaniciRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("kullanici", new Kullanici());
        return "register";
    }

    @PostMapping("/register")
    public String processRegister(@ModelAttribute Kullanici kullanici) {

        // Şifre encode
        kullanici.setSifre(passwordEncoder.encode(kullanici.getSifre()));

        kullaniciRepository.save(kullanici);

        return "redirect:/login?registered";
    }
}
