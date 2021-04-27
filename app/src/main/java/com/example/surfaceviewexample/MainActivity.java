package com.example.surfaceviewexample;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
    DrawView mDrawView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mDrawView = findViewById(R.id.drawView);
    }
    /**
     * Pauses game when activity is paused.
     */
    @Override
    protected void onPause() {
        super.onPause();
        mDrawView.pause();
    }

    /**
     * Resumes game when activity is resumed.
     */
    @Override
    protected void onResume() {
        super.onResume();
        mDrawView.resume();
    }
}