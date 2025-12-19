package ru.mirea.chirkovia.yandexdriver;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.yandex.mapkit.MapKitFactory;
import com.yandex.mapkit.geometry.Point;
import com.yandex.mapkit.map.CameraPosition;
import com.yandex.mapkit.map.Map;
import com.yandex.mapkit.mapview.MapView;
import com.yandex.mapkit.map.PolylineMapObject;
import com.yandex.mapkit.Animation;
import com.yandex.mapkit.directions.DirectionsFactory;
import com.yandex.mapkit.directions.driving.DrivingOptions;
import com.yandex.mapkit.directions.driving.DrivingRoute;
import com.yandex.mapkit.directions.driving.DrivingRouter;
import com.yandex.mapkit.directions.driving.DrivingSession;
import com.yandex.mapkit.directions.driving.VehicleOptions;
import com.yandex.mapkit.RequestPoint;
import com.yandex.mapkit.RequestPointType;
import com.yandex.runtime.Error;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements DrivingSession.DrivingRouteListener {

    private MapView mapView;
    private DrivingRouter drivingRouter;
    private DrivingSession drivingSession;
    private final List<PolylineMapObject> routePolylines = new ArrayList<>();

    private final Point ROUTE_START = new Point(55.751574, 37.573856);
    private final Point ROUTE_END = new Point(55.758254, 37.601170);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        MapKitFactory.initialize(this);
        setContentView(R.layout.activity_main);

        mapView = findViewById(R.id.mapview);

        mapView.getMap().move(
                new CameraPosition(ROUTE_START, 12.0f, 0.0f, 0.0f),
                new Animation(Animation.Type.SMOOTH, 0),
                null
        );

        DirectionsFactory.initialize(this);
        drivingRouter = DirectionsFactory.getInstance().createDrivingRouter();

        requestRoutes();
    }

    private void requestRoutes() {
        clearRoutes();

        DrivingOptions drivingOptions = new DrivingOptions();
        drivingOptions.setRoutesCount(4); // нужно 4 маршрута :contentReference[oaicite:4]{index=4}
        VehicleOptions vehicleOptions = new VehicleOptions();

        List<RequestPoint> points = new ArrayList<>();
        points.add(new RequestPoint(ROUTE_START, RequestPointType.WAYPOINT, null));
        points.add(new RequestPoint(ROUTE_END, RequestPointType.WAYPOINT, null));

        drivingSession = drivingRouter.requestRoutes(points, drivingOptions, vehicleOptions, this);
    }

    private void clearRoutes() {
        for (PolylineMapObject polyline : routePolylines) {
            mapView.getMap().getMapObjects().remove(polyline);
        }
        routePolylines.clear();
    }

    @Override
    public void onDrivingRoutes(List<DrivingRoute> routes) {
        for (DrivingRoute route : routes) {
            PolylineMapObject polyline = mapView.getMap().getMapObjects().addPolyline(route.getGeometry());
            routePolylines.add(polyline);
        }
    }

    @Override
    public void onDrivingRoutesError(Error error) {
    }

    @Override
    protected void onStart() {
        super.onStart();
        MapKitFactory.getInstance().onStart();
        mapView.onStart();
    }

    @Override
    protected void onStop() {
        mapView.onStop();
        MapKitFactory.getInstance().onStop();
        super.onStop();
    }
}
