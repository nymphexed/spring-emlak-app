package com.proje.odevi.emlak.model;

import java.util.List;

import jakarta.persistence.*;

@Entity
@Table(name = "EMLAK")
public class Emlak {

    @Id
    @SequenceGenerator(name="emlak_seq", sequenceName="EMLAK_SEQ", allocationSize=1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="emlak_seq")
    @Column(name = "ID")
    private Long id;

    @ManyToMany(mappedBy = "favoriler")
    private List<Kullanici> favoriAlanlar;

    @Column(name = "ADRES")
    private String adres;

    @Column(name = "BASLIK")
    private String baslik;

    @Column(name = "BINA_YASI")
    private Integer binaYasi;

    @Column(name = "ESYALI")
    private Integer esyali;

    @Column(name = "FIYAT")
    private Double fiyat;

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
    @JoinColumn(name = "IL_ID")
    private Il il;

    @ManyToOne
    @JoinColumn(name = "ILCE_ID")
    private Ilce ilce;

    @ManyToOne
    @JoinColumn(name = "KATEGORI_ID")
    private Kategori kategori;


    @ManyToOne
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

    public Double getFiyat() {
        return fiyat;
    }
    public void setFiyat(Double fiyat) {
        this.fiyat = fiyat;
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
}
