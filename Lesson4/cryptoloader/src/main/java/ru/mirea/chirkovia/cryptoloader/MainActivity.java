package ru.mirea.chirkovia.cryptoloader;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;

import javax.crypto.SecretKey;

import ru.mirea.chirkovia.cryptoloader.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity
        implements LoaderManager.LoaderCallbacks<String> {

    private static final int LOADER_ID = 1234;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.buttonEncrypt.setOnClickListener(v -> {
            String phrase = binding.editTextPhrase.getText().toString().trim();
            if (phrase.isEmpty()) {
                Toast.makeText(this, "Введите фразу", Toast.LENGTH_SHORT).show();
                return;
            }

            // 1. Генерируем ключ по какому‑то seed (можно использовать саму фразу или константу)
            SecretKey key = MyLoader.generateKey("my_super_seed");

            // 2. Шифруем фразу в Activity
            byte[] cipherText = MyLoader.encryptMsg(phrase, key);

            // 3. Упаковываем зашифрованный текст и ключ в Bundle
            Bundle args = new Bundle();
            args.putByteArray(MyLoader.ARG_CIPHER_TEXT, cipherText);
            args.putByteArray(MyLoader.ARG_KEY, key.getEncoded());

            // 4. Запускаем Loader (если уже есть — перезапускаем)
            LoaderManager.getInstance(this)
                    .restartLoader(LOADER_ID, args, this);
        });
    }

    // --------- LoaderCallbacks ---------

    @NonNull
    @Override
    public Loader<String> onCreateLoader(int id, @Nullable Bundle args) {
        if (id == LOADER_ID) {
            return new MyLoader(this, args);
        }
        throw new IllegalArgumentException("Invalid loader id");
    }

    @Override
    public void onLoadFinished(@NonNull Loader<String> loader, String data) {
        if (loader.getId() == LOADER_ID && data != null) {
            // Показываем расшифрованную фразу
            Toast.makeText(this, "Дешифровано: " + data, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<String> loader) {
        // Ничего не делаем, фраза нам больше не нужна
    }
}
