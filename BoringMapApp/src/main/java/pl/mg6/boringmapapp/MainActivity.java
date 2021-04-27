package pl.mg6.boringmapapp;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

import com.androidmapsextensions.ClusterOptions;
import com.androidmapsextensions.ClusterOptionsProvider;
import com.androidmapsextensions.ClusteringSettings;
import com.androidmapsextensions.GoogleMap;
import com.androidmapsextensions.Marker;
import com.androidmapsextensions.MarkerOptions;
import com.androidmapsextensions.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.ui.IconGenerator;

import java.util.List;

public class MainActivity extends ActionBarActivity {

    private GoogleMap map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new SupportMapFragment())
                    .commit();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (map == null) {
            SupportMapFragment f = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.container);
            map = f.getExtendedMap();
            if (map != null) {
                setupMap();
            }
        }
    }

    private void setupMap() {
        ClusteringSettings settings = new ClusteringSettings();
        settings.clusterOptionsProvider(new ClusterOptionsProvider() {
            @Override
            public ClusterOptions getClusterOptions(List<Marker> markers) {
                String text = "Trójmiasto";
                if (markers.size() == 2) {
                    text = "Dwumiasto";
                }
                Bitmap bmp = new IconGenerator(MainActivity.this).makeIcon(text);
                BitmapDescriptor icon = BitmapDescriptorFactory.fromBitmap(bmp);
                return new ClusterOptions().icon(icon);
            }
        });
        map.setClustering(settings);
        MarkerOptions options = new MarkerOptions();
        map.addMarker(options.title("Gdańsk").position(new LatLng(54.360, 18.639)));
        map.addMarker(options.title("Gdynia").position(new LatLng(54.520, 18.530)));
        map.addMarker(options.title("Sopot").position(new LatLng(54.439, 18.559)));
    }
}
