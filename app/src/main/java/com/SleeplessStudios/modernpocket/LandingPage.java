package com.SleeplessStudios.modernpocket;

import androidx.appcompat.app.AppCompatActivity;

//imported intent and ImageButton
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import UtilLib.LoginManager;

public class LandingPage extends AppCompatActivity {
    private ImageButton signUp;
    private ImageButton login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing_page);
        //LoginManager.getInstance().ClearActiveUser();

        //button listener
        signUp = (ImageButton) findViewById(R.id.lp_signup_btn);
        signUp.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                openSignUpScreen();
            }
        });

        login = (ImageButton) findViewById(R.id.lp_login_btn);
        login.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                openLoginScreen();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        //LoginManager.getInstance().ClearActiveUser();
    }

    //method to open activity using intent
    public void openSignUpScreen()
    {
        Intent intent = new Intent(this, SignUp.class);
        startActivity(intent);
    }

    public void openLoginScreen()
    {
        Intent intent = new Intent(this, Login.class);
        startActivity(intent);
    }
}