package com.SleeplessStudios.modernpocket;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.IOException;

import ObjectLib.ItemCollection;
import UtilLib.DataManager;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import de.hdodenhof.circleimageview.CircleImageView;

public class CreateCollection extends AppCompatActivity {
    private ImageButton saveCollection;
    private ImageButton discardCollection;
    private ImageButton collectionImage;
    private EditText collName;
    private EditText collDescription;
    private TextView topCollectionName;
    private CircleImageView photo;
    private int GET_FROM_GALLERY = 1;

    private AlertDialog.Builder dialogBuilder;
    private AlertDialog dialog;
    private Button takePhoto;
    private Button libraryPhoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_collection);

        saveCollection = (ImageButton) findViewById(R.id.check_btn);
        discardCollection = (ImageButton) findViewById(R.id.x_btn);
        collectionImage = (ImageButton) findViewById(R.id.coll_add_img_btn);
        collectionImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openPhotoDialog();
            }
        });

        collName = (EditText) findViewById(R.id.coll_name_txt);
        collDescription = (EditText) findViewById(R.id.coll_desc_txt);
        topCollectionName = (TextView) findViewById(R.id.editable_coll_creation_txt);
        photo = (CircleImageView) findViewById(R.id.coll_image_img);

        if (DataManager.getInstance().getActiveCollection() != null) {
            PopulateFields(DataManager.getInstance().getActiveCollection());
        }

        discardCollection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backToCollections();
            }
        });

        saveCollection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BitmapDrawable bd = (BitmapDrawable) photo.getDrawable();
                Bitmap photoIn = bd.getBitmap();
                ItemCollection save = new ItemCollection(collName.getText().toString(), collDescription.getText().toString(), photoIn);
                DataManager.getInstance().AddOrUpdateCollection(save);
                //DataManager.getInstance().setActiveCollection(DataManager.getInstance().getActiveCollection());
                DataManager.getInstance().RefreshCollection(getParent());
                backToCollections();
            }
        });
    }

    private void PopulateFields(ItemCollection activeCollection) {
        collName.setText(activeCollection.getCollectionName());
        collDescription.setText(activeCollection.getDescription());
        photo.setImageBitmap(activeCollection.getImageBitmap());
    }

    public void backToCollections() {
        DataManager.getInstance().RefreshCollection(this);
        finish();
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