package ru.mirea.chirkovia.notebook;

import android.os.Bundle;
import android.os.Environment;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

import ru.mirea.chirkovia.notebook.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.buttonSave.setOnClickListener(v -> {
            String name = binding.editTextFileName.getText().toString().trim();
            String quote = binding.editTextQuote.getText().toString();
            if (name.isEmpty()) {
                Toast.makeText(this, "Введите название файла", Toast.LENGTH_SHORT).show();
                return;
            }
            writeFileToExternalStorage(name + ".txt", quote);
        });

        binding.buttonLoad.setOnClickListener(v -> {
            String name = binding.editTextFileName.getText().toString().trim();
            if (name.isEmpty()) {
                Toast.makeText(this, "Введите название файла", Toast.LENGTH_SHORT).show();
                return;
            }
            String text = readFileFromExternalStorage(name + ".txt");
            if (text != null) {
                binding.editTextQuote.setText(text);
            }
        });
    }

    private void writeFileToExternalStorage(String fileName, String data) {
        File path = getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS);
        if (path != null && !path.exists()) {
            path.mkdirs();
        }
        if (path == null) {
            Toast.makeText(this, "Нет доступа к хранилищу", Toast.LENGTH_SHORT).show();
            return;
        }
        File file = new File(path, fileName);
        try (FileOutputStream fos = new FileOutputStream(file, false)) {
            fos.write(data.getBytes(StandardCharsets.UTF_8));
            Toast.makeText(this, "Файл сохранён: " + file.getAbsolutePath(),
                    Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(this, "Ошибка записи: " + e.getMessage(),
                    Toast.LENGTH_SHORT).show();
        }
    }

    private String readFileFromExternalStorage(String fileName) {
        File path = getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS);
        if (path == null) {
            Toast.makeText(this, "Нет доступа к хранилищу", Toast.LENGTH_SHORT).show();
            return null;
        }
        File file = new File(path, fileName);
        if (!file.exists()) {
            Toast.makeText(this, "Файл не найден", Toast.LENGTH_SHORT).show();
            return null;
        }
        StringBuilder builder = new StringBuilder();
        try (FileInputStream fis = new FileInputStream(file);
             InputStreamReader isr = new InputStreamReader(fis, StandardCharsets.UTF_8);
             BufferedReader reader = new BufferedReader(isr)) {

            String line = reader.readLine();
            while (line != null) {
                builder.append(line).append("\n");
                line = reader.readLine();
            }
            Toast.makeText(this, "Файл загружен", Toast.LENGTH_SHORT).show();
            return builder.toString().trim();
        } catch (Exception e) {
            Toast.makeText(this, "Ошибка чтения: " + e.getMessage(),
                    Toast.LENGTH_SHORT).show();
            return null;
        }
    }
}
