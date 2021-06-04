package com.SleeplessStudios.modernpocket;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageButton;

public class Favourites extends AppCompatActivity {
    private ImageButton filter;
    private ImageButton sidebar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourites);

        filter = (ImageButton) findViewById(R.id.fav_filter_btn);
        sidebar = (ImageButton) findViewById(R.id.burger_bar_fav_btn);
    }
}