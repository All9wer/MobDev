package ru.mirea.chirkovia.osmmaps;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.preference.PreferenceManager;
import org.osmdroid.config.Configuration;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.api.IMapController;
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay;
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider;
import org.osmdroid.views.overlay.compass.CompassOverlay;
import org.osmdroid.views.overlay.compass.InternalCompassOrientationProvider;
import org.osmdroid.views.overlay.ScaleBarOverlay;
import android.view.Display;
import android.graphics.Point;
import org.osmdroid.views.overlay.Marker;



public class MainActivity extends AppCompatActivity {

    private static final int REQ_LOCATION = 100;
    private MapView mapView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Configuration.getInstance().load(
                getApplicationContext(),
                PreferenceManager.getDefaultSharedPreferences(getApplicationContext())
        );
        Configuration.getInstance().setUserAgentValue(getPackageName());

        setContentView(R.layout.activity_main);

        mapView = findViewById(R.id.mapView);
        mapView.setTileSource(TileSourceFactory.MAPNIK);
        mapView.setMultiTouchControls(true);
        mapView.setZoomRounding(true);

        IMapController mapController = mapView.getController();
        mapController.setZoom(15.0);

        GeoPoint startPoint = new GeoPoint(55.794229, 37.700772);
        mapController.setCenter(startPoint);

        requestLocationPermissionIfNeeded();
    }
    private MyLocationNewOverlay myLocationOverlay;
    private CompassOverlay compassOverlay;
    private ScaleBarOverlay scaleBarOverlay;
    private void addMarkers() {
        // Маркер 1
        Marker m1 = new Marker(mapView);
        m1.setPosition(new GeoPoint(55.794229, 37.700772));
        m1.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
        m1.setTitle("Точка 1");
        m1.setSubDescription("Описание точки 1");
        mapView.getOverlays().add(m1);

        // Маркер 2
        Marker m2 = new Marker(mapView);
        m2.setPosition(new GeoPoint(55.751574, 37.573856));
        m2.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
        m2.setTitle("Точка 2");
        m2.setSubDescription("Описание точки 2");
        mapView.getOverlays().add(m2);

        // Маркер 3
        Marker m3 = new Marker(mapView);
        m3.setPosition(new GeoPoint(55.758254, 37.601170));
        m3.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
        m3.setTitle("Точка 3");
        m3.setSubDescription("Описание точки 3");
        mapView.getOverlays().add(m3);

        Marker.OnMarkerClickListener listener = (marker, mapView) -> {
            marker.showInfoWindow();
            return true;
        };

        m1.setOnMarkerClickListener(listener);
        m2.setOnMarkerClickListener(listener);
        m3.setOnMarkerClickListener(listener);

        mapView.invalidate();
    }

    private void requestLocationPermissionIfNeeded() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    REQ_LOCATION
            );
        } else {
            setupLocationOverlay();
        }
    }

    private void setupLocationOverlay() {
        myLocationOverlay = new MyLocationNewOverlay(new GpsMyLocationProvider(this), mapView);
        myLocationOverlay.enableMyLocation();
        myLocationOverlay.enableFollowLocation();
        mapView.getOverlays().add(myLocationOverlay);

        compassOverlay = new CompassOverlay(this,
                new InternalCompassOrientationProvider(this), mapView);
        compassOverlay.enableCompass();
        mapView.getOverlays().add(compassOverlay);

        scaleBarOverlay = new ScaleBarOverlay(mapView);
        scaleBarOverlay.setAlignBottom(true);
        scaleBarOverlay.setAlignRight(false);
        mapView.getOverlays().add(scaleBarOverlay);
        mapView.getOverlays().add(scaleBarOverlay);

        mapView.invalidate();
        addMarkers();

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mapView != null) mapView.onResume();
    }

    @Override
    protected void onPause() {
        if (mapView != null) mapView.onPause();
        super.onPause();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQ_LOCATION && grantResults.length > 0
                && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            setupLocationOverlay();
        }
    }
}
