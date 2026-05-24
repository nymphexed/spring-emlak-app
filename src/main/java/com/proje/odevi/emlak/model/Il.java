package com.proje.odevi.emlak.model;

import jakarta.persistence.*;


@Entity
@Table(name = "IL")
public class Il {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String ad;

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

    // Getter – Setter
}