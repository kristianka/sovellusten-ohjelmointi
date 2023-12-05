package com.example.weather_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Switch;

public class SettingsActivity extends AppCompatActivity {

    public boolean isMetric;
    public boolean isMetricOnEntrance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Switch isMetricToggle = findViewById(R.id.isMetricToggle);
        Intent intent = getIntent();

        if (savedInstanceState != null) {
            isMetric = savedInstanceState.getBoolean("IS_METRIC", true);
            isMetricToggle.setChecked(isMetric);
        } else {
            isMetric = intent.getBooleanExtra("IS_METRIC", true);
            isMetricToggle.setChecked(isMetric);
        }

        isMetricOnEntrance = isMetric;
    }

    public void toggleMetric(View view) {
        Switch isMetricToggle = findViewById(R.id.isMetricToggle);
        isMetric = isMetricToggle.isChecked();
    }

    public void back(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("IS_METRIC", isMetric);
        if (isMetricOnEntrance == isMetric) {
            finish();
            return;
        }
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("IS_METRIC", isMetric);
        if (isMetricOnEntrance == isMetric) {
            finish();
            return;
        }
        startActivity(intent);
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstance) {
        super.onSaveInstanceState(savedInstance);
        savedInstance.putBoolean("IS_METRIC", isMetric);
    }

}