package ru.mirea.chirkovia.lesson3;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class SecondActivity extends AppCompatActivity {
    private static final int LIST_NUMBER = 22;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        TextView textView = findViewById(R.id.textResult);
        Intent intent = getIntent();
        String time = intent.getStringExtra(MainActivity.EXTRA_TIME);
        int square = LIST_NUMBER * LIST_NUMBER;
        String text = "КВАДРАТ ЗНАЧЕНИЯ МОЕГО НОМЕРА ПО СПИСКУ В ГРУППЕ " +
                "СОСТАВЛЯЕТ ЧИСЛО " + square +
                ", а текущее время " + time;
        textView.setText(text);
    }
}
