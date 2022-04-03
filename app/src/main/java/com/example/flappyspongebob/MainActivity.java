package com.example.flappyspongebob;

import androidx.appcompat.app.AppCompatActivity;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    public static BoardBox boardBox;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        ScreenSize.widthPixels = dm.widthPixels;
        ScreenSize.heightPixels =  dm.heightPixels;
        setContentView(R.layout.activity_main);
        boardBox = findViewById(R.id.boarbox);
        boardBox.setVisibility(View.GONE);
    }
}