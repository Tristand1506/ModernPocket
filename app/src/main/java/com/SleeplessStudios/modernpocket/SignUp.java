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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.*;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import ObjectLib.UserAcount;
import UtilLib.DataManager;
import UtilLib.LoginManager;

public class SignUp extends AppCompatActivity {
    private String TAG = "Sign_Up";
    private ImageButton btnSignUp;
    private EditText username;
    private EditText email;
    private EditText password;

    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        //Firebase Init
        mAuth = FirebaseAuth.getInstance();


        // Text Fields
        username =  findViewById(R.id.user_txt);
        email    =  findViewById(R.id.email_txt);
        password    =  findViewById(R.id.passwords_txt);
        //button listener
        btnSignUp = (ImageButton) findViewById(R.id.login_btn);
        btnSignUp.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                SignUpAccount(email.getText().toString(),password.getText().toString());
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

    @Override
    protected void onStart() {
        super.onStart();
        LoginManager.getInstance().initUsers();
    }

    private void SignUpAccount(String email , String password ){
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            //create new userData
                            UserAcount user = new UserAcount(username.getText().toString(),email);
                            System.out.println( user.toString());
                            LoginManager.getInstance().getUserDatabase()
                                    .child(mAuth.getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        // Sign in success, update UI with the signed-in user's information
                                        Log.d(TAG, "createUserWithEmail:success");

                                        Toast.makeText(SignUp.this, "Registration Successful", Toast.LENGTH_LONG).show();
                                        openMainScreen();
                                    } else {
                                        Log.w(TAG, "User Addition Failed", task.getException());
                                        // TODO
                                        // put something here i guess
                                    }
                                }
                            });
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(SignUp.this, "Authentication failed.",
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

