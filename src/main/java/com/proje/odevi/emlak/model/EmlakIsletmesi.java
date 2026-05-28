package com.proje.odevi.emlak.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Entity
@Table(name = "EMLAK_ISLETMESI", schema = "EMLAK")
public class EmlakIsletmesi {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "emlak_isletme_seq")
    @SequenceGenerator(name = "emlak_isletme_seq", sequenceName = "EMLAK.EMLAK_ISLETME_SEQ", allocationSize = 1)
    private Long id;

    private String isletmeAdi;
    private String yetkili;
    private String adres;
    private String telefon;
    private String fax;

    @OneToOne
    @JoinColumn(name = "kullanici_id")
    private Kullanici kullanici;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIsletmeAdi() {
        return isletmeAdi;
    }

    public void setIsletmeAdi(String isletme_adi) {
        this.isletmeAdi = isletmeAdi;
    }

    public String getYetkili() {
        return yetkili;
    }

    public void setYetkili(String yetkili) {
        this.yetkili = yetkili;
    }

    public String getAdres() {
        return adres;
    }

    public void setAdres(String adres) {
        this.adres = adres;
    }

    public String getTelefon() {
        return telefon;
    }

    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public Kullanici getKullanici() {
        return kullanici;
    }

    public void setKullanici(Kullanici kullanici) {
        this.kullanici = kullanici;
    }

    // getter – setter

    public EmlakIsletmesi() {
    }
}