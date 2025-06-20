package com.cbf.common.utils.sign;


import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

/**
 * @author : Liuqijie
 * @Date: 2025/6/20 11:27
 */
public class Aes {
    private static final String ALGORITHM = "AES";
    private static final String SECRET = "1234567890123456"; // 16位密钥

    public static String encrypt(String value) throws Exception {
        SecretKeySpec key = new SecretKeySpec(SECRET.getBytes(), ALGORITHM);
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, key);
        return Base64.getEncoder().encodeToString(cipher.doFinal(value.getBytes()));
    }

    public static String decrypt(String encrypted) throws Exception {
        SecretKeySpec key = new SecretKeySpec(SECRET.getBytes(), ALGORITHM);
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, key);
        return new String(cipher.doFinal(Base64.getDecoder().decode(encrypted)));
    }

    public static void main(String[] args) throws Exception {
        String encrypted = encrypt("cbf_redis");
        System.out.println("Encrypted password: " + encrypted);
    }
}
