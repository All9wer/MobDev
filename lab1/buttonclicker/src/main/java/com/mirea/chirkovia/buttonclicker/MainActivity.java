package com.mirea.chirkovia.buttonclicker;

import com.mirea.chirkovia.buttonclicker.R;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private TextView tvOut;
    private CheckBox checkBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvOut = findViewById(R.id.tvOut);
        checkBox = findViewById(R.id.checkBox);
    }
    public void onMyButtonClick(View view) {
        TextView tvOut = findViewById(R.id.tvOut);
        CheckBox checkBox = findViewById(R.id.checkBox);

        if (view.getId() == R.id.btnWhoAmI) {
            tvOut.setText("Мой номер по списку № 22");
        } else if (view.getId() == R.id.btnItIsNotMe) {
            tvOut.setText("Это не я сделал");
        }

        checkBox.setChecked(!checkBox.isChecked());
    }
}