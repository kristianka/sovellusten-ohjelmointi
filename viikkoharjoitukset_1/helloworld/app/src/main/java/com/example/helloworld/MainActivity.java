package com.example.helloworld;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private TextView mainText;
    private boolean hasBeenPressed = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // App's header
        TextView headerText = findViewById(R.id.headerText);
        headerText.setText(R.string.header);
        // Main body
        mainText = findViewById(R.id.mainText);
        mainText.setText(R.string.button_off);
        // Button text
        TextView buttonText = findViewById(R.id.buttonText);
        buttonText.setText(R.string.buttonText);
    }
    public void buttonClicked(View view) {
        hasBeenPressed = !hasBeenPressed;
        if (hasBeenPressed) {
            mainText.setText(R.string.button_on);
        } else {
            mainText.setText(R.string.button_off);
        }
    }
}