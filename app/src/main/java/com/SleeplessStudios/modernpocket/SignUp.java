package com.SleeplessStudios.modernpocket;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import UtilLib.LoginManager;

public class SignUp extends AppCompatActivity {
    private ImageButton mainScreen;
    private EditText username;
    private EditText email;
    private EditText password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        // Text Fields
        username = (EditText) findViewById(R.id.user_txt);
        email    = (EditText) findViewById(R.id.email_txt);
        password    = (EditText) findViewById(R.id.passwords_txt);
        //button listener
        mainScreen = (ImageButton) findViewById(R.id.login_btn);
        mainScreen.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                SignUpAccount();
            }
        });
    }

    private void SignUpAccount(){
        if(LoginManager.getInstance().AddAccount(username.getText().toString(), email.getText().toString(), password.getText().toString(), this)){
            openMainScreen();
        };
    }
    public void openMainScreen()
    {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}