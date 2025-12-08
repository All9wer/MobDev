package ru.mirea.chirkovia.lesson6;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import ru.mirea.chirkovia.lesson6.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private static final String PREF_NAME = "mirea_settings";
    private static final String KEY_GROUP = "GROUP";
    private static final String KEY_NUMBER = "NUMBER";
    private static final String KEY_FILM = "FAVORITE_FILM";

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        SharedPreferences sharedPref =
                getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        String group = sharedPref.getString(KEY_GROUP, "");
        int number = sharedPref.getInt(KEY_NUMBER, 0);
        String film = sharedPref.getString(KEY_FILM, "");

        binding.editTextGroup.setText(group);
        if (number != 0) {
            binding.editTextNumber.setText(String.valueOf(number));
        }
        binding.editTextFilm.setText(film);

        // сохранение
        binding.buttonSave.setOnClickListener(v -> {
            String newGroup = binding.editTextGroup.getText().toString();
            String numberStr = binding.editTextNumber.getText().toString();
            String newFilm = binding.editTextFilm.getText().toString();

            int newNumber = 0;
            if (!numberStr.isEmpty()) {
                newNumber = Integer.parseInt(numberStr);
            }

            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putString(KEY_GROUP, newGroup);
            editor.putInt(KEY_NUMBER, newNumber);
            editor.putString(KEY_FILM, newFilm);
            editor.apply();

            Toast.makeText(this, "Сохранено в SharedPreferences",
                    Toast.LENGTH_SHORT).show();
        });
    }
}
