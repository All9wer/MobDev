package ru.mirea.chirkovia.cryptoloader;

import android.content.Context;
import android.os.Bundle;
import android.os.SystemClock;
import androidx.annotation.NonNull;
import androidx.loader.content.AsyncTaskLoader;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;


public class MyLoader extends AsyncTaskLoader<String> {
    public static final String ARG_CIPHER_TEXT = "ARG_CIPHER_TEXT";
    public static final String ARG_KEY = "ARG_KEY";
    private byte[] cipherText;
    private byte[] keyBytes;

    public MyLoader(@NonNull Context context, Bundle args) {
        super(context);
        if (args != null) {
            cipherText = args.getByteArray(ARG_CIPHER_TEXT);
            keyBytes = args.getByteArray(ARG_KEY);
        }
    }
    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        forceLoad();
    }
    @Override
    public String loadInBackground() {
        if (cipherText == null || keyBytes == null) {
            return null;
        }

        // Имитируем долгую операцию
        SystemClock.sleep(3000);

        SecretKey originalKey = new SecretKeySpec(keyBytes, 0, keyBytes.length, "AES");
        return decryptMsg(cipherText, originalKey);
    }

    // шифрование

    public static SecretKey generateKey(String seed) {
        try {
            java.security.SecureRandom sr =
                    java.security.SecureRandom.getInstance("SHA1PRNG");
            sr.setSeed(seed.getBytes());
            KeyGenerator kg = KeyGenerator.getInstance("AES");
            kg.init(256, sr);
            return new SecretKeySpec(kg.generateKey().getEncoded(), "AES");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public static byte[] encryptMsg(String message, SecretKey secret) {
        try {
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, secret);
            return cipher.doFinal(message.getBytes());
        } catch (NoSuchAlgorithmException
                 | NoSuchPaddingException
                 | InvalidKeyException
                 | BadPaddingException
                 | IllegalBlockSizeException e) {
            throw new RuntimeException(e);
        }
    }

    public static String decryptMsg(byte[] cipherText, SecretKey secret) {
        try {
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE, secret);
            return new String(cipher.doFinal(cipherText));
        } catch (NoSuchAlgorithmException
                 | NoSuchPaddingException
                 | InvalidKeyException
                 | BadPaddingException
                 | IllegalBlockSizeException e) {
            throw new RuntimeException(e);
        }
    }
}
