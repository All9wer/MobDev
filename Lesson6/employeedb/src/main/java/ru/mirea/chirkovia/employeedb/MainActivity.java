package ru.mirea.chirkovia.employeedb;

import android.os.Bundle;
import android.text.TextUtils;
import androidx.appcompat.app.AppCompatActivity;
import java.util.List;
import ru.mirea.chirkovia.employeedb.databinding.ActivityMainBinding;
public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private SuperHeroDao heroDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        AppDatabase db = App.getInstance().getDatabase();
        heroDao = db.superHeroDao();

        binding.buttonAdd.setOnClickListener(v -> {
            String name = binding.editTextName.getText().toString().trim();
            String power = binding.editTextPower.getText().toString().trim();
            String levelStr = binding.editTextLevel.getText().toString().trim();

            if (TextUtils.isEmpty(name) || TextUtils.isEmpty(power) || TextUtils.isEmpty(levelStr)) {
                binding.textViewOutput.setText("Заполните все поля для добавления героя");
                return;
            }

            int level = Integer.parseInt(levelStr);

            SuperHero hero = new SuperHero();
            hero.name = name;
            hero.power = power;
            hero.level = level;
            heroDao.insert(hero);

            binding.textViewOutput.setText("Герой добавлен");
            clearInputs();
        });

        binding.buttonUpdate.setOnClickListener(v -> {
            String name = binding.editTextName.getText().toString().trim();
            String power = binding.editTextPower.getText().toString().trim();
            String levelStr = binding.editTextLevel.getText().toString().trim();

            SuperHero hero = heroDao.getById(1);
            if (hero == null) {
                binding.textViewOutput.setText("Герой с id=1 не найден");
                return;
            }

            if (!TextUtils.isEmpty(name)) hero.name = name;
            if (!TextUtils.isEmpty(power)) hero.power = power;
            if (!TextUtils.isEmpty(levelStr)) {
                hero.level = Integer.parseInt(levelStr);
            }

            heroDao.update(hero);
            binding.textViewOutput.setText("Герой с id=1 обновлён");
            clearInputs();
        });

        binding.buttonShowAll.setOnClickListener(v -> showAllHeroes());
    }

    private void showAllHeroes() {
        List<SuperHero> heroes = heroDao.getAll();
        if (heroes.isEmpty()) {
            binding.textViewOutput.setText("В базе нет героев");
            return;
        }
        StringBuilder sb = new StringBuilder();
        for (SuperHero h : heroes) {
            sb.append(h.id)
                    .append(": ")
                    .append(h.name)
                    .append(" (")
                    .append(h.power)
                    .append("), уровень=")
                    .append(h.level)
                    .append("\n");
        }
        binding.textViewOutput.setText(sb.toString().trim());
    }

    private void clearInputs() {
        binding.editTextName.setText("");
        binding.editTextPower.setText("");
        binding.editTextLevel.setText("");
    }
}
