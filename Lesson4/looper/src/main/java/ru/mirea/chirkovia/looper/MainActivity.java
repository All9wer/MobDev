package ru.mirea.chirkovia.looper;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import ru.mirea.chirkovia.looper.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private MyLooper myLooper;

    private final Handler mainThreadHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(@NonNull Message msg) {
            String result = msg.getData().getString("RESULT");
            Log.d(MainActivity.class.getSimpleName(),
                    "Task execute. This is result: " + result);
            if (result != null) {
                Toast.makeText(MainActivity.this, result, Toast.LENGTH_SHORT).show();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        myLooper = new MyLooper(mainThreadHandler);
        myLooper.start();

        binding.buttonMirea.setOnClickListener(v -> {
            if (myLooper == null || myLooper.mHandler == null) {
                Toast.makeText(this, "Looper ещё не готов", Toast.LENGTH_SHORT).show();
                return;
            }

            String ageStr = binding.editTextAge.getText().toString().trim();
            String job = binding.editTextJob.getText().toString().trim();

            int age = 0;
            try {
                age = Integer.parseInt(ageStr);
            } catch (NumberFormatException e) {
                Toast.makeText(this, "Введите возраст", Toast.LENGTH_SHORT).show();
                return;
            }

            Message msg = Message.obtain();
            Bundle bundle = new Bundle();
            bundle.putInt(MyLooper.KEY_AGE, age);
            bundle.putString(MyLooper.KEY_JOB, job);
            msg.setData(bundle);

            myLooper.mHandler.sendMessage(msg);
        });
    }
}
