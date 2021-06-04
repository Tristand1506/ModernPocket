package com.SleeplessStudios.modernpocket;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageButton;

public class CreateObjective extends AppCompatActivity {
    private ImageButton save;
    private ImageButton discard;
    private EditText editName;
    private EditText editDesc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_objective);

        save = (ImageButton) findViewById(R.id.save_create_objective_btn);
        discard = (ImageButton) findViewById(R.id.x_create_objective_btn);

        editName = (EditText) findViewById(R.id.objective_name_txt);
        editDesc = (EditText) findViewById(R.id.objective_desc_txt);
    }
}