package com.example.aktiviteetin_tallentaminen;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private String text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState != null) {
            text = savedInstanceState.getString("TEXTBOX", "Type here");
        }
        EditText helloText = findViewById(R.id.editTextTextMultiLine);
        helloText.setText(text);
        Log.d("HELLO","Created");
    }

    public void buttonClicked(View view) {
        EditText helloText = findViewById(R.id.editTextTextMultiLine);
        text = helloText.getText().toString();
    }

    protected void onStart() {
        super.onStart();
        Log.d("HELLO","Started");
    }

    protected void onPause() {
        super.onPause();
        Log.d("HELLO","Paused");
    }

    protected void onResume() {
        super.onResume();
        Log.d("HELLO","Resumed");
    }

    protected void onStop() {
        super.onStop();
        Log.d("HELLO","Stopped");
    }

    protected void onDestroy() {
        super.onDestroy();
        Log.d("HELLO","Destroyed");
    }

    @Override
    protected void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        Log.d("HELLO","Saving instance state");
        bundle.putString("TEXTBOX", text);
    }

    @Override
    protected void onRestoreInstanceState(Bundle bundle) {
        super.onRestoreInstanceState(bundle);
        Log.d("HELLO","Restoring instance state");
        text = bundle.getString("TEXTBOX", "Enter some text here");
    }
}