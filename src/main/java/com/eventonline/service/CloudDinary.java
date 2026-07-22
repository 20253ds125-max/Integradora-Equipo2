package com.eventonline.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import io.github.cdimascio.dotenv.Dotenv;
import jakarta.servlet.http.Part;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class CloudDinary {

    private final Cloudinary cloudinary;

    public CloudDinary(){
        Dotenv dotenv =Dotenv.load();
        this.cloudinary = new Cloudinary(ObjectUtils.asMap(
                "cloud_name", dotenv.get("CLOUD_NAME"),
                "api_key", dotenv.get("API_KEY"),
                "api_secret", dotenv.get("API_SECRET")
        ));
    }

    public List<String> subirFotos(Collection<Part> partes)throws Exception{
        List<String> rutasFotos = new ArrayList<>();

        for (Part parte : partes) {
            if (parte.getName().equals("photos") && parte.getSize() > 0) {
                InputStream inputStream = parte.getInputStream();
                byte[] bytesImagen = inputStream.readAllBytes();

                Map respuestaNube = cloudinary.uploader().upload(bytesImagen, ObjectUtils.emptyMap());
                rutasFotos.add((String) respuestaNube.get("secure_url"));
            }
        }
        return rutasFotos;
    }
}
