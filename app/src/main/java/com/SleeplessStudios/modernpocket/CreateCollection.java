package com.SleeplessStudios.modernpocket;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

public class CreateCollection extends AppCompatActivity {
    private Spinner spinnerChooseItemType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_collection);

        spinnerChooseItemType = findViewById(R.id.chooseitemtype_spinner);
        //array
        String[] itemTypeList = getResources().getStringArray(R.array.item_types);
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, itemTypeList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerChooseItemType.setAdapter(adapter);

    }
}