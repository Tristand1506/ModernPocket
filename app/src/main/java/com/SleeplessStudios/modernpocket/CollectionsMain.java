package com.SleeplessStudios.modernpocket;

import UtilLib.LoginManager;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.PopupMenu;

import com.google.android.material.navigation.NavigationView;

import UtilLib.DataManager;
import UtilLib.RecyclerViewCollectionAdapter;

public class CollectionsMain extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    private ImageButton createCollection;
    private ImageButton filterCollections;
    private ImageButton sidebar;
    private DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collections_main);
        DataManager.getInstance().InitCollections();
        //DataManager.getInstance().RefreshCollection(this);
        DataManager.getInstance().setActiveCollection(null);
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

        filterCollections.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu dropDownMenu = new PopupMenu(CollectionsMain.this, filterCollections);
                final Menu menu = dropDownMenu.getMenu();

                menu.add(0, 0, 0, "Alphabetical");
                menu.add(0, 1, 0, "Completion");

                dropDownMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case 0:
                                DataManager.getInstance().SortCollectionsByFilter(DataManager.NAME);
                                initRecyclerView();
                                return true;
                            case 1:
                                DataManager.getInstance().SortCollectionsByFilter(DataManager.COMPLEATION);
                                initRecyclerView();
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
        DataManager.getInstance().InitCollections();
        //DataManager.getInstance().RefreshCollection(this);
        DataManager.getInstance().setActiveCollection(null);
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
        LoginManager.getInstance().LogOut();
        startActivity(intent);

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
        RecyclerViewCollectionAdapter adapter = new RecyclerViewCollectionAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

}