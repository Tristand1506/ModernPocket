package com.SleeplessStudios.modernpocket;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageButton;

public class LentItems extends AppCompatActivity {
    private ImageButton filter;
    private ImageButton sidebar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lent_items);

        filter = (ImageButton) findViewById(R.id.lent_filter_btn);
        sidebar = (ImageButton) findViewById(R.id.burger_bar_lent_btn);
    }
}