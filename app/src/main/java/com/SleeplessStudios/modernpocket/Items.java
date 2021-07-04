package com.SleeplessStudios.modernpocket;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.PopupMenu;

import com.google.android.material.navigation.NavigationView;

import UtilLib.DataManager;
import UtilLib.RecyclerViewItemAdapter;

public class Items extends AppCompatActivity {
    private ImageButton createItem;
    private ImageButton filterItems;

    private ImageButton editCollection;
    private ImageButton pieChart;
    private TextView collectionName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_items);
        DataManager.getInstance().refreshItems(this);
        initRecyclerView();
        DataManager.getInstance().setActiveItem(null);
        collectionName = (TextView) findViewById(R.id.editable_coll_txt);
        collectionName.setText(DataManager.getInstance().getActiveCollection().getCollectionName());

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
        filterItems.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu dropDownMenu = new PopupMenu(Items.this, filterItems);
                final Menu menu = dropDownMenu.getMenu();

                menu.add(0, 0, 0, "Item 1");
                menu.add(0, 1, 0, "Item 2");

                dropDownMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case 0:
                                // item ID 0 was clicked
                                return true;
                            case 1:
                                // item ID 1 was clicked
                                return true;
                        }
                        return false;
                    }
                });
                dropDownMenu.show();
            }
        });

        pieChart = (ImageButton) findViewById(R.id.chart_btn);
        pieChart.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                openPieChart();
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


    public void openItemCreation() {
        Intent intent = new Intent(this, CreateItem.class);
        startActivity(intent);
    }

    public void openCollectionEdit() {
        Intent intent = new Intent(this, CreateCollection.class);
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

    private void initRecyclerView(){
        RecyclerView recyclerView = findViewById(R.id.recycler_view_items);
        RecyclerViewItemAdapter adapter = new RecyclerViewItemAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void openPieChart()
    {
        Intent intent = new Intent(this, Chart.class);
        startActivity(intent);
    }
}