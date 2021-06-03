package com.SleeplessStudios.modernpocket;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity {
    private ImageButton collections;
    private ImageButton tasks;
    private ImageButton googleLens;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
}