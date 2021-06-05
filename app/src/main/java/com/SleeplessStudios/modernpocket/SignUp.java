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

public class SignUp extends AppCompatActivity {
    private ImageButton btnSignUp;
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
        btnSignUp = (ImageButton) findViewById(R.id.login_btn);
        btnSignUp.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                SignUpAccount();
            }
        });

        password.setOnEditorActionListener(new EditText.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    btnSignUp.performClick();
                    return true;
                }
                return false;
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