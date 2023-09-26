package com.example.constraint_layout;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private Button[] buttons;
    private boolean[] buttonStates;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttons = new Button[]{
            findViewById(R.id.button1),
            findViewById(R.id.button2),
            findViewById(R.id.button3),
            findViewById(R.id.button4)
        };

        // button states array
        buttonStates = new boolean[buttons.length];

        for (int i = 0; i < buttons.length; i++) {
            final int buttonIndex = i;
            // event listeners for every button
            buttons[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    toggleButtonBackground(buttons[buttonIndex], buttonIndex);
                }
            });
        }
    }
    private void toggleButtonBackground(Button button, int index) {
        int newColor;
        if (buttonStates[index]) {
            newColor = getResources().getColor(R.color.normalColor);
        } else {
            newColor = getResources().getColor(R.color.pressedColor);
        }
        button.setBackgroundColor(newColor);
        buttonStates[index] = !buttonStates[index];
    }
}