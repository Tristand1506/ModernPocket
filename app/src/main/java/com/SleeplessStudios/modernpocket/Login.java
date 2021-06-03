package com.SleeplessStudios.modernpocket;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import UtilLib.LoginManager;

public class Login extends AppCompatActivity {
    private ImageButton btnLogIn;
    private EditText loginField;
    private EditText password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loginField  = (EditText) findViewById(R.id.username_txt);
        password    = (EditText) findViewById(R.id.password_txt);

        //button listener
        btnLogIn = (ImageButton) findViewById(R.id.login_btn);
        btnLogIn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                LogInAccount();
            }
        });
    }

    private void LogInAccount(){
        if(LoginManager.getInstance().ValidateAccount(loginField.getText().toString(),password.getText().toString(),this)){
            openMainScreen();
        };
    }

    public void openMainScreen()
    {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}