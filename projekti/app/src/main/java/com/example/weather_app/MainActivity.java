package com.example.weather_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.hardware.SensorManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import android.view.inputmethod.EditorInfo;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import com.squareup.picasso.Picasso;

public class MainActivity extends AppCompatActivity {
    String OPENWEATHERKEY = "";
    private SensorManager sensorManager;
    private LocationManager locationManager;

    public boolean isMetric;
    public String tempUnit = "";
    public String windUnit = "";
    public String unit = "";
    String cityName;
    String countryName;
    String weather;
    double temperature;
    double feelsLike;
    double wind;
    double humidity;
    String iconCode;
    long sunriseTimestamp;
    long sunsetTimestamp;


    public void setValues() {
        TextView cityNameTextView = findViewById(R.id.cityNameTextView);
        TextView weatherTextView = findViewById(R.id.weatherTextView);
        TextView temperatureTextView = findViewById(R.id.temperatureTextView);
        TextView feelsLikeTextView = findViewById(R.id.feelsLikeTextView);
        TextView windTextView = findViewById(R.id.windTextView);
        TextView sunriseTextView = findViewById(R.id.sunriseTextView);
        TextView sunsetTextView = findViewById(R.id.sunsetTextView);
        TextView humidityTextView = findViewById(R.id.humidityTextView);

        String sunriseTime = convertTimestampToTime(sunriseTimestamp);
        String sunsetTime = convertTimestampToTime(sunsetTimestamp);
        setDatesAndTimes();

        cityNameTextView.setText(cityName + ", " + countryName);
        temperatureTextView.setText("" + Math.round(temperature) + tempUnit);
        feelsLikeTextView.setText(getString(R.string.feels_like) + " " + Math.round(feelsLike) + tempUnit);
        weatherTextView.setText(weather);
        windTextView.setText(getString(R.string.wind_speed) + ": " + Math.round(wind) + " " + windUnit);
        humidityTextView.setText(getString(R.string.humidity) + ": " + humidity + " %");
        sunriseTextView.setText(sunriseTime);
        sunsetTextView.setText(sunsetTime);

        // Construct the URL for the weather icon
        String iconUrl = "https://openweathermap.org/img/w/" + iconCode + ".png";
        ImageView weatherIconImageView = findViewById(R.id.weatherIconImageView);
        Picasso.get().load(iconUrl).into(weatherIconImageView);
    }

    public void changeUnits() {
        if (isMetric) {
            tempUnit = "°C";
            windUnit = "m/s";
            unit = "metric";
            // temperature = (temperature - 32) * 5/9;
            // wind = wind / 2.237;
        } else {
            tempUnit = "°F";
            windUnit = "mph";
            unit = "imperial";
            // temperature = (temperature * 9/5) + 32;
            // wind = wind * 2.237;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText locationEditText = findViewById(R.id.searchText);

        Intent intent = getIntent();
        isMetric = intent.getBooleanExtra("IS_METRIC", true);
        changeUnits();

        if (savedInstanceState != null) {
            cityName = savedInstanceState.getString("WEATHER_CITY");
            countryName =  savedInstanceState.getString("WEATHER_COUNTRY");
            weather = savedInstanceState.getString("WEATHER_WEATHERTYPE");
            temperature = savedInstanceState.getDouble("WEATHER_TEMPERATURE");
            feelsLike = savedInstanceState.getDouble("WEATHER_FEELS_LIKE");
            wind = savedInstanceState.getDouble("WEATHER_WIND");
            humidity = savedInstanceState.getDouble("WEATHER_HUMIDITY");
            iconCode = savedInstanceState.getString("WEATHER_ICON_CODE");
            sunriseTimestamp = savedInstanceState.getLong("WEATHER_SUNRISE");
            sunsetTimestamp = savedInstanceState.getLong("WEATHER_SUNSET");
            setValues();
        }
        // make the search via keyboard press
        locationEditText.setOnEditorActionListener((textView, actionId, keyEvent) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE || actionId == EditorInfo.IME_NULL) {
                getWeatherData(textView);
                return true;
            }
            return false;
        });
    }

    public void getWeatherData2(Double lat, Double lon) {
        String WEATHER_URL = "https://api.openweathermap.org/data/2.5/weather?lat=" + lat + "&lon=" + lon + "&appid=" + OPENWEATHERKEY + "&units=" + unit;
        StringRequest request = new StringRequest(Request.Method.GET, WEATHER_URL, response -> {
            Toast.makeText(this, response, Toast.LENGTH_LONG).show();
            parseWeatherJsonAndUpdateUi(response);
        }, error -> {
            Toast.makeText(this, "Unable to find city or network error. Please try again later.", Toast.LENGTH_LONG)
                    .show();
        });
        // Add to request queue
        Volley.newRequestQueue(this).add(request);
    }

    public void getGPSLocation(View view) {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[] {android.Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 0);
            return;
        }
        locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
        LocationListener locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(@NonNull Location location) {
                double lat = location.getLatitude();
                double lon = location.getLongitude();
                getWeatherData2(lat, lon);
                // Remove location updates after receiving the first update
                locationManager.removeUpdates(this);
            }
        };

        Location currentLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        if (currentLocation != null) {
            double lat = currentLocation.getLatitude();
            double lon = currentLocation.getLongitude();
            getWeatherData2(lat, lon);
            // Remove updates immediately if last known location is available
            locationManager.removeUpdates(locationListener);
        } else {
            // Request location updates
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
        }
    }

    public void setDatesAndTimes() {
        // Get current date and time
        Calendar calendar = Calendar.getInstance();
        Date currentDate = calendar.getTime();

        // Format the date and time
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMMM", Locale.getDefault());
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());

        String formattedDate = dateFormat.format(currentDate);
        String formattedTime = timeFormat.format(currentDate);

        // Display the date and time in a TextView
        TextView dateTimeTextView = findViewById(R.id.dateTimeTextView);
        dateTimeTextView.setText(formattedDate + ", " + formattedTime);
    }

    public void getWeatherData(View view) {
        EditText locationEditText = findViewById(R.id.searchText);
        String city = locationEditText.getText().toString().trim();
        if (city.isEmpty()) {
            Toast.makeText(this, "Please enter a location", Toast.LENGTH_SHORT).show();
            return;
        }
        String WEATHER_URL = "https://api.openweathermap.org/data/2.5/weather?q= " + city + "&appid=" + OPENWEATHERKEY
                + "&units=" + unit;
        StringRequest request = new StringRequest(Request.Method.GET, WEATHER_URL, response -> {
            Toast.makeText(this, response, Toast.LENGTH_LONG).show();
            parseWeatherJsonAndUpdateUi(response);
        }, error -> {
            Toast.makeText(this, "Unable to find city or network error. Please try again later.", Toast.LENGTH_LONG)
                    .show();
        });
        // Add to request queue
        Volley.newRequestQueue(this).add(request);
    }

    public static String convertTimestampToTime(long timestamp) {
        // Convert the timestamp to milliseconds
        Date date = new Date(timestamp * 1000);

        // Format the date and time
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());
        return timeFormat.format(date);
    }

    @SuppressLint("SetTextI18n")
    private void parseWeatherJsonAndUpdateUi(String response) {
        try {
            JSONObject weatherJSON = new JSONObject(response);

            cityName = weatherJSON.getString("name");
            countryName = weatherJSON.getJSONObject("sys").getString("country");
            weather = weatherJSON.getJSONArray("weather").getJSONObject(0).getString("main");
            temperature = weatherJSON.getJSONObject("main").getDouble("temp");
            feelsLike = weatherJSON.getJSONObject("main").getDouble("feels_like");
            wind = weatherJSON.getJSONObject("wind").getDouble("speed");
            humidity =  weatherJSON.getJSONObject("main").getDouble("humidity");
            iconCode = weatherJSON.getJSONArray("weather").getJSONObject(0).getString("icon");
            sunriseTimestamp = weatherJSON.getJSONObject("sys").getLong("sunrise");
            sunsetTimestamp = weatherJSON.getJSONObject("sys").getLong("sunset");

            changeUnits();
            setValues();
        } catch (JSONException e) {
            Toast.makeText(this, "An error occurred:" + e, Toast.LENGTH_LONG).show();
            throw new RuntimeException(e);
        }
    }

    public void openSettings(View view) {
        Intent intent = new Intent(this, SettingsActivity.class);
        intent.putExtra("IS_METRIC", isMetric);
        startActivity(intent);
    }

    // no need to return to settings, return to launcher
    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() == 0) {
            moveTaskToBack(true);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        bundle.putString("WEATHER_CITY", cityName);
        bundle.putString("WEATHER_COUNTRY", countryName);
        bundle.putString("WEATHER_WEATHERTYPE", weather);
        bundle.putDouble("WEATHER_TEMPERATURE", temperature);
        bundle.putDouble("WEATHER_FEELS_LIKE", feelsLike);
        bundle.putDouble("WEATHER_WIND", wind);
        bundle.putDouble("WEATHER_HUMIDITY", humidity);
        bundle.putString("WEATHER_ICON_CODE", iconCode);
        bundle.putLong("WEATHER_SUNRISE", sunriseTimestamp);
        bundle.putLong("WEATHER_SUNSET", sunsetTimestamp);
    }

    @Override
    protected void onRestoreInstanceState(Bundle bundle) {
        super.onRestoreInstanceState(bundle);
        cityName = bundle.getString("WEATHER_CITY");
        countryName =  bundle.getString("WEATHER_COUNTRY");
        weather = bundle.getString("WEATHER_WEATHERTYPE");
        temperature = bundle.getDouble("WEATHER_TEMPERATURE");
        feelsLike = bundle.getDouble("WEATHER_FEELS_LIKE");
        wind = bundle.getDouble("WEATHER_WIND");
        humidity = bundle.getDouble("WEATHER_HUMIDITY");
        iconCode = bundle.getString("WEATHER_ICON_CODE");
        sunriseTimestamp = bundle.getLong("WEATHER_SUNRISE");
        sunsetTimestamp = bundle.getLong("WEATHER_SUNSET");
    }

}