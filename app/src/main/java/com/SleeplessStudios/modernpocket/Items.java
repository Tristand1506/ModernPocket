package com.SleeplessStudios.modernpocket;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

public class Items extends AppCompatActivity {
    private ImageButton createItem;
    private ImageButton filterItems;
    private ImageButton sidebar;
    private ImageButton editItems;
    private TextView collectionName;

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

        filterItems = (ImageButton) findViewById(R.id.filter_items_btn);
        sidebar = (ImageButton) findViewById(R.id.burgerbar_items_btn);
        editItems = (ImageButton) findViewById(R.id.edit_items);
        collectionName = (TextView) findViewById(R.id.editable_coll_txt);
    }

    public void openItemCreation()
    {
        Intent intent = new Intent(this, CreateItem.class);
        startActivity(intent);
    }
}