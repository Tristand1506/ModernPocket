package com.SleeplessStudios.modernpocket;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import ObjectLib.UserAcount;
import UtilLib.LoginManager;

public class EditProfile extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private ImageButton avatar;
    private ImageButton sidebar;
    private Button save;
    private TextView email;
    private TextView user;
    private EditText phone;
    private EditText gender;
    private DrawerLayout drawer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LoginManager.getInstance().initUsers();
        setContentView(R.layout.activity_edit_profile);

        drawer = findViewById(R.id.sidebar_main);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, R.string.sidebar_open, R.string.sidebar_close);
        drawer.addDrawerListener(toggle);
        NavigationView navView = findViewById(R.id.sidebar_view);
        navView.setNavigationItemSelectedListener(this);

        avatar = (ImageButton) findViewById(R.id.profile_avatar_btn);
        sidebar = (ImageButton) findViewById(R.id.burger_bar_profile_btn);
        sidebar.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                openSidebar();
            }
        });

        email = findViewById(R.id.edit_email_txt);
        phone = (EditText) findViewById(R.id.edit_phone_txt);
        gender = (EditText) findViewById(R.id.edit_gender_txt);
        user = findViewById(R.id.profile_username_txt);
        save = (Button) findViewById(R.id.save_profile_btn);

        user.setText(LoginManager.getInstance().getAccountFromEmail(LoginManager.getActiveUser().getEmail()).getUsername());
        email.setText(LoginManager.getInstance().getAccountFromEmail(LoginManager.getActiveUser().getEmail()).getEmail());
        phone.setText(LoginManager.getInstance().getAccountFromEmail(LoginManager.getActiveUser().getEmail()).getPhone());
        gender.setText(LoginManager.getInstance().getAccountFromEmail(LoginManager.getActiveUser().getEmail()).getGender());

        save.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                UserAcount in = new UserAcount(LoginManager.getInstance().getAccountFromEmail( LoginManager.getActiveUser().getEmail()).getUsername(),LoginManager.getActiveUser().getEmail(),phone.getText().toString(),gender.getText().toString());
                LoginManager.getInstance().UpdateUserData(in);
                Toast.makeText(EditProfile.this, "Profile Updated",
                        Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId())
        {
            case R.id.sidebar_collections:
                openCollections();
                break;
            case R.id.sidebar_tasks:
                openTasks();
                break;
            case R.id.sidebar_lens:
                openLens();
                break;
            case R.id.sidebar_profile:
                openProfile();
                break;
            case R.id.sidebar_fav:
                openFavourites();
                break;
            case R.id.sidebar_lent:
                openLent();
                break;
            case R.id.sidebar_settings:
                openSettings();
                break;
            case R.id.sidebar_help:
                openHelp();
                break;
            case R.id.sidebar_logout:
                Logout();
                break;
        }
        return true;
    }
    public void openSidebar()
    {
        drawer.openDrawer(GravityCompat.END);
    }

    public void openProfile()
    {
        drawer.closeDrawer(GravityCompat.END);
        Intent intent = new Intent(this, EditProfile.class);
        startActivity(intent);
    }
    public void openFavourites()
    {
        drawer.closeDrawer(GravityCompat.END);
        Intent intent = new Intent(this, Favourites.class);
        startActivity(intent);
    }
    public void openLent()
    {
        drawer.closeDrawer(GravityCompat.END);
        Intent intent = new Intent(this, LentItems.class);
        startActivity(intent);
    }
    public void openSettings()
    {
        drawer.closeDrawer(GravityCompat.END);
        Intent intent = new Intent(this, Settings.class);
        startActivity(intent);
    }
    public void openCollections()
    {
        drawer.closeDrawer(GravityCompat.END);
        Intent intent = new Intent(this, CollectionsMain.class);
        startActivity(intent);
    }
    public void openTasks()
    {
        drawer.closeDrawer(GravityCompat.END);
        Intent intent = new Intent(this, TasksMain.class);
        startActivity(intent);
    }
    public void openLens()
    {
        drawer.closeDrawer(GravityCompat.END);
        Intent intent = new Intent(this, ObjectLens.class);
        startActivity(intent);
    }

    public void Logout()
    {
        LoginManager.getInstance().LogOut();
    }
    //-----------------------TO DO--------------------------------------
    public void openRate()
    {

    }
    public void openHelp()
    {

    }
    @Override
    public void onBackPressed()
    {
        if (drawer.isDrawerOpen(GravityCompat.END))
        {
            drawer.closeDrawer(GravityCompat.END);
        }
        else {
            super.onBackPressed();
        }
    }
}