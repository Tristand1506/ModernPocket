package com.SleeplessStudios.modernpocket;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ObjectLib.ItemCollection;
import UtilLib.DataManager;
import de.hdodenhof.circleimageview.CircleImageView;

public class CreateCollection extends AppCompatActivity {
    private ImageButton saveCollection;
    private ImageButton discardCollection;
    private ImageButton collectionImage;
    private EditText collName;
    private EditText collDescription;
    private TextView topCollectionName;
    private CircleImageView photo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_collection);

        saveCollection = (ImageButton) findViewById(R.id.check_btn);
        discardCollection = (ImageButton) findViewById(R.id.x_btn);
        collectionImage = (ImageButton) findViewById(R.id.coll_add_img_btn);
        collName = (EditText) findViewById(R.id.coll_name_txt);
        collDescription = (EditText) findViewById(R.id.coll_desc_txt);
        topCollectionName = (TextView) findViewById(R.id.editable_coll_creation_txt);
        photo = (CircleImageView) findViewById(R.id.coll_image_img);

        if (DataManager.getInstance().getActiveCollection() != null){
            ItemCollection load = DataManager.getInstance().getActiveCollection();
            topCollectionName.setText(load.getCollectionName());
            collName.setText(load.getCollectionName());
            collDescription.setText(load.getDescription());
        }

        discardCollection.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                backToCollections();
            }
        });

        saveCollection.setOnClickListener(new View.OnClickListener(){
        @Override
        public void onClick(View v)
        {
            ItemCollection save = new ItemCollection(collName.getText().toString(),collDescription.getText().toString());
            DataManager.getInstance().AddOrUpdateCollection(save,getApplicationContext());
            backToCollections();
        }
        });

    }

    public void backToCollections()
    {
        DataManager.getInstance().RefreshCollection(this);
        finish();
    }
}