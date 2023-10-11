package com.example.weatherapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void getWeatherData(View view) {
        // Volley library
        // Replace <ID> with your own id from openweathermap api page
        String WEATHER_URL = "https://api.openweathermap.org/data/2.5/weather?q=tampere&appid=<ID>&units=metric";
        StringRequest request = new StringRequest(Request.Method.GET, WEATHER_URL, response -> {
            // Säätiedot haettu onnistuneesti
            Toast.makeText(this, response, Toast.LENGTH_LONG).show();
            parseWeatherJsonAndUpdateUi(response);
        }, error -> {
            // Verkkovirhe yms.
            Toast.makeText(this, "Verkkovirhe!", Toast.LENGTH_LONG).show();
        });
        // Lähetetään request Volleylla, lisätään request requestqueueen
        Volley.newRequestQueue(this).add(request);
    }

    private void parseWeatherJsonAndUpdateUi(String response) {
        try {
            JSONObject weatherJSON = new JSONObject(response);
            String cityName = weatherJSON.getString("name");
            String weather = weatherJSON.getJSONArray("weather").getJSONObject(0).getString("main");
            double temperature = weatherJSON.getJSONObject("main").getDouble("temp");
            double wind = weatherJSON.getJSONObject("wind").getDouble("speed");

            TextView cityNameTextView = findViewById(R.id.cityNameTextView);
            TextView weatherTextView = findViewById(R.id.weatherTextView);
            TextView temperatureTextView = findViewById(R.id.temperatureTextView);
            TextView windTextView = findViewById(R.id.windTextView);

            cityNameTextView.setText(cityName);
            weatherTextView.setText(weather);
            temperatureTextView.setText("" + temperature + "C");
            windTextView.setText("" + wind + " m/s");
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    public void openWeatherOnBrowser(View view) {
        String openWeatherTampere = "https://openweathermap.org/city/634963";
        Uri webpage = Uri.parse(openWeatherTampere);
        Intent intent = new Intent(Intent.ACTION_VIEW, webpage);
        // Tarkastetaan onko laitteella tämän intentin toteuttava palvelu
        // tässä tilanteessa onko webbi selainta
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        } else {
            Toast.makeText(this, "Missing browser", Toast.LENGTH_LONG).show();
        }
    }
}