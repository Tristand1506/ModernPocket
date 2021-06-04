package com.SleeplessStudios.modernpocket;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class Settings extends AppCompatActivity {
    private ImageButton account;
    private ImageButton notifs;
    private ImageButton privacy;
    private ImageButton help;
    private ImageButton about;
    private ImageButton filter;
    private ImageButton sidebar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        account = (ImageButton) findViewById(R.id.settings_acc_btn);
        notifs = (ImageButton) findViewById(R.id.settings_notif_btn);
        privacy = (ImageButton) findViewById(R.id.settings_privacy_btn);
        help = (ImageButton) findViewById(R.id.settings_help_btn);
        about = (ImageButton) findViewById(R.id.settings_about_btn);
        filter = (ImageButton) findViewById(R.id.filter_settings_btn);
        sidebar = (ImageButton) findViewById(R.id.burger_bar_settings_btn);


        //listeners
        account.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                openProfile();
            }
        });
    }

    public void openProfile()
    {
        Intent intent = new Intent(this, EditProfile.class);
        startActivity(intent);
    }
}