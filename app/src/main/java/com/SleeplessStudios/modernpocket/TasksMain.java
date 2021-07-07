package com.SleeplessStudios.modernpocket;

import ObjectLib.ItemCollection;
import ObjectLib.Task;
import UtilLib.DataManager;
import UtilLib.RecyclerViewCollectionAdapter;
import UtilLib.RecyclerViewTaskAdapter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.PopupMenu;

import com.google.android.material.navigation.NavigationView;

public class TasksMain extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private ImageButton sidebar;
    private ImageButton createTask;
    private DrawerLayout drawer;

    private AlertDialog.Builder dialogBuilder;
    private AlertDialog dialog;
    private EditText taskName;
    private EditText taskDueDate;
    private ImageButton filter;
    private Button create;
    private Button cancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tasks_main);
        DataManager.getInstance().InitTasks();
        //DataManager.getInstance().RefreshCollection(this);
        //DataManager.getInstance().setActiveCollection(null);
        initRecyclerView();
        DataManager.getInstance().setActiveTask(null);
        drawer = findViewById(R.id.sidebar_main);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, R.string.sidebar_open, R.string.sidebar_close);
        drawer.addDrawerListener(toggle);
        NavigationView navView = findViewById(R.id.sidebar_view);
        navView.setNavigationItemSelectedListener(this);

        //if (DataManager.getInstance().getActiveCollection() != null) {
            //PopulateFields(DataManager.getInstance().getActiveCollection());
        //}

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

        filter = (ImageButton) findViewById(R.id.filter_tasks_btn);
        filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu dropDownMenu = new PopupMenu(TasksMain.this, filter);
                final Menu menu = dropDownMenu.getMenu();

                menu.add(0, 0, 0, "Alphabetical");
                menu.add(0, 1, 0, "Completion");

                dropDownMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case 0:
                                // alphabetical was clicked
                                return true;
                            case 1:
                                // completion was clicked
                                return true;
                        }
                        return false;
                    }
                });
                dropDownMenu.show();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        DataManager.getInstance().InitTasks();
        //DataManager.getInstance().RefreshCollection(this);
        DataManager.getInstance().setActiveTask(null);
        initRecyclerView();
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
        if (drawer.isDrawerOpen(GravityCompat.END))
        {
            drawer.closeDrawer(GravityCompat.END);
        }
        Intent intent = new Intent(this, EditProfile.class);
        startActivity(intent);
    }
    public void openFavourites()
    {
        if (drawer.isDrawerOpen(GravityCompat.END))
        {
            drawer.closeDrawer(GravityCompat.END);
        }
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
        Intent intent = new Intent(this, LandingPage.class);
        startActivity(intent);
    }
    //-----------------------TO DO--------------------------------------
    public void openHelp()
    {

    }
    public void CreateTask()
    {
        Task save = new Task(taskName.getText().toString(), taskDueDate.getText().toString());
        DataManager.getInstance().AddOrUpdateTask(save);
        initRecyclerView();
        //DataManager.getInstance().RefreshCollection(getParent());
        dialog.dismiss();
    }

    private void PopulateFields(ItemCollection activeCollection) {
        taskName.setText(activeCollection.getCollectionName());
        taskDueDate.setText(activeCollection.getDescription());
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

    private void initRecyclerView(){
        RecyclerView recyclerView = findViewById(R.id.recycler_view_tasks);
        RecyclerViewTaskAdapter adapter = new RecyclerViewTaskAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}