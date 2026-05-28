package com.proje.odevi.emlak.config;

import com.proje.odevi.emlak.model.Kullanici;
import com.proje.odevi.emlak.repository.KullaniciRepository;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.ui.Model;

import java.security.Principal;

@ControllerAdvice
@Component
public class GlobalModelAttributes {

    private final KullaniciRepository kullaniciRepository;

    public GlobalModelAttributes(KullaniciRepository kullaniciRepository) {
        this.kullaniciRepository = kullaniciRepository;
    }

    @ModelAttribute
    public void addUserInfo(Model model, Principal principal) {
        if (principal != null) {
            Kullanici k = kullaniciRepository.findByEmail(principal.getName()).orElse(null);
            if (k != null) {
                model.addAttribute("adSoyad", k.getAd() + " " + k.getSoyad());
            }
        }
    }
}
