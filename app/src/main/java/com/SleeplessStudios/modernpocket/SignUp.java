package com.SleeplessStudios.modernpocket;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class SignUp extends AppCompatActivity {
    public ImageButton mainScreen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        //button listener
        mainScreen = (ImageButton) findViewById(R.id.login_btn);
        mainScreen.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                openMainScreen();
            }
        });
    }

    public void openMainScreen()
    {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}