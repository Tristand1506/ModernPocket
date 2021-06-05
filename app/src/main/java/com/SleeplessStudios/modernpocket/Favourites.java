package com.SleeplessStudios.modernpocket;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Bundle;
import android.widget.ImageButton;

import com.google.android.material.navigation.NavigationView;

public class Favourites extends AppCompatActivity{
    private ImageButton filter;
    private ImageButton sidebar;
    private DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourites);

        filter = (ImageButton) findViewById(R.id.fav_filter_btn);
        sidebar = (ImageButton) findViewById(R.id.burger_bar_fav_btn);
    }
}