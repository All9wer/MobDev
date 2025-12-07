package ru.mirea.chirkovia.worker;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;

import ru.mirea.chirkovia.worker.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.buttonCheckInternet.setOnClickListener(v -> {
            OneTimeWorkRequest request =
                    new OneTimeWorkRequest.Builder(InternetCheckWorker.class)
                            .build();
            WorkManager workManager = WorkManager.getInstance(this);
            workManager.enqueue(request);
            workManager.getWorkInfoByIdLiveData(request.getId())
                    .observe(this, workInfo -> {
                        if (workInfo == null) return;
                        if (workInfo.getState() == WorkInfo.State.SUCCEEDED) {
                            Data output = workInfo.getOutputData();
                            boolean hasInternet =
                                    output.getBoolean(
                                            InternetCheckWorker.KEY_HAS_INTERNET,
                                            false
                                    );

                            String msg = hasInternet
                                    ? "Интернет есть"
                                    : "Интернета нет";

                            Toast.makeText(this, msg,
                                    Toast.LENGTH_SHORT).show();
                        }
                    });
        });
    }
}