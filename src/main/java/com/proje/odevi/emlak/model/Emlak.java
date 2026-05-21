package com.proje.odevi.emlak.model;

import jakarta.persistence.*;

@Entity
@Table(name = "emlak")
public class Emlak {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String baslik;
    private Double fiyat;

    private String adres;
    private Integer odaSayisi;
    private Integer metrekare;

    @Column(length = 1000)
    private String aciklama;

    private String il;
    private String ilce;

    private Integer binaYasi;
    private Integer kat;

    private Boolean esyali;

    private String isitmaTipi;
    // Getter - Setter
}
