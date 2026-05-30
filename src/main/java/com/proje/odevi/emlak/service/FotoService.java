package com.proje.odevi.emlak.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Service
public class FotoService {

    public byte[] resize(MultipartFile file, int maxWidth) throws IOException {

        BufferedImage original = ImageIO.read(file.getInputStream());

        int width = original.getWidth();
        int height = original.getHeight();

        // Zaten küçükse hiç dokunma
        if (width <= maxWidth) {
            return file.getBytes();
        }

        // Yeni boyutları hesapla
        int newWidth = maxWidth;
        int newHeight = (newWidth * height) / width;

        // Yeni boş resim oluştur
        BufferedImage resized = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_RGB);

        Graphics2D g = resized.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g.drawImage(original, 0, 0, newWidth, newHeight, null);
        g.dispose();

        // Byte array'e çevir
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(resized, "jpg", baos);

        return baos.toByteArray();
    }
}
