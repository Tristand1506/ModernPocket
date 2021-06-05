package com.SleeplessStudios.modernpocket;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity {
    private ImageButton collections;
    private ImageButton tasks;
    private ImageButton googleLens;
    private ImageButton sidebar;
    private DrawerLayout drawer;

    private Button profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawer = findViewById(R.id.sidebar_main);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, R.string.sidebar_open, R.string.sidebar_close);
        drawer.addDrawerListener(toggle);

        sidebar = (ImageButton) findViewById(R.id.burger_bar_main_btn);
        sidebar.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                openSidebar();
            }
        });

        //button listener
        collections = (ImageButton) findViewById(R.id.collections_btn);
        collections.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                openCollections();
            }
        });

        //button listener
        tasks = (ImageButton) findViewById(R.id.tasks_btn);
        tasks.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                openTasks();
            }
        });

        //button listener
        googleLens = (ImageButton) findViewById(R.id.objectlens_btn);
        googleLens.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                openLens();
            }
        });

    }

    @Override
    public void onBackPressed()
    {
        finishAffinity();
    }

    public void openCollections()
    {
        Intent intent = new Intent(this, CollectionsMain.class);
        startActivity(intent);
    }
    public void openTasks()
    {
        Intent intent = new Intent(this, TasksMain.class);
        startActivity(intent);
    }
    public void openLens()
    {
        Intent intent = new Intent(this, ObjectLens.class);
        startActivity(intent);
    }
    public void openSidebar()
    {
            drawer.openDrawer(GravityCompat.END);
    }

    public void openProfile()
    {
        Intent intent = new Intent(this, EditProfile.class);
        startActivity(intent);
    }
}