package com.SleeplessStudios.modernpocket;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class Items extends AppCompatActivity {
    private ImageButton createItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_items);

        createItem = (ImageButton) findViewById(R.id.create_item_btn);
        createItem.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                openItemCreation();
            }
        });
    }

    public void openItemCreation()
    {
        Intent intent = new Intent(this, CreateItem.class);
        startActivity(intent);
    }
}