package com.example.bankcards.util;

import lombok.NoArgsConstructor;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

@NoArgsConstructor
public final class CardEncryptionUtil {
    private static final String ALGORITHM = "AES";
    private static final String SECRET_KEY = "9X1uVfF7FWNWiFeQ";

    public static String encrypt(String plainCardNumber) {
        try {
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            SecretKeySpec key = new SecretKeySpec(SECRET_KEY.getBytes(), ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE,key);
            return Base64.getEncoder().encodeToString(cipher.doFinal(plainCardNumber.getBytes()));
        } catch (Exception e) {
            throw new IllegalStateException("Failed to encrypt card number", e);
        }
    }

    public static String decrypt(String encryptedCardNumber) {
        try{
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            SecretKeySpec key = new SecretKeySpec(SECRET_KEY.getBytes(), ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE,key);
            return new String(cipher.doFinal(Base64.getDecoder().decode(encryptedCardNumber)));
        }catch (Exception e){
            throw new IllegalStateException("Failed to decrypt card number", e);
        }
    }
}
