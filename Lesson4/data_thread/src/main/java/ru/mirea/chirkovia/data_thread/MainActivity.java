package ru.mirea.chirkovia.data_thread;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import java.util.concurrent.TimeUnit;
import ru.mirea.chirkovia.data_thread.databinding.ActivityMainBinding; // поправь пакет
public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        final Runnable runn1 = new Runnable() {
            @Override
            public void run() {
                binding.tvInfo.setText("runn1: обновление из UI-потока (runOnUiThread)");
            }
        };

        final Runnable runn2 = new Runnable() {
            @Override
            public void run() {
                binding.tvInfo.setText("runn2: обновление через post");
            }
        };

        final Runnable runn3 = new Runnable() {
            @Override
            public void run() {
                binding.tvInfo.setText("runn3: обновление через postDelayed");
            }
        };

        // Фоновый поток, который по очереди вызывает эти Runnable
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    TimeUnit.SECONDS.sleep(2);
                    runOnUiThread(runn1);
                    TimeUnit.SECONDS.sleep(1);
                    binding.tvInfo.postDelayed(runn3, 2000);
                    binding.tvInfo.post(runn2);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        t.start();
    }
}