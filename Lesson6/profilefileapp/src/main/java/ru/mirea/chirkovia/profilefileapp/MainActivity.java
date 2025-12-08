package ru.mirea.chirkovia.profilefileapp;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import ru.mirea.chirkovia.profilefileapp.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.buttonProfile.setOnClickListener(
                v -> startActivity(new Intent(this, ProfileActivity.class)));

        binding.buttonFiles.setOnClickListener(
                v -> startActivity(new Intent(this, FileWorkActivity.class)));
    }
}
