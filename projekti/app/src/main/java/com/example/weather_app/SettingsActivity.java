package com.example.weather_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Switch;

public class SettingsActivity extends AppCompatActivity {

    public boolean isMetric;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Intent intent = getIntent();
        isMetric = intent.getBooleanExtra("IS_METRIC", true);
        Switch isMetricToggle = findViewById(R.id.isMetricToggle);
        isMetricToggle.setChecked(isMetric);
    }

    public void toggleMetric(View view) {
        Switch isMetricToggle = findViewById(R.id.isMetricToggle);
        isMetric = isMetricToggle.isChecked();
    }

    public void back(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("IS_METRIC", isMetric);
        startActivity(intent);
    }
}