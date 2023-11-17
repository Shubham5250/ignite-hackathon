


package com.example.pharmacyio.Screens;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.pharmacyio.R;

public class splash_screen extends AppCompatActivity {

    private static final long SPLASH_DURATION = 2000; // 2 seconds

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        // Use a Handler to delay the transition to the main activity
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // Start the main activity after the specified duration
                startActivity(new Intent(splash_screen.this, authentication.class));
                finish();
            }
        }, SPLASH_DURATION);
    }
}