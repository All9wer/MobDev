package ru.mirea.chirkovia.audiocontrol;

import android.Manifest;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.io.File;
import java.io.IOException;

import ru.mirea.chirkovia.audiocontrol.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_CODE_PERMISSION = 200;
    private static final String TAG = MainActivity.class.getSimpleName();

    private ActivityMainBinding binding;

    private boolean isWork = false;
    private String recordFilePath;

    private MediaRecorder recorder = null;
    private MediaPlayer player = null;

    private boolean isStartRecording = true;
    private boolean isStartPlaying = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        int audioRecordPermissionStatus =
                ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO);

        if (audioRecordPermissionStatus == PackageManager.PERMISSION_GRANTED) {
            isWork = true;
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.RECORD_AUDIO},
                    REQUEST_CODE_PERMISSION);
        }


        File audioFile = new File(
                getExternalFilesDir(Environment.DIRECTORY_MUSIC),
                "audiorecordtest.3gp"
        );
        recordFilePath = audioFile.getAbsolutePath();

        binding.playButton.setEnabled(false);

        binding.recordButton.setOnClickListener(v -> {
            if (isStartRecording) {
                startRecording();
                binding.recordButton.setText("Stop recording");
                binding.playButton.setEnabled(false);
            } else {
                stopRecording();
                binding.recordButton.setText("Start recording");
                binding.playButton.setEnabled(true);
            }
            isStartRecording = !isStartRecording;
        });

        binding.playButton.setOnClickListener(v -> {
            if (isStartPlaying) {
                startPlaying();
                binding.playButton.setText("Stop playing");
                binding.recordButton.setEnabled(false);
            } else {
                stopPlaying();
                binding.playButton.setText("Start playing");
                binding.recordButton.setEnabled(true);
            }
            isStartPlaying = !isStartPlaying;
        });
    }

    private void startRecording() {
        if (!isWork) {
            Toast.makeText(this, "Нет разрешения на запись", Toast.LENGTH_SHORT).show();
            return;
        }
        recorder = new MediaRecorder();
        recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        recorder.setOutputFile(recordFilePath);
        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
        try {
            recorder.prepare();
        } catch (IOException e) {
            Log.e(TAG, "prepare() failed");
        }
        recorder.start();
        Toast.makeText(this, "Запись начата", Toast.LENGTH_SHORT).show();
    }

    private void stopRecording() {
        if (recorder != null) {
            recorder.stop();
            recorder.release();
            recorder = null;
            Toast.makeText(this, "Запись остановлена", Toast.LENGTH_SHORT).show();
        }
    }

    private void startPlaying() {
        player = new MediaPlayer();
        try {
            player.setDataSource(recordFilePath);
            player.prepare();
            player.start();
            Toast.makeText(this, "Воспроизведение", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            Log.e(TAG, "prepare() failed");
        }
    }

    private void stopPlaying() {
        if (player != null) {
            player.release();
            player = null;
            Toast.makeText(this, "Проигрывание остановлено", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE_PERMISSION) {
            isWork = grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED;
            if (!isWork) {
                Toast.makeText(this, "Разрешение не выдано, приложение закроется",
                        Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }
}
