package ru.mirea.chirkovia.lesson4;

import android.os.Bundle;
import android.util.Log;
import androidx.appcompat.app.AppCompatActivity;
import ru.mirea.chirkovia.lesson4.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.editTextMirea.setText("Мой первый текст с ViewBinding");

        binding.buttonMirea.setOnClickListener(view -> {
            String text = binding.editTextMirea.getText().toString();
            binding.textViewMirea.setText(text);

            Log.d(MainActivity.class.getSimpleName(), "buttonMirea clicked, text = " + text);
        });
    }
}