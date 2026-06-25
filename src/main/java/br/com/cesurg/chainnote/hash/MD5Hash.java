package br.com.cesurg.chainnote.hash;

import java.security.MessageDigest;

public class MD5Hash implements CalculadoraHash {

    @Override
    public String calcular(String entrada) {
        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            byte[] bytes = digest.digest(entrada.getBytes());

            StringBuilder sb = new StringBuilder();
            for (byte b : bytes) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();

        } catch (Exception e) {
            throw new RuntimeException("Erro ao calcular MD5", e);
        }
    }
}
