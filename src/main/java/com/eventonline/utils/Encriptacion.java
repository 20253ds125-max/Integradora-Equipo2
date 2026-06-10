package com.eventonline.utils;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Encriptacion {

    public String Encriptar(String pass){
        try {
            MessageDigest algoritmo = MessageDigest.getInstance("SHA-256");

            byte[] hashAlgoritmos = algoritmo.digest(pass.getBytes(StandardCharsets.UTF_8));

            StringBuilder hexString = new StringBuilder();
            for (byte b : hashAlgoritmos) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            return hexString.toString();

        }catch (NoSuchAlgorithmException e){
            System.err.println(e.getMessage());
            return pass;
        }
    }
}
