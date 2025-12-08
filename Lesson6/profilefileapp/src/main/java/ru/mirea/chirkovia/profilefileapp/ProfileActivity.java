package ru.mirea.chirkovia.profilefileapp;

import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import ru.mirea.chirkovia.profilefileapp.databinding.ActivityProfileBinding;

public class ProfileActivity extends AppCompatActivity {

    private static final String PREF_NAME = "profile_prefs";
    private static final String KEY_NICK = "NICK";
    private static final String KEY_HERO = "HERO";
    private static final String KEY_MOOD = "MOOD";
    private ActivityProfileBinding binding;
    private SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        prefs = getSharedPreferences(PREF_NAME, MODE_PRIVATE);

        binding.editTextNickname.setText(prefs.getString(KEY_NICK, ""));
        binding.editTextHero.setText(prefs.getString(KEY_HERO, ""));
        binding.editTextMood.setText(prefs.getString(KEY_MOOD, ""));

        binding.buttonSaveProfile.setOnClickListener(v -> {
            SharedPreferences.Editor e = prefs.edit();
            e.putString(KEY_NICK, binding.editTextNickname.getText().toString());
            e.putString(KEY_HERO, binding.editTextHero.getText().toString());
            e.putString(KEY_MOOD, binding.editTextMood.getText().toString());
            e.apply();
        });
    }
}
