package ru.mirea.chirkovia.yandexmaps;
import ru.mirea.chirkovia.yandexmaps.BuildConfig;
import android.app.Application;
import com.yandex.mapkit.MapKitFactory;
import android.util.Log;
public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        MapKitFactory.setApiKey(BuildConfig.MAPKIT_API_KEY);
    }
}


