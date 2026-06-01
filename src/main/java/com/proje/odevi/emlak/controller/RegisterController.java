package com.proje.odevi.emlak.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.proje.odevi.emlak.model.EmlakIsletmesi;
import com.proje.odevi.emlak.model.Kullanici;
import com.proje.odevi.emlak.repository.EmlakIsletmesiRepository;
import com.proje.odevi.emlak.repository.KullaniciRepository;

@Controller
public class RegisterController {

    @Autowired
    private KullaniciRepository kullaniciRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private EmlakIsletmesiRepository isletmeRepository;

    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("kullanici", new Kullanici());
        return "register";
    }

    // @PostMapping("/register")
    // public String processRegister(@ModelAttribute Kullanici kullanici) {

    // // Şifre encode
    // kullanici.setSifre(passwordEncoder.encode(kullanici.getSifre()));

    // kullaniciRepository.save(kullanici);

    // return "redirect:/login?registered";
    // }

    @PostMapping("/register")
    public String processRegister(
            @ModelAttribute Kullanici kullanici,
            @RequestParam(value = "seller", required = false) String seller,
            @RequestParam(value = "isletmeEkle", required = false) String isletmeEkle,
            @RequestParam(value = "isletmeAdi", required = false) String isletmeAdi,
            @RequestParam(value = "yetkili", required = false) String yetkili,
            @RequestParam(value = "adres", required = false) String adres,
            @RequestParam(value = "telefon", required = false) String telefon,
            @RequestParam(value = "fax", required = false) String fax) {

        // Şifre encode
        kullanici.setSifre(passwordEncoder.encode(kullanici.getSifre()));

        // Satıcı mı?
        if (seller != null) {
            kullanici.setSeller(true);
        }

        System.out.println("SELLER PARAM = " + seller);

        // Kullanıcıyı kaydet
        kullaniciRepository.save(kullanici);

        // İşletme eklemek istiyorsa
        if (seller != null && isletmeEkle != null) {

            EmlakIsletmesi isletme = new EmlakIsletmesi();
            isletme.setIsletmeAdi(isletmeAdi);
            isletme.setYetkili(yetkili);
            isletme.setAdres(adres);
            isletme.setTelefon(telefon);
            isletme.setFax(fax);
            isletme.setKullanici(kullanici);

            isletmeRepository.save(isletme);
        }

        return "redirect:/login?registered";
    }
}
