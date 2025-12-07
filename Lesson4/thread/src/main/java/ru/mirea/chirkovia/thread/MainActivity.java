package ru.mirea.chirkovia.thread;
import android.os.Bundle;
import android.util.Log;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Arrays;
import ru.mirea.chirkovia.thread.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private static final String TAG = MainActivity.class.getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Thread mainThread = Thread.currentThread();
        // Записываем поток имя в TextView
        binding.textView.setText("Имя потока: " + mainThread.getName());
        mainThread.setName("StudentThread-22");
        binding.textView.append(
                "\nНовое имя: " + mainThread.getName() +
                        "\nПриоритет: " + mainThread.getPriority()
        );
        Log.d(TAG, "Thread stack: " + Arrays.toString(mainThread.getStackTrace()));
        binding.buttonMirea.setOnClickListener(v -> {
            long endTime = System.currentTimeMillis() + 20 * 1000; // 20 секунд

            while (System.currentTimeMillis() < endTime) {
                synchronized (this) {
                    try {
                        wait(endTime - System.currentTimeMillis());
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
            }

            Log.d(TAG, "Heavy operation finished");
        });
    }
}
