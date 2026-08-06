package com.eventonline.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import io.github.cdimascio.dotenv.Dotenv;
import jakarta.servlet.http.Part;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class CloudDinary {

    private final Cloudinary cloudinary;

    public CloudDinary(){
        Dotenv dotenv = Dotenv.load();
        this.cloudinary = new Cloudinary(ObjectUtils.asMap(
                "cloud_name", dotenv.get("CLOUD_NAME"),
                "api_key", dotenv.get("API_KEY"),
                "api_secret", dotenv.get("API_SECRET")
        ));
    }

    public List<String> subirFotos(Collection<Part> partes) throws Exception {
        List<String> rutasFotos = new ArrayList<>();

        for (Part parte : partes) {
            if (parte.getSubmittedFileName() != null && !parte.getSubmittedFileName().trim().isEmpty() && parte.getSize() > 0) {
                InputStream inputStream = parte.getInputStream();
                byte[] bytesImagen = inputStream.readAllBytes();

                validarEsImagen(bytesImagen);

                Map respuestaNube = cloudinary.uploader().upload(bytesImagen, ObjectUtils.emptyMap());
                rutasFotos.add((String) respuestaNube.get("secure_url"));
            }
        }
        return rutasFotos;
    }

    public String subirFoto(Part parte) throws Exception {
        if (parte == null || parte.getSize() == 0) {
            throw new IllegalArgumentException("El archivo de imagen está vacío o es nulo.");
        }
        InputStream inputStream = parte.getInputStream();
        byte[] bytesImagen = inputStream.readAllBytes();

        validarEsImagen(bytesImagen);

        Map respuestaNube = cloudinary.uploader().upload(bytesImagen, ObjectUtils.emptyMap());

        return (String) respuestaNube.get("secure_url");
    }

    private void validarEsImagen(byte[] bytes) {
        if (bytes == null || bytes.length < 12) {
            throw new IllegalArgumentException("El archivo seleccionado está vacío o es demasiado pequeño.");
        }

        boolean esJpg = (bytes[0] == (byte) 0xFF && bytes[1] == (byte) 0xD8 && bytes[2] == (byte) 0xFF);
        boolean esPng = (bytes[0] == (byte) 0x89 && bytes[1] == (byte) 0x50 && bytes[2] == (byte) 0x4E && bytes[3] == (byte) 0x47);
        boolean esGif = (bytes[0] == (byte) 0x47 && bytes[1] == (byte) 0x49 && bytes[2] == (byte) 0x46);
        boolean esWebp = (bytes[0] == (byte) 'R' && bytes[1] == (byte) 'I' && bytes[2] == (byte) 'F' && bytes[3] == (byte) 'F' &&
                bytes[8] == (byte) 'W' && bytes[9] == (byte) 'E' && bytes[10] == (byte) 'B' && bytes[11] == (byte) 'P');

        if (!esJpg && !esPng && !esGif && !esWebp) {
            throw new IllegalArgumentException("Formato no permitido o extensión alterada. Solo se aceptan imágenes reales (JPG, PNG, GIF, WEBP).");
        }

        if (!esWebp) {
            try {
                BufferedImage img = ImageIO.read(new ByteArrayInputStream(bytes));
                if (img == null) {
                    throw new IllegalArgumentException("El archivo está corrupto o no es una imagen válida.");
                }
            } catch (IOException e) {
                throw new IllegalArgumentException("No se pudo procesar la estructura de la imagen.");
            }
        }
    }

    public void borrarFotos(List<String> rutasFotos) {
        try {
            for (String url : rutasFotos) {
                String idUrl = extraerIdFoto(url);
                cloudinary.uploader().destroy(idUrl, ObjectUtils.emptyMap());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void borrarFoto(String url) {
        try {
            String idUrl = extraerIdFoto(url);
            cloudinary.uploader().destroy(idUrl, ObjectUtils.emptyMap());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String extraerIdFoto(String url) {
        int inicio = url.lastIndexOf("/") + 1;
        int fin = url.lastIndexOf(".");
        if (inicio > 0 && fin > inicio) {
            return url.substring(inicio, fin);
        }
        return null;
    }
}