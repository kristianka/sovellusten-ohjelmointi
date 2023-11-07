package com.example.deviceapi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private SensorManager sensorManager;
    private LocationManager locationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void startSensors(View view) {
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        List<Sensor> sensors = sensorManager.getSensorList(Sensor.TYPE_ALL);
        for (Sensor s : sensors) {
            Toast.makeText(this, s.getName(), Toast.LENGTH_LONG).show();
            Log.d("SENSOR_PRINT", s.getName() + " by " + s.getVendor());
        }
        // Rekisteröidään kuuntelemaan kiihtyvyysanturin x,y,z lukemia
        Sensor accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        if(accelerometer != null) {
            sensorManager.registerListener(new SensorEventListener() {
                @Override
                public void onSensorChanged(SensorEvent event) {
                    // Sensorin arvo muuttui. Päivitetään käyttöliittymään
                    float x,y,z;
                    x = event.values[0];
                    y = event.values[1];
                    z = event.values[2];
                    TextView sensorTextView = findViewById(R.id.sensorTextView);
                    sensorTextView.setText("X: " + x + "\nY: " + y + "\nZ: " + z);
                }

                @Override
                public void onAccuracyChanged(Sensor sensor, int accuracy) {

                }
            }, accelerometer, SensorManager.SENSOR_DELAY_UI);
        }
    }

    public void startGPS(View view) {
        // Katsotaan ensiksi onko oikeudet sijaintiin ja jos ei, pyydetään se
        // Tarvitaan tälläinen aina dangerous permissioneihin eli vaarallisiin oikeuksiin kuten gps, kalenteri, puhelut
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // Ei oikeuksia, joten pyydetään oikeudet
            requestPermissions(new String[] {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 0);
            return;
        }
        locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
        Location currentLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
 
        // Jos on oikeudet, luetaan ja rekisteröidään kuuntelemaan lat&lng arvoja
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, new LocationListener() {
            @Override
            public void onLocationChanged(@NonNull Location location) {
                double lat = location.getLatitude();
                double lon = location.getLongitude();
                TextView locationTextView = findViewById(R.id.locationTextView);
                locationTextView.setText("Lat: " + lat + "\nLon: " + lon);
                Log.d("GPS_PRINT", "Lat: " + lat + " Lon: " + lon);
            }
        });

    }


}