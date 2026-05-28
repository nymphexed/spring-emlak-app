package com.proje.odevi.emlak.model;

import java.util.List;

import jakarta.persistence.*;


@Entity
@Table(name = "IL")
public class Il {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String ad;

    @OneToMany(mappedBy = "il", fetch = FetchType.LAZY)
    private List<Ilce> ilceler;

    // Getter – Setter
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

    public List<Ilce> getIlceler() {
        return ilceler;
    }


    
}