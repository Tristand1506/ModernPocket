package com.SleeplessStudios.modernpocket;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import ObjectLib.Collectible;
import ObjectLib.Objective;
import UtilLib.DataManager;

public class CreateObjective extends AppCompatActivity {
    private ImageButton save;
    private ImageButton discard;
    private EditText name;
    private EditText description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_objective);

        save = (ImageButton) findViewById(R.id.save_create_objective_btn);
        discard = (ImageButton) findViewById(R.id.x_create_objective_btn);

        name = (EditText) findViewById(R.id.objective_name_txt);
        description = (EditText) findViewById(R.id.objective_desc_txt);

        if (DataManager.getInstance().getActiveObjective()!=null){
            //System.out.println("Current Account: "+ LoginManager.getInstance().getActiveUser().getUsername());
            PopulateFields(DataManager.getInstance().getActiveObjective());
        }

        save.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Objective in = new Objective(name.getText().toString(), description.getText().toString());
                DataManager.getInstance().AddOrUpdateObjective(in);
                DataManager.getInstance().RefreshActiveTask();
                backTotask();
            }
        });
    }

    private void backTotask() {
        finish();
    }

    private void PopulateFields(Objective activeObjective) {
        name.setText(activeObjective.getObjectiveName());
        description.setText(activeObjective.getDescription());
    }
}