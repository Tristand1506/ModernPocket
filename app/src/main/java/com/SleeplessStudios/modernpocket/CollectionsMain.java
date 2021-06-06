package com.SleeplessStudios.modernpocket;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

import com.google.android.material.navigation.NavigationView;

import UtilLib.DataManager;
import UtilLib.RecylerViewAdapter;

public class CollectionsMain extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    private ImageButton createCollection;
    private ImageButton filterCollections;
    private ImageButton sidebar;
    private DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collections_main);
        DataManager.getInstance().RefreshCollection(this);
        initRecyclerView();

        drawer = findViewById(R.id.sidebar_main);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, R.string.sidebar_open, R.string.sidebar_close);
        drawer.addDrawerListener(toggle);
        NavigationView navView = findViewById(R.id.sidebar_view);
        navView.setNavigationItemSelectedListener(this);

        createCollection = (ImageButton) findViewById(R.id.create_coll_btn);
        createCollection.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                DataManager.getInstance().setActiveCollection(null);
                openCreateCollection();
            }
        });

        filterCollections = (ImageButton) findViewById(R.id.filter_coll_btn);

        sidebar = (ImageButton) findViewById(R.id.burgerbar_coll_btn);
        sidebar.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                openSidebar();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        DataManager.getInstance().RefreshCollection(this);
        initRecyclerView();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
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

    //method to open activity using intent
    public void openCreateCollection()
    {
        Intent intent = new Intent(this, CreateCollection.class);
        startActivity(intent);
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
    public void openCollections()
    {
        Intent intent = new Intent(this, CollectionsMain.class);
        startActivity(intent);
    }
    public void openTasks()
    {
        Intent intent = new Intent(this, TasksMain.class);
        startActivity(intent);
    }
    public void openLens()
    {
        Intent intent = new Intent(this, ObjectLens.class);
        startActivity(intent);
    }

    public void Logout()
    {

    }
    //-----------------------TO DO--------------------------------------
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

    private void initRecyclerView(){
        RecyclerView recyclerView = findViewById(R.id.recycler_view_coll);
        RecylerViewAdapter adapter = new RecylerViewAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

}