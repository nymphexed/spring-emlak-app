package com.proje.odevi.emlak;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class TestBCrypt {
    public static void main(String[] args) {
        String raw = "12345";
        String dbHash = "$2a$10$7EqJtq98hPqEX7fNZaFWoOa8uG0nS1r6z5p1r8u1Y8Z8Q0uUuV2xG\r\n" + //
                        "";

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        System.out.println("Yeni üretilen hash: " + encoder.encode(raw));
        System.out.println("DB hash ile eşleşiyor mu? " + encoder.matches(raw, dbHash));
    }
}
