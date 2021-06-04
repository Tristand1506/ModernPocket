package com.SleeplessStudios.modernpocket;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class CreateCollection extends AppCompatActivity {
    private ImageButton saveCollection;
    private ImageButton discardCollection;
    private ImageButton collectionImage;
    private EditText collName;
    private EditText collDescription;
    private TextView topCollectionName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_collection);

        saveCollection = (ImageButton) findViewById(R.id.check_btn);
        discardCollection = (ImageButton) findViewById(R.id.x_btn);
        collectionImage = (ImageButton) findViewById(R.id.coll_icon_btn);
        collName = (EditText) findViewById(R.id.coll_name_txt);
        collDescription = (EditText) findViewById(R.id.coll_desc_txt);
        topCollectionName = (TextView) findViewById(R.id.editable_coll_creation_txt);

    }
}