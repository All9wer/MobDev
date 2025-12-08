package ru.mirea.chirkovia.securesharedpreferences;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.security.crypto.EncryptedSharedPreferences;
import androidx.security.crypto.MasterKeys;

import java.io.IOException;
import java.security.GeneralSecurityException;

import ru.mirea.chirkovia.securesharedpreferences.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private static final String PREF_FILE = "secret_shared_prefs";
    private static final String KEY_POET = "FAVORITE_POET";

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        try {
            String masterKeyAlias = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC);

            SharedPreferences secureSharedPreferences =
                    EncryptedSharedPreferences.create(
                            PREF_FILE,
                            masterKeyAlias,
                            getBaseContext(),
                            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
                    );

            if (!secureSharedPreferences.contains(KEY_POET)) {
                secureSharedPreferences.edit()
                        .putString(KEY_POET, "Маяковский")
                        .apply();
            }

            String poetName = secureSharedPreferences.getString(KEY_POET, "Неизвестный поэт");
            binding.textPoetName.setText(poetName);

        } catch (GeneralSecurityException | IOException e) {
            throw new RuntimeException(e);
        }
    }
}
