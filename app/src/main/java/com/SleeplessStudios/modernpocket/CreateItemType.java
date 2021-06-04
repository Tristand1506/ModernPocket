package com.SleeplessStudios.modernpocket;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageButton;

public class CreateItemType extends AppCompatActivity {
    private ImageButton save;
    private ImageButton discard;
    private EditText itemName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_item_type);

        save = (ImageButton) findViewById(R.id.save_item_type_btn);
        discard = (ImageButton) findViewById(R.id.x_createitem_btn);
        itemName = (EditText) findViewById(R.id.item_name_txt);
    }
}