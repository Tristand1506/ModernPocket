package com.SleeplessStudios.modernpocket;

import UtilLib.DataModal;
import UtilLib.LoginManager;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.toolbox.StringRequest;
import com.google.android.material.navigation.NavigationView;

import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
//import okhttp3.Request;
import okhttp3.RequestBody;
//import okhttp3.Response;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.ml.vision.FirebaseVision;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;
import com.google.firebase.ml.vision.label.FirebaseVisionImageLabel;
import com.google.firebase.ml.vision.label.FirebaseVisionImageLabeler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class ObjectLens extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    static final int REQUEST_IMAGE_CAPTURE = 1;
    private DrawerLayout drawer;
    private ImageButton sidebar;
    private ProgressBar loading;

    private ImageView img;
    private Button snap, searchResultsBtn;
    private Bitmap imageBitmap;
    private RecyclerView resultRV;
    private RVLensAdapter searchResultsRVAdapter;
    private ArrayList<DataModal> dataModalArrayList;
    private String title, link, displayed_link, snippet;

    private String TAG = "ObjectLens";
    //public static int TIMEOUT_MS=10000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_object_lens);

        drawer = findViewById(R.id.sidebar_main);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, R.string.sidebar_open, R.string.sidebar_close);
        drawer.addDrawerListener(toggle);
        NavigationView navView = findViewById(R.id.sidebar_view);
        navView.setNavigationItemSelectedListener(this);

        sidebar = (ImageButton) findViewById(R.id.burgerbar_lens_btn);
        sidebar.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                openSidebar();
            }
        });

        img = (ImageView) findViewById(R.id.image);
        snap = (Button) findViewById(R.id.snapbtn);
        searchResultsBtn = findViewById(R.id.idBtnSearchResults);
        resultRV = findViewById(R.id.idRVSearchResults);
        loading = (ProgressBar) findViewById(R.id.loading);

        // initializing our array list
        dataModalArrayList = new ArrayList<>();

        // initializing our adapter class.
        searchResultsRVAdapter = new RVLensAdapter(dataModalArrayList, ObjectLens.this);

        // layout manager for our recycler view.
        LinearLayoutManager manager = new LinearLayoutManager(ObjectLens.this, LinearLayoutManager.VERTICAL, false);
        resultRV.setLayoutManager(manager);
        resultRV.setAdapter(searchResultsRVAdapter);

        snap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // calling a method to capture an image.
                dispatchTakePictureIntent();
            }
        });

        searchResultsBtn.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                // calling a method to get search results.
                loading.setVisibility(View.VISIBLE);
                //loading.setProgress(10, true);
                getResults();
            }
        });
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
        LoginManager.getInstance().LogOut();
        Intent intent = new Intent(this, LandingPage.class);
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

    //------------OBJECT LENS METHODS--------------
    private void getResults() {
        // inside the label image method we are calling a firebase vision image
        // and passing our image bitmap to it.
        FirebaseVisionImage image = FirebaseVisionImage.fromBitmap(imageBitmap);

        // on below line we are creating a labeler for our image bitmap and
        // creating a variable for our firebase vision image labeler.
        FirebaseVisionImageLabeler labeler = FirebaseVision.getInstance().getOnDeviceImageLabeler();

        // calling a method to process an image and adding on success listener method to it.
        labeler.processImage(image).addOnSuccessListener(new OnSuccessListener<List<FirebaseVisionImageLabel>>() {
            @Override
            public void onSuccess(List<FirebaseVisionImageLabel> firebaseVisionImageLabels) {
                String searchQuery = firebaseVisionImageLabels.get(0).getText();
                try {
                    searchData(searchQuery);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                // displaying error message.
                Toast.makeText(ObjectLens.this, "Failed to detect image", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void searchData(String searchQuery) throws IOException {
        //not using serpapi, using serphouse

        //String apiKey = "&api_token=Q85iAOTYmwNWiPOob8LFs4gOYN553LqIOWQLw4Qj9NrBJrY5nxVmzKkR14dp";
        String apiKey = "apikey=e1739ac0-e172-11eb-b293-0d9a803019c7&q=";
        String url = "https://app.zenserp.com/api/v2/search?" + apiKey + searchQuery.trim() + "&search_engine=google.com&location=Cape%20Town%2CWestern%20Cape%2CSouth%20Africa&hl=en";

        // creating a new variable for our request queue
        RequestQueue queue = Volley.newRequestQueue(ObjectLens.this);


        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                Log.e(TAG, "response received");
                try {

                    //JSONObject results = response.getJSONObject("results").getJSONObject("results");
                    //JSONObject results = response.getJSONObject("knowledge_graph").getJSONObject("knowledge_graph");

                    // extracting data from our json.
                    JSONArray organicResultsArray = response.getJSONArray("organic");

                    System.out.println(organicResultsArray);
                    for (int i = 0; i < organicResultsArray.length(); i++) {
                        JSONObject organicObj = organicResultsArray.getJSONObject(i);
                        if (organicObj.has("title")) {
                            title = organicObj.getString("title");
                        }
                        if (organicObj.has("url")) {
                            link = organicObj.getString("url");
                        }
                        if (organicObj.has("destination")) {
                            displayed_link = organicObj.getString("destination");
                        }
                        if (organicObj.has("description")) {
                            snippet = organicObj.getString("description");
                        }
                        // on below line we are adding data to our array list.
                        dataModalArrayList.add(new DataModal(title, link, displayed_link, snippet));
                    }
                    // notifying our adapter class
                    // on data change in array list.
                    loading.setVisibility(View.INVISIBLE);
                    searchResultsRVAdapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    loading.setVisibility(View.VISIBLE);
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // displaying error message.
                loading.setVisibility(View.VISIBLE);
                Toast.makeText(ObjectLens.this, "No results found for this search query", Toast.LENGTH_SHORT).show();
                Log.e(TAG, "error finding search results: \n" + error);
            }
        });
        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(50 * 1000, 1, 1.0f));
        queue.add(jsonObjectRequest);
    }

    // method to capture image.
    private void dispatchTakePictureIntent() {
        // inside this method we are calling an implicit intent to capture an image.
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // calling a start activity for result when image is captured.
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // inside on activity result method we are
        // setting our image to our image view from bitmap.
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            imageBitmap = (Bitmap) extras.get("data");
            // on below line we are setting our
            // bitmap to our image view.
            img.setImageBitmap(imageBitmap);
        }
    }
}