package com.proje.odevi.emlak.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;

@Entity
@Table(name = "EMLAK")
public class Emlak {

    @Id
    @SequenceGenerator(name = "emlak_seq", sequenceName = "EMLAK_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "emlak_seq")
    @Column(name = "ID")
    private Long id;

    @ManyToMany(mappedBy = "favoriler")
    @JsonIgnore
    private List<Kullanici> favoriAlanlar;

    @OneToMany(mappedBy = "emlak", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<EmlakFoto> fotolar = new ArrayList<>();

    @Column(name = "ADRES")
    private String adres;

    @Column(name = "BASLIK")
    private String baslik;

    @Column(name = "BINA_YASI")
    private Integer binaYasi;

    @Column(name = "ESYALI")
    private Integer esyali;

    @Column(name = "FIYAT")
    private BigDecimal fiyat;

    @Column(name = "ISITMA_TIPI")
    private String isitmaTipi;

    @Column(name = "KAT")
    private Integer kat;

    @Column(name = "METREKARE")
    private Integer metrekare;

    @Column(name = "ODA_SAYISI")
    private Integer odaSayisi;

    @Column(name = "ACIKLAMA")
    private String aciklama;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "IL_ID")
    private Il il;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "ILCE_ID")
    private Ilce ilce;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "KATEGORI_ID")
    private Kategori kategori;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "KULLANICI_ID")
    private Kullanici kullanici;

    // -------------------- GETTER – SETTER --------------------

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAdres() {
        return adres;
    }

    public void setAdres(String adres) {
        this.adres = adres;
    }

    public String getBaslik() {
        return baslik;
    }

    public void setBaslik(String baslik) {
        this.baslik = baslik;
    }

    public Integer getBinaYasi() {
        return binaYasi;
    }

    public void setBinaYasi(Integer binaYasi) {
        this.binaYasi = binaYasi;
    }

    public Integer getEsyali() {
        return esyali;
    }

    public void setEsyali(Integer esyali) {
        this.esyali = esyali;
    }

    public BigDecimal getFiyat() {
        return fiyat;
    }

    public void setFiyat(BigDecimal fiyat2) {
        this.fiyat = fiyat2;
    }

    public String getIsitmaTipi() {
        return isitmaTipi;
    }

    public void setIsitmaTipi(String isitmaTipi) {
        this.isitmaTipi = isitmaTipi;
    }

    public Integer getKat() {
        return kat;
    }

    public void setKat(Integer kat) {
        this.kat = kat;
    }

    public Integer getMetrekare() {
        return metrekare;
    }

    public void setMetrekare(Integer metrekare) {
        this.metrekare = metrekare;
    }

    public Integer getOdaSayisi() {
        return odaSayisi;
    }

    public void setOdaSayisi(Integer odaSayisi) {
        this.odaSayisi = odaSayisi;
    }

    public String getAciklama() {
        return aciklama;
    }

    public void setAciklama(String aciklama) {
        this.aciklama = aciklama;
    }

    public Il getIl() {
        return il;
    }

    public void setIl(Il il) {
        this.il = il;
    }

    public Ilce getIlce() {
        return ilce;
    }

    public void setIlce(Ilce ilce) {
        this.ilce = ilce;
    }

    public Kategori getKategori() {
        return kategori;
    }

    public void setKategori(Kategori kategori) {
        this.kategori = kategori;
    }

    public Kullanici getKullanici() {
        return kullanici;
    }

    public void setKullanici(Kullanici kullanici) {
        this.kullanici = kullanici;
    }

    public List<Kullanici> getFavoriAlanlar() {
        return favoriAlanlar;
    }

    public void setFavoriAlanlar(List<Kullanici> favoriAlanlar) {
        this.favoriAlanlar = favoriAlanlar;
    }

    public List<EmlakFoto> getFotolar() {
        return fotolar;
    }

    public void setFotolar(List<EmlakFoto> fotolar) {
        this.fotolar = fotolar;
    }

    public Emlak() {
        // Parametresiz constructor, Spring MVC formları için gerekli
    }

    public Emlak(Long id) {
        this.id = id;
    }

}
