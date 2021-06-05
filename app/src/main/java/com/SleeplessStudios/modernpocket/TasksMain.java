package com.SleeplessStudios.modernpocket;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
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

import com.google.android.material.navigation.NavigationView;

public class TasksMain extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private ImageButton filter;
    private ImageButton sidebar;
    private ImageButton createTask;
    private DrawerLayout drawer;

    private AlertDialog.Builder dialogBuilder;
    private AlertDialog dialog;
    private EditText taskName;
    private EditText taskDueDate;
    private Button create;
    private Button cancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tasks_main);

        drawer = findViewById(R.id.sidebar_main);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, R.string.sidebar_open, R.string.sidebar_close);
        drawer.addDrawerListener(toggle);
        NavigationView navView = findViewById(R.id.sidebar_view);
        navView.setNavigationItemSelectedListener(this);

        filter = (ImageButton) findViewById(R.id.filter_tasks_btn);
        sidebar = (ImageButton) findViewById(R.id.burgerbar_tasks_btn);
        sidebar.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                openSidebar();
            }
        });

        createTask = (ImageButton) findViewById(R.id.create_task_btn);

        createTask.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                OpenTaskDialog();
            }
        });
    }

    public void OpenTaskDialog()
    {
        dialogBuilder = new AlertDialog.Builder(this);
        final View taskDialogView = getLayoutInflater().inflate(R.layout.dialog_task_create, null);

        dialogBuilder.setView(taskDialogView);
        taskName = (EditText) taskDialogView.findViewById(R.id.editable_task_name_txt);
        taskDueDate = (EditText) taskDialogView.findViewById(R.id.editable_due_date_txt);
        create = (Button) taskDialogView.findViewById(R.id.confirm_create_task_btn);
        cancel = (Button) taskDialogView.findViewById(R.id.cancel_task_btn);

        dialogBuilder.setView(taskDialogView);

        dialog = dialogBuilder.create();
        dialog.show();

        create.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                CreateTask();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                dialog.dismiss();
            }
        });
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId())
        {
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
            case R.id.sidebar_rate:
                openRate();
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
        Intent intent = new Intent(this, EditProfile.class);
        startActivity(intent);
    }
    public void openFavourites()
    {
        Intent intent = new Intent(this, Favourites.class);
        startActivity(intent);
    }
    public void openLent()
    {
        Intent intent = new Intent(this, LentItems.class);
        startActivity(intent);
    }
    public void openSettings()
    {
        Intent intent = new Intent(this, Settings.class);
        startActivity(intent);
    }

    public void Logout()
    {

    }
    //-----------------------TO DO--------------------------------------
    public void openRate()
    {

    }
    public void openHelp()
    {

    }

    public void CreateTask()
    {

    }
}