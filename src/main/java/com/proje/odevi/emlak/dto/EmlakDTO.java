package com.proje.odevi.emlak.dto;

import java.math.BigDecimal;
import java.util.List;

public class EmlakDTO {

    public Long id;
    public String baslik;
    public String adres;
    public Integer binaYasi;
    public Integer esyali;
    public BigDecimal fiyat;
    public String isitmaTipi;
    public Integer kat;
    public Integer metrekare;
    public Integer odaSayisi;
    public String aciklama;

    public String il;
    public String ilce;

    public List<String> fotolar;
}
