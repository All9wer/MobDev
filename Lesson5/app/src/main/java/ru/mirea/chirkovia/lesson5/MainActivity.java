package ru.mirea.chirkovia.lesson5;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.widget.ListView;
import android.widget.SimpleAdapter;

import ru.mirea.chirkovia.lesson5.databinding.ActivityMainBinding;
public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        //  менеджер датчиков
        SensorManager sensorManager =
                (SensorManager) getSystemService(SENSOR_SERVICE);
        List<Sensor> sensors = sensorManager.getSensorList(Sensor.TYPE_ALL);
        ArrayList<HashMap<String, Object>> arrayList = new ArrayList<>();

        for (Sensor sensor : sensors) {
            HashMap<String, Object> map = new HashMap<>();
            map.put("Name", sensor.getName());
            map.put("Value", sensor.getMaximumRange());
            arrayList.add(map);
        }
        String[] from = {"Name", "Value"};
        int[] to = {android.R.id.text1, android.R.id.text2};

        SimpleAdapter adapter = new SimpleAdapter(
                this,
                arrayList,
                android.R.layout.simple_list_item_2,
                from,
                to
        );

        ListView listView = binding.sensorListView;
        listView.setAdapter(adapter);
    }
}
