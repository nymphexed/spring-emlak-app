package com.proje.odevi.emlak.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;

@Entity
public class EmlakFoto {

    @Id
    @SequenceGenerator(name = "emlakfoto_seq", sequenceName = "EMLAK_FOTO_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "emlakfoto_seq")
    private Long id;

    private String url;

    @ManyToOne
    @JoinColumn(name = "emlak_id")
    private Emlak emlak;

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Emlak getEmlak() {
        return emlak;
    }

    public void setEmlak(Emlak emlak) {
        this.emlak = emlak;
    }

}
