package com.example.elinkaari;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView helloText = findViewById(R.id.helloTextView);
        helloText.setText("Hello from code");
        Log.d("HELLO","Created");
    }

    public void buttonClicked(View view) {
        TextView helloText = findViewById(R.id.helloTextView);
        helloText.setText("Button pressed");
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
}