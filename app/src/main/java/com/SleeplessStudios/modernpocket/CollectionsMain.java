package com.SleeplessStudios.modernpocket;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class CollectionsMain extends AppCompatActivity {
    private ImageButton createCollection;
    private ImageButton filterCollections;
    private ImageButton sidebar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collections_main);

        createCollection = (ImageButton) findViewById(R.id.create_coll_btn);
        createCollection.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                openCreateCollection();
            }
        });

        filterCollections = (ImageButton) findViewById(R.id.filter_coll_btn);
        sidebar = (ImageButton) findViewById(R.id.burgerbar_coll_btn);
    }

    //method to open activity using intent
    public void openCreateCollection()
    {
        Intent intent = new Intent(this, CreateCollection.class);
        startActivity(intent);
    }
}