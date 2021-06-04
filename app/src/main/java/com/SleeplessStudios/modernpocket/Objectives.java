package com.SleeplessStudios.modernpocket;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

public class Objectives extends AppCompatActivity {
    private ImageButton createObjective;
    private ImageButton edit;
    private ImageButton filterObjectives;
    private ImageButton sidebar;
    private EditText dueDate;
    private TextView taskName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_objectives);

        createObjective = (ImageButton) findViewById(R.id.create_objective_btn);
        edit = (ImageButton) findViewById(R.id.edit_objectives_btn);
        filterObjectives = (ImageButton) findViewById(R.id.filter_objectives_btn);
        sidebar = (ImageButton) findViewById(R.id.burger_bar_objectives_btn);

        dueDate = (EditText) findViewById(R.id.objective_due_date_txt);

        taskName = (TextView) findViewById(R.id.editable_taskname_txt);

        createObjective.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                openCreateObjective();
            }
        });
    }

    //method to open activity using intent
    public void openCreateObjective()
    {
        Intent intent = new Intent(this, CreateObjective.class);
        startActivity(intent);
    }
}