package com.SleeplessStudios.modernpocket;

import ObjectLib.Collectible;
import UtilLib.LoginManager;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
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
import android.widget.TextView;

import java.util.HashMap;
import java.util.Hashtable;

import UtilLib.DataManager;
import UtilLib.RecyclerViewItemAdapter;

public class Items extends AppCompatActivity {
    private ImageButton createItem;
    private ImageButton filterItems;
    private ImageButton editCollection;
    private ImageButton pieChart;
    private TextView collectionName;

    Chart chart = new Chart();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_items);

        System.out.println("Chekking Active Item\n" + DataManager.getInstance().getActiveItem() );
        initRecyclerView();
        DataManager.getInstance().setActiveItem(null);
        collectionName = findViewById(R.id.editable_coll_txt);
        collectionName.setText(DataManager.getInstance().getActiveCollection().getCollectionName());

        createItem = findViewById(R.id.create_item_btn);

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

                menu.add(0, 0, 0, "Alphabetical");
                menu.add(0, 1, 0, "Owned");
                menu.add(0, 2, 0, "Lent Out");

                dropDownMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case 0:
                                // alphabetical was clicked
                                initRecyclerView(DataManager.NAME);
                                return true;
                            case 1:
                                // owned was clicked
                                initRecyclerView(DataManager.OWNED);
                                return true;
                            case 2:
                                // lent out was clicked
                                initRecyclerView(DataManager.LENT);
                                return true;
                        }
                        return false;
                    }
                });
                dropDownMenu.show();
            }
        });

        pieChart =  findViewById(R.id.chart_btn);
        pieChart.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                openPieChart();
            }
        });

        editCollection =  findViewById(R.id.edit_items);
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
        DataManager.getInstance().initData();
        DataManager.getInstance().getActiveCollection();
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
        LoginManager.getInstance().LogOut();
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
    private void initRecyclerView(String filter){
        RecyclerView recyclerView = findViewById(R.id.recycler_view_items);
        RecyclerViewItemAdapter adapter = new RecyclerViewItemAdapter(this,filter);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void openPieChart()
    {
        Intent intent = new Intent(this, Chart.class);
        startActivity(intent);
    }
}