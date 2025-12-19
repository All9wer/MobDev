package ru.mirea.chirkovia.yandexdriver;
import ru.mirea.chirkovia.yandexdriver.BuildConfig;
import android.app.Application;
import com.yandex.mapkit.MapKitFactory;

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        MapKitFactory.setApiKey(BuildConfig.MAPKIT_API_KEY);
    }
}
