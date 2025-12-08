package ru.mirea.chirkovia.profilefileapp;

import android.os.Bundle;
import android.util.Base64;
import androidx.appcompat.app.AppCompatActivity;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.charset.StandardCharsets;

import ru.mirea.chirkovia.profilefileapp.databinding.ActivityFileWorkBinding;

public class FileWorkActivity extends AppCompatActivity {

    private ActivityFileWorkBinding binding;
    private final String fileName = "crypto_note.txt";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityFileWorkBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.fabEncrypt.setOnClickListener(v -> {
            String plain = binding.editTextPlain.getText().toString();
            if (plain.isEmpty()) return;

            try (FileOutputStream fos = openFileOutput(fileName, MODE_PRIVATE)) {
                fos.write(plain.getBytes(StandardCharsets.UTF_8));
            } catch (Exception ignored) {}

            try (FileInputStream fis = openFileInput(fileName)) {
                byte[] data = new byte[fis.available()];
                fis.read(data);
                String base64 = Base64.encodeToString(data, Base64.NO_WRAP);
                binding.editTextEncrypted.setText(base64);
            } catch (Exception ignored) {}
        });
    }
}
