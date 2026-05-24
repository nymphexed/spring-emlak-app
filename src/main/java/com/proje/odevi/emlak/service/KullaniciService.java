package com.proje.odevi.emlak.service;

import com.proje.odevi.emlak.model.Kullanici;
import com.proje.odevi.emlak.repository.KullaniciRepository;
import org.springframework.stereotype.Service;
import java.util.List;


@Service
public class KullaniciService {

    private final KullaniciRepository kullaniciRepository;

    public KullaniciService(KullaniciRepository kullaniciRepository) {
        this.kullaniciRepository = kullaniciRepository;
    }

    public List<Kullanici> findAll() {
        return kullaniciRepository.findAll();
    }

    public Kullanici save(Kullanici kullanici) {
        return kullaniciRepository.save(kullanici);
    }

    public Kullanici findById(Long id) {
        return kullaniciRepository.findById(id).orElse(null);
    }

    public void delete(Long id) {
        kullaniciRepository.deleteById(id);
    }
}