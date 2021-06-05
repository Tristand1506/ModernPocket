package com.SleeplessStudios.modernpocket;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

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

        password.setOnEditorActionListener(new EditText.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    btnLogIn.performClick();
                    return true;
                }
                return false;
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