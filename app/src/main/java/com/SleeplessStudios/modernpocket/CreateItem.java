package com.SleeplessStudios.modernpocket;

import androidx.appcompat.app.AppCompatActivity;

import ObjectLib.Collectible;
import UtilLib.DataManager;
import UtilLib.SQLiteDBHelper;
import de.hdodenhof.circleimageview.CircleImageView;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CreateItem extends AppCompatActivity {
    private ImageButton saveItem;
    private ImageButton discardItem;
    private ImageButton addPhoto;
    private ImageButton lessAmount;
    private ImageButton addAmount;
    private ImageButton createItemType;
    private EditText name;
    private EditText amount;
    private EditText description;
    private EditText acquireDate;
    private EditText acquireLoc;
    private EditText lentTo;
    private EditText returnDate;
    private TextView itemName;
    private Spinner spinnerChooseItemType;
    private CircleImageView photo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_item);

        saveItem = (ImageButton) findViewById(R.id.check_createitem_btn);
        discardItem = (ImageButton) findViewById(R.id.x_createitem_btn);
        addPhoto = (ImageButton) findViewById(R.id.item_add_img_btn);
        lessAmount = (ImageButton) findViewById(R.id.less_items_btn);
        addAmount = (ImageButton) findViewById(R.id.add_items_btn);
        createItemType = (ImageButton) findViewById(R.id.create_item_type_btn);

        name = (EditText) findViewById(R.id.item_name_txt);
        amount = (EditText) findViewById(R.id.items_amount_txt);
        description = (EditText) findViewById(R.id.item_desc_txt);
        acquireDate = (EditText) findViewById(R.id.item_date_acq_txt);
        acquireLoc = (EditText) findViewById(R.id.item_location_acq_txt);
        lentTo = (EditText) findViewById(R.id.item_lent_to_txt);
        returnDate = (EditText) findViewById(R.id.item_return_date_txt);

        itemName = (TextView) findViewById(R.id.top_item_name_txt);

        photo = (CircleImageView) findViewById(R.id.item_image_img);

        discardItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backToItems();
            }
        });
        saveItem.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                BitmapDrawable bd = (BitmapDrawable) photo.getDrawable();
                Bitmap photoIn = bd.getBitmap();
                Collectible in = new Collectible(name.getText().toString(), description.getText().toString(), getDateFromString(acquireDate.getText().toString()), acquireLoc.getText().toString() ,photoIn);
                DataManager.getInstance().AddOrUpdateItem(in,getParent());
                backToItems();
            }
        });

        //spinner
        spinnerChooseItemType = findViewById(R.id.chooseitemtype_spinner);
        //array for spinner
        String[] itemTypeList = getResources().getStringArray(R.array.item_types);
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, itemTypeList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerChooseItemType.setAdapter(adapter);
    }

    public void backToItems() {
        DataManager.getInstance().refreshItems(this);
        finish();
    }


    public static Date getDateFromString(String dateIn){
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date();
        try{
            date = dateFormat.parse(dateIn);
        } catch (ParseException e){
            Log.e("DataBase", "getDateFromString: Failed, invalid format ", e );
        }
        return date;
    }



}