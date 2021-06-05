package com.SleeplessStudios.modernpocket;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageButton;

import com.google.android.material.navigation.NavigationView;

public class EditProfile extends AppCompatActivity{
    private ImageButton avatar;
    private ImageButton sidebar;
    private EditText email;
    private EditText phone;
    private EditText gender;
    private EditText DOB;
    private DrawerLayout drawer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        drawer = findViewById(R.id.sidebar_main);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, R.string.sidebar_open, R.string.sidebar_close);
        drawer.addDrawerListener(toggle);
        NavigationView navView = findViewById(R.id.sidebar_view);

        avatar = (ImageButton) findViewById(R.id.profile_avatar_btn);
        sidebar = (ImageButton) findViewById(R.id.burger_bar_profile_btn);

        email = (EditText) findViewById(R.id.edit_email_txt);
        phone = (EditText) findViewById(R.id.edit_phone_txt);
        gender = (EditText) findViewById(R.id.edit_gender_txt);
        DOB = (EditText) findViewById(R.id.edit_DOB_txt);
    }
}