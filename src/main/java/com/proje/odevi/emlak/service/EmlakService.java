package com.proje.odevi.emlak.service;

import com.proje.odevi.emlak.model.Emlak;
import com.proje.odevi.emlak.repository.EmlakRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmlakService {

    private final EmlakRepository repo;

    public EmlakService(EmlakRepository repo) {
        this.repo = repo;
    }

    public List<Emlak> listele() {
        return repo.findAll();
    }

    public Emlak kaydet(Emlak emlak) {
        return repo.save(emlak);
    }

    public void sil(Long id) {
        repo.deleteById(id);
    }

    public Emlak getir(Long id) {
        return repo.findById(id).orElse(null);
    }
}