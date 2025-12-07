package ru.mirea.chirkovia.workmanager;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.work.Constraints;
import androidx.work.NetworkType;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;

import ru.mirea.chirkovia.workmanager.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.buttonStartWork.setOnClickListener(v -> {
            // только Wi‑Fi
            Constraints constraints = new Constraints.Builder()
                    .setRequiredNetworkType(NetworkType.UNMETERED)  // Wi‑Fi
                    .build();

            OneTimeWorkRequest uploadWorkRequest =
                    new OneTimeWorkRequest.Builder(UploadWorker.class)
                            .setConstraints(constraints)
                            .build();

            WorkManager workManager = WorkManager.getInstance(this);
            workManager.enqueue(uploadWorkRequest);
            workManager.getWorkInfoByIdLiveData(uploadWorkRequest.getId())
                    .observe(this, workInfo -> {
                        if (workInfo == null) return;

                        WorkInfo.State state = workInfo.getState();
                        Log.d("MainActivity", "Work state: " + state);

                        if (state == WorkInfo.State.SUCCEEDED) {
                            Toast.makeText(this,
                                    "Работа WorkManager завершена",
                                    Toast.LENGTH_SHORT).show();
                        }
                        else {Toast.makeText(this,
                                "нет интернета",
                                Toast.LENGTH_SHORT).show();}
                    });
        });
    }
}
