package com.example.valuuttamuunnin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class settings extends AppCompatActivity {
    private float homeCurrency = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Intent intent = getIntent();
        Float conversionRate = intent.getFloatExtra("CONVERSION_RATE", 1);

        EditText conversionRateInput = findViewById(R.id.conversionRateInput);
        conversionRateInput.setText(conversionRate.toString());

        homeCurrency = intent.getFloatExtra("HOME_CURRENCY", 0);
    }

    public void backToConverter(View view) {
        Intent mainIntent = new Intent(this, MainActivity.class);
        EditText conversionRateInput = findViewById(R.id.conversionRateInput);

        String valueInput = conversionRateInput.getText().toString();
        float conversionRate = Float.parseFloat(valueInput);

        mainIntent.putExtra("CONVERSION_RATE", conversionRate);
        mainIntent.putExtra("HOME_CURRENCY", homeCurrency);
        startActivity(mainIntent);
    }
}