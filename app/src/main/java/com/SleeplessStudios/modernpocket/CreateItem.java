package com.SleeplessStudios.modernpocket;

import androidx.appcompat.app.AppCompatActivity;
import de.hdodenhof.circleimageview.CircleImageView;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

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

        saveItem = (ImageButton) findViewById(R.id.create_item_btn);
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

        //spinner
        spinnerChooseItemType = findViewById(R.id.chooseitemtype_spinner);
        //array for spinner
        String[] itemTypeList = getResources().getStringArray(R.array.item_types);
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, itemTypeList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerChooseItemType.setAdapter(adapter);
    }
}