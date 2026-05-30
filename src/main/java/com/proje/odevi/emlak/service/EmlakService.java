package com.proje.odevi.emlak.service;

import com.proje.odevi.emlak.model.Emlak;
import com.proje.odevi.emlak.model.EmlakFoto;
import com.proje.odevi.emlak.model.Kullanici;
import com.proje.odevi.emlak.repository.EmlakFotoRepository;
import com.proje.odevi.emlak.repository.EmlakRepository;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.*;
import java.util.List;
import java.util.UUID;

@Service
public class EmlakService {

    private final EmlakRepository repo; // İlan CRUD işlemleri
    private final EmlakFotoRepository fotoRepo; // Fotoğraf CRUD işlemleri
    private final FotoService fotoService; // Fotoğraf küçültme servisi

    public EmlakService(EmlakRepository repo,
            EmlakFotoRepository fotoRepo,
            FotoService fotoService) {
        this.repo = repo;
        this.fotoRepo = fotoRepo;
        this.fotoService = fotoService;
    }

    // ID ile ilan getir
    public Emlak getir(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Emlak bulunamadı"));
    }

    public List<Emlak> listele() {
        return repo.findAll();
    }

    public List<Emlak> search(String q) {
        return repo.findByBaslikContainingIgnoreCaseOrAciklamaContainingIgnoreCaseOrAdresContainingIgnoreCase(
                q, q, q);
    }

    // Satıcıya ait ilanları getir
    public List<Emlak> findBySaticiId(Long id) {
        return repo.findByKullaniciId(id);
    }

    // İlanı kaydet (insert/update)
    public Emlak kaydet(Emlak emlak) {
        return repo.save(emlak);
    }

    // İlan kaydetme + fotoğraf ekleme işlemi
    public void kaydetIlan(Emlak emlak,
            List<MultipartFile> dosyalar,
            Kullanici satici) throws IOException {

        // İlanın sahibini ata
        emlak.setKullanici(satici);

        // Düzenleme ise eski fotoğrafları koru
        if (emlak.getId() != null) {
            Emlak eski = getir(emlak.getId());
            emlak.setFotolar(eski.getFotolar());
        }

        // Yeni fotoğrafları işle
        for (MultipartFile dosya : dosyalar) {
            if (!dosya.isEmpty()) {
                fotoKaydet(emlak, dosya);
            }
        }

        // Son haliyle ilanı kaydet
        kaydet(emlak);
    }

    // Tek bir fotoğrafı kaydetme işlemi
    public void fotoKaydet(Emlak emlak, MultipartFile dosya) throws IOException {

        // uploads klasörü yoksa oluştur
        Path uploadPath = Paths.get("uploads");
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        // Benzersiz dosya adı oluştur
        String fileName = UUID.randomUUID() + "_" + dosya.getOriginalFilename();

        // Fotoğrafı küçült
        byte[] compressed = fotoService.resize(dosya, 1280);

        // Fotoğrafı diske yaz
        Files.write(uploadPath.resolve(fileName), compressed);

        // DB kaydı oluştur
        EmlakFoto ef = new EmlakFoto();
        ef.setUrl("/uploads/" + fileName);
        ef.setEmlak(emlak);

        // İlanın fotoğraf listesine ekle
        emlak.getFotolar().add(ef);
    }

    // Fotoğraf silme (yetki kontrolü dahil)
    public String fotoSilYetkili(Long fotoId, Kullanici satici) {

        // Fotoğrafı bul
        EmlakFoto foto = fotoRepo.findById(fotoId)
                .orElseThrow();

        // Fotoğraf bu satıcıya ait değilse izin verme
        if (!foto.getEmlak().getKullanici().getId().equals(satici.getId())) {
            return "NO_PERMISSION";
        }

        // Silme işlemini yap
        fotoSil(fotoId);
        return "OK";
    }

    // Fotoğrafı fiziksel + veritabanından sil
    public void fotoSil(Long fotoId) {

        EmlakFoto foto = fotoRepo.findById(fotoId).orElseThrow();

        // İlanın fotoğraf listesinden çıkar
        Emlak emlak = foto.getEmlak();
        emlak.getFotolar().remove(foto);

        // Fiziksel dosyayı sil
        try {
            Path path = Paths.get("uploads/" + foto.getUrl().replace("/uploads/", ""));
            Files.deleteIfExists(path);
        } catch (Exception ignored) {
        }

        // DB kaydını sil
        fotoRepo.delete(foto);
    }

    // İlan silme (yetki kontrolü dahil)
    public void silYetkili(Long id, Kullanici satici) {

        Emlak emlak = getir(id);

        // İlan bu satıcıya ait değilse izin verme
        if (!emlak.getKullanici().getId().equals(satici.getId())) {
            throw new RuntimeException("NO_PERMISSION");
        }

        // İlanı sil
        repo.deleteById(id);
    }

    // EmlakService içine ekle
    public List<Emlak> ara(
            Long ilId,
            Long ilceId,
            Long kategoriId,
            Integer odaSayisi,
            Integer minMetrekare,
            Integer maxMetrekare,
            BigDecimal minFiyat,
            BigDecimal maxFiyat,
            Integer binaYasi,
            Integer kat,
            Integer esyali,
            String isitmaTipi) {

        return repo.ara(
                ilId, ilceId, kategoriId,
                odaSayisi,
                minMetrekare, maxMetrekare,
                minFiyat, maxFiyat,
                binaYasi, kat, esyali,
                isitmaTipi);
    }

}
