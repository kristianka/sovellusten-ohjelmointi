package com.example.weather_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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

import android.view.inputmethod.EditorInfo;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import com.squareup.picasso.Picasso;

// app icon by <a href="https://www.flaticon.com/free-icons/wind" title="wind icons">Wind icons created by umartvurdu - Flaticon</a>
public class MainActivity extends AppCompatActivity {
    String OPENWEATHERKEY = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText locationEditText = findViewById(R.id.searchText);

        // Set up the OnEditorActionListener to listen for the "OK" button press
        locationEditText.setOnEditorActionListener((textView, actionId, keyEvent) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE || actionId == EditorInfo.IME_NULL) {
                // User pressed the "OK" button on the keyboard, initiate the search
                getWeatherData(textView);
                return true;
            }
            return false;
        });
    }

    public void getDateAndTime() {
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
                + "&units=metric";
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

    private void parseWeatherJsonAndUpdateUi(String response) {
        try {
            JSONObject weatherJSON = new JSONObject(response);
            String cityName = weatherJSON.getString("name");
            String weather = weatherJSON.getJSONArray("weather").getJSONObject(0).getString("main");
            double temperature = weatherJSON.getJSONObject("main").getDouble("temp");
            double feelsLike = weatherJSON.getJSONObject("main").getDouble("feels_like");
            double wind = weatherJSON.getJSONObject("wind").getDouble("speed");
            String iconCode = weatherJSON.getJSONArray("weather").getJSONObject(0).getString("icon");

            TextView weatherTextView = findViewById(R.id.weatherTextView);
            TextView temperatureTextView = findViewById(R.id.temperatureTextView);
            TextView feelsLikeTextView = findViewById(R.id.feelsLikeTextView);
            TextView windTextView = findViewById(R.id.windTextView);

            getDateAndTime();
            temperatureTextView.setText("" + Math.round(temperature) + "°C");
            feelsLikeTextView.setText("Feels like " + Math.round(feelsLike) + "°C");
            weatherTextView.setText(weather);
            windTextView.setText("" + Math.round(wind) + " m/s");

            // Construct the URL for the weather icon
            String iconUrl = "https://openweathermap.org/img/w/" + iconCode + ".png";
            ImageView weatherIconImageView = findViewById(R.id.weatherIconImageView);
            Picasso.get().load(iconUrl).into(weatherIconImageView);
            Log.d("MAIN", Uri.parse(iconUrl).toString());
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    public void openSettings(View view) {
        Intent intent = new Intent(this, SettingsActivity.class);
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

}