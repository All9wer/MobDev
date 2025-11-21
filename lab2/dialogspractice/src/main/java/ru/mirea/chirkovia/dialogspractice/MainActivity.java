package ru.mirea.chirkovia.dialogspractice;


import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClickTime(View view) {
        DialogFragment dialog = new MyTimeDialogFragment();
        dialog.show(getSupportFragmentManager(), "time");
    }

    public void onClickDate(View view) {
        DialogFragment dialog = new MyDateDialogFragment();
        dialog.show(getSupportFragmentManager(), "date");
    }

    public void onClickProgress(View view) {
        DialogFragment dialog = new MyProgressDialogFragment();
        dialog.show(getSupportFragmentManager(), "progress");
    }
}