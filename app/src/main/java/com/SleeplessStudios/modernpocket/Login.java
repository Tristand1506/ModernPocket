package com.SleeplessStudios.modernpocket;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.*;
import com.google.firebase.auth.*;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import UtilLib.DataManager;
import UtilLib.LoginManager;

public class Login extends AppCompatActivity {
    private ImageButton btnLogIn;
    private EditText loginField;
    private EditText password;

    //Tag
    private String TAG ="LoginPage";

    // firebase instance
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Firebase Init
        mAuth = FirebaseAuth.getInstance();

        setContentView(R.layout.activity_login);
        loginField  =  findViewById(R.id.username_txt);
        password    =  findViewById(R.id.password_txt);

        //button listener
        btnLogIn = findViewById(R.id.login_btn);
        btnLogIn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                LogInAccount(loginField.getText().toString(), password.getText().toString());
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

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        LoginManager.getInstance().initUsers();
        if (currentUser != null){
            //TODO
            // Find out where the fuck to put this stupid thing
        }
    }

    private void LogInAccount(String email, String password){
        String _email = email;
        if (!_email.contains("@")){
            if (LoginManager.getInstance().getAccountFromUsername(email) != null) {
                _email = LoginManager.getInstance().getAccountFromUsername(email).getEmail();
            }
        }
        mAuth.signInWithEmailAndPassword(_email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            openMainScreen();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure, attempting User SignIn", task.getException());
                            Toast.makeText(Login.this, "Login failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public void openMainScreen()
    {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}