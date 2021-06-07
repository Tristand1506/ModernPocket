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
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;

import UtilLib.DataManager;
import UtilLib.RecyclerViewItemAdapter;

public class Items extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    private ImageButton createItem;
    private ImageButton filterItems;
    private ImageButton sidebar;
    private ImageButton editCollection;
    private TextView collectionName;
    private DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_items);
        DataManager.getInstance().refreshItems(this);
        initRecyclerView();
        DataManager.getInstance().setActiveItem(null);
        collectionName = (TextView) findViewById(R.id.editable_coll_txt);
        collectionName.setText(DataManager.getInstance().getActiveCollection().getCollectionName());

        drawer = findViewById(R.id.sidebar_main);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, R.string.sidebar_open, R.string.sidebar_close);
        drawer.addDrawerListener(toggle);
        NavigationView navView = findViewById(R.id.sidebar_view);
        navView.setNavigationItemSelectedListener(this);

        createItem = (ImageButton) findViewById(R.id.create_item_btn);

        createItem.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                openItemCreation();
            }
        });

        filterItems = (ImageButton) findViewById(R.id.filter_items_btn);
        sidebar = (ImageButton) findViewById(R.id.burgerbar_items_btn);
        sidebar.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                openSidebar();
            }
        });

        editCollection = (ImageButton) findViewById(R.id.edit_items);
        editCollection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCollectionEdit();
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        DataManager.getInstance().refreshItems(this);
        initRecyclerView();
        DataManager.getInstance().setActiveItem(null);
        collectionName.setText(DataManager.getInstance().getActiveCollection().getCollectionName());
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

    public void openItemCreation() {
        Intent intent = new Intent(this, CreateItem.class);
        startActivity(intent);
    }

    public void openCollectionEdit() {
        Intent intent = new Intent(this, CreateCollection.class);
        startActivity(intent);
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
        Intent intent = new Intent(this, LandingPage.class);
        startActivity(intent);
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


    private void initRecyclerView(){
        RecyclerView recyclerView = findViewById(R.id.recycler_view_items);
        RecyclerViewItemAdapter adapter = new RecyclerViewItemAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}