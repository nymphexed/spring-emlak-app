package com.proje.odevi.emlak.controller;

import java.security.Principal;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.proje.odevi.emlak.model.EmlakIsletmesi;
import com.proje.odevi.emlak.model.Kullanici;
import com.proje.odevi.emlak.repository.EmlakIsletmesiRepository;
import com.proje.odevi.emlak.repository.KullaniciRepository;
import com.proje.odevi.emlak.security.CustomUserDetails;

@Controller
@RequestMapping("/profil")
public class ProfilController {

    private final KullaniciRepository kullaniciRepository;
    private final EmlakIsletmesiRepository isletmeRepository;

    public ProfilController(KullaniciRepository kullaniciRepository,
            EmlakIsletmesiRepository isletmeRepository) {
        this.kullaniciRepository = kullaniciRepository;
        this.isletmeRepository = isletmeRepository;
    }

    @GetMapping
    public String profil(Model model, Principal principal) {

        // ✔ Giriş yapmayan kullanıcı → login sayfasına
        if (principal == null) {
            return "redirect:/login";
        }

        Kullanici k = kullaniciRepository.findByEmail(principal.getName())
                .orElseThrow();

        model.addAttribute("kullanici", k);

        System.err.println("DEBUG isSeller = " + k.isSeller());
        System.err.println("DEBUG isletme = " + k.getIsletme());
        System.err.println("DEBUG koşul = " + (k.isSeller() && k.getIsletme() == null));

        return "profil";
    }

    @PostMapping("/isletme-ekle")
    public String isletmeEkle(Principal principal,
            @RequestParam String isletmeAdi,
            @RequestParam String yetkili,
            @RequestParam String adres,
            @RequestParam String telefon,
            @RequestParam(required = false) String fax) {

        if (principal == null) {
            return "redirect:/login";
        }

        Kullanici k = kullaniciRepository.findByEmail(principal.getName())
                .orElseThrow();

        // ✔ Yeni işletme oluştur
        EmlakIsletmesi isletme = new EmlakIsletmesi();
        isletme.setIsletmeAdi(isletmeAdi);
        isletme.setYetkili(yetkili);
        isletme.setAdres(adres);
        isletme.setTelefon(telefon);
        isletme.setFax(fax);
        isletme.setKullanici(k);

        isletmeRepository.save(isletme);

        // ✔ Kullanıcı artık satıcıdır
        k.setSeller(true);
        k.setIsletme(isletme);
        kullaniciRepository.save(k);

        return "redirect:/profil";
    }

    @PostMapping("/isletme-sil")
    public String isletmeSil(Principal principal) {

        if (principal == null) {
            return "redirect:/login";
        }

        Kullanici k = kullaniciRepository.findByEmail(principal.getName())
                .orElseThrow();

        if (k.getIsletme() != null) {

            // ✔ İşletmeyi sil
            isletmeRepository.delete(k.getIsletme());

            k.setIsletme(null);
            kullaniciRepository.save(k);
            CustomUserDetails yeniUserDetails = new CustomUserDetails(k);

            Authentication yeniAuth = new UsernamePasswordAuthenticationToken(
                    yeniUserDetails,
                    null,
                    yeniUserDetails.getAuthorities());
            yeniUserDetails.setKullanici(k);

            SecurityContextHolder.getContext().setAuthentication(yeniAuth);
        }

        return "redirect:/profil";
    }

    @PostMapping("/satici-ol")
    public String saticiOl(Authentication authentication, RedirectAttributes redirectAttributes) {

        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        Kullanici kullanici = userDetails.getKullanici();

        // 1) Veritabanında güncelle
        kullanici.setSeller(true);
        kullaniciRepository.save(kullanici);

        // 2) Güncellenmiş kullanıcıyı tekrar yükle
        CustomUserDetails yeniUserDetails = new CustomUserDetails(kullanici);

        // 3) Yeni Authentication oluştur
        UsernamePasswordAuthenticationToken yeniAuth = new UsernamePasswordAuthenticationToken(
                yeniUserDetails,
                authentication.getCredentials(),
                yeniUserDetails.getAuthorities());

        // 4) SecurityContext’e koy
        SecurityContextHolder.getContext().setAuthentication(yeniAuth);

        redirectAttributes.addFlashAttribute("successMessage",
                "Tebrikler! Artık satıcısın. Eğer bir emlak işletmen varsa şimdi ekleyebilirsin.");
        redirectAttributes.addFlashAttribute("confetti", true);
        return "redirect:/profil";
    }

    @PostMapping("/satici-kaldir")
    public String saticiKaldir(Authentication authentication, RedirectAttributes redirectAttributes) {

        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        Kullanici kullanici = userDetails.getKullanici();

        // 1) Eğer işletme varsa sil
        if (kullanici.getIsletme() != null) {
            isletmeRepository.delete(kullanici.getIsletme());
            kullanici.setIsletme(null);
        }

        // 2) Satıcı rolünü kaldır
        kullanici.setSeller(false);
        kullaniciRepository.save(kullanici);

        // 3) SecurityContext güncelle
        CustomUserDetails yeniUserDetails = new CustomUserDetails(kullanici);

        Authentication yeniAuth = new UsernamePasswordAuthenticationToken(
                yeniUserDetails,
                authentication.getCredentials(),
                yeniUserDetails.getAuthorities());

        SecurityContextHolder.getContext().setAuthentication(yeniAuth);

        // 4) Mesaj
        redirectAttributes.addFlashAttribute("successMessage",
                "Satıcı profilin kaldırıldı. Artık normal kullanıcı olarak devam ediyorsun.");

        return "redirect:/profil";
    }

}
