package com.SleeplessStudios.modernpocket;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import ObjectLib.Collectible;
import UtilLib.DataManager;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import de.hdodenhof.circleimageview.CircleImageView;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.IOException;
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
    private TextView amountHeading;

    private AlertDialog.Builder dialogBuilder;
    private AlertDialog dialog;
    private Button takePhoto;
    private Button libraryPhoto;
    private int GET_FROM_GALLERY = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_item);


        saveItem = (ImageButton) findViewById(R.id.check_createitem_btn);
        discardItem = (ImageButton) findViewById(R.id.x_createitem_btn);
        addPhoto = (ImageButton) findViewById(R.id.item_add_img_btn);
        addPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openPhotoDialog();
            }
        });

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
        amountHeading = (TextView) findViewById(R.id.items_amount_headingtxt);

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
                Collectible in = new Collectible(name.getText().toString(), description.getText().toString(),acquireDate.getText().toString(), acquireLoc.getText().toString() ,photoIn);
                DataManager.getInstance().AddOrUpdateItem(in);
                DataManager.getInstance().RefreshActiveCollection();
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

        if (DataManager.getInstance().getActiveItem()!=null){
            //System.out.println("Current Account: "+ LoginManager.getInstance().getActiveUser().getUsername());
            PopulateFields(DataManager.getInstance().getActiveItem());
        }
    }

    private void PopulateFields(Collectible activeItem) {
        name.setText(activeItem.getName());
        description.setText(activeItem.getDescription());
        photo.setImageBitmap(activeItem.getImageBitmap());
        if (activeItem.isOwned()) {
            acquireDate.setText(activeItem.getAcquisitionDate());
            acquireLoc.setText(activeItem.getAcquisitionLoc());
        }

        //todo
        // lent and return
    }

    public void backToItems() {
        finish();
    }



    private void askCamPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 101);
        } else {
            openCamera();
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 101) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openCamera();
            }
        } else {
            Toast.makeText(this, "Camera permission is required to use the camera", Toast.LENGTH_SHORT).show();
        }
    }

    private void openCamera() {
        Intent camera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(camera, 102);
    }
    public void openPhotoDialog() {
        dialogBuilder = new AlertDialog.Builder(this);
        final View photoDialogView = getLayoutInflater().inflate(R.layout.dialog_choose_photo_type, null);

        takePhoto = (Button) photoDialogView.findViewById(R.id.take_photo_btn);
        libraryPhoto = (Button) photoDialogView.findViewById(R.id.library_photo_btn);

        dialogBuilder.setView(photoDialogView);

        dialog = dialogBuilder.create();
        dialog.show();

        takePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                askCamPermission();
                dialog.dismiss();
            }
        });

        libraryPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI), GET_FROM_GALLERY);
                dialog.dismiss();
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 102) {
            Bitmap image = (Bitmap) data.getExtras().get("data");
            photo.setImageBitmap(image);
        }

        //Detects request codes
        if (requestCode == GET_FROM_GALLERY && resultCode == Activity.RESULT_OK) {
            Uri selectedImage = data.getData();
            Bitmap bitmap = null;
            try {
                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImage);
                photo.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }


}