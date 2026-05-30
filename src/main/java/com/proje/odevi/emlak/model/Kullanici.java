package com.proje.odevi.emlak.model;

import java.util.List;

import jakarta.persistence.*;

@Entity
@Table(name = "KULLANICI")
public class Kullanici {

    @Id
    @SequenceGenerator(name="kullanici_seq", sequenceName="KULLANICI_SEQ", allocationSize=1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="kullanici_seq")
    private Long id;

    private String ad;
    private String soyad;
    private String email;
    private String sifre;

    //  Yeni sistem: Kullanıcı satıcı mı? false → sadece alıcı   true  → hem alıcı hem satıcı
 
    @Column(name = "IS_SELLER")
    private boolean isSeller = false;


    @ManyToMany
    @JoinTable(
        name = "FAVORI",
        joinColumns = @JoinColumn(name = "KULLANICI_ID"),
        inverseJoinColumns = @JoinColumn(name = "EMLAK_ID")
    )
    private List<Emlak> favoriler;

    @OneToOne(mappedBy = "kullanici",
          fetch = FetchType.LAZY,
          cascade = CascadeType.ALL,
          orphanRemoval = true)
    private EmlakIsletmesi isletme;


    // GETTER - SETTER

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getAd() {
        return ad;
    }
    public void setAd(String ad) {
        this.ad = ad;
    }

    public String getSoyad() {
        return soyad;
    }
    public void setSoyad(String soyad) {
        this.soyad = soyad;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public String getSifre() {
        return sifre;
    }
    public void setSifre(String sifre) {
        this.sifre = sifre;
    }

    public boolean isSeller() {
        return isSeller;
    }
    public void setSeller(boolean seller) {
        this.isSeller = seller;
    }
    
    public List<Emlak> getFavoriler() {
        return favoriler;
    }
    public void setFavoriler(List<Emlak> favoriler) {
        this.favoriler = favoriler;
    }

    public EmlakIsletmesi getIsletme() {
        return isletme;
    }
    public void setIsletme(EmlakIsletmesi isletme) {
        this.isletme = isletme;
    }

    public Kullanici() {
    // Parametresiz constructor, Spring MVC formları için gerekli
    }
    public Kullanici(Long id) {
        this.id = id;
    }

}
