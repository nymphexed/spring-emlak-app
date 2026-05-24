package com.proje.odevi.emlak.service;

import com.proje.odevi.emlak.model.Kategori;
import com.proje.odevi.emlak.repository.KategoriRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class KategoriService {

    private final KategoriRepository kategoriRepository;

    public KategoriService(KategoriRepository kategoriRepository) {
        this.kategoriRepository = kategoriRepository;
    }

    public List<Kategori> findAll() {
        return kategoriRepository.findAll();
    }

    public Kategori save(Kategori kategori) {
        return kategoriRepository.save(kategori);
    }

    public Kategori findById(Long id) {
        return kategoriRepository.findById(id).orElse(null);
    }

    public void delete(Long id) {
        kategoriRepository.deleteById(id);
    }
}