package com.example.valuuttamuunnin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private float conversionRate = 1.0f;
    private float homeCurrencyAmount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = getIntent();
        conversionRate = intent.getFloatExtra("CONVERSION_RATE", 1.0f);
        homeCurrencyAmount = intent.getFloatExtra("HOME_CURRENCY", 0);

        EditText homeCurrency = findViewById(R.id.homeCurrencyEditText);
        homeCurrency.setText(String.valueOf(homeCurrencyAmount));
    }

    public void openSettings(View view) {
        Intent intent = new Intent(this, settings.class);
        intent.putExtra("CONVERSION_RATE", conversionRate);
        intent.putExtra("HOME_CURRENCY", homeCurrencyAmount);
        startActivity(intent);
    }

    public void convertCurrency(View view) {
        // Luetaan source kentästä luku, tehdään muunnos ja kirjoitetaan destination
        // kenttään valuuttamuunnos
        EditText homeCurrencyEditText = findViewById(R.id.homeCurrencyEditText);
        EditText destinationCurrencyEditText = findViewById(R.id.destinationCurrencyEditText);

        // Luetaan lähdevaluutta tekstikentästä ja muunnetaan floatiksi
        String homeCurrencyInput = homeCurrencyEditText.getText().toString();
        float homeCurrencyAmount = Float.parseFloat(homeCurrencyInput);

        // Valuuttamuunnos
        float destinationCurrency = homeCurrencyAmount * conversionRate;
        // Kirjoitetaan tulos kohdevaluutta -kenttään
        destinationCurrencyEditText.setText(String.valueOf(destinationCurrency));
    }
}