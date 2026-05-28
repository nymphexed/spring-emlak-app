package com.proje.odevi.emlak.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.proje.odevi.emlak.model.Kullanici;

public class CustomUserDetails implements UserDetails {

    private Kullanici kullanici;

    public CustomUserDetails(Kullanici kullanici) {
        this.kullanici = kullanici;
    }

    public void setKullanici(Kullanici kullanici) {
    this.kullanici = kullanici;
    }

    public Kullanici getKullanici() {
        return kullanici;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> auth = new ArrayList<>();

        // Her kullanıcı alıcıdır
        auth.add(new SimpleGrantedAuthority("ROLE_ALICI"));

        // Satıcı ise ek rol
        if (kullanici.isSeller()) {
            auth.add(new SimpleGrantedAuthority("ROLE_SATICI"));
        }

        return auth;
    }

    @Override
    public String getPassword() {
        return kullanici.getSifre();
    }

    @Override
    public String getUsername() {
        return kullanici.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() { return true; }

    @Override
    public boolean isAccountNonLocked() { return true; }

    @Override
    public boolean isCredentialsNonExpired() { return true; }

    @Override
    public boolean isEnabled() { return true; }
}
