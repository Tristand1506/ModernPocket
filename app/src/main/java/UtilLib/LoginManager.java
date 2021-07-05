package UtilLib;

import android.app.Activity;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.*;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import ObjectLib.UserAcount;

public class LoginManager {

    private String TAG = "LoginManager";

    // Singleton DataManager
    private static final LoginManager Manage = new LoginManager();
    public static LoginManager getInstance() {return Manage;}
    private DatabaseReference userData = FirebaseDatabase.getInstance("https://modernpocket-f5780-default-rtdb.europe-west1.firebasedatabase.app/").getReference("Users");

    public DatabaseReference getUserDatabase(){
        return userData;
    }
    public LoginManager(){
        userData.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                accounts.clear();
                System.out.println("Refreshing Accounts...");
                for (DataSnapshot snap: dataSnapshot.getChildren())
                {
                    UserAcount acc = snap.getValue(UserAcount.class);
                    accounts.add(acc);
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
    }
    private List<UserAcount> accounts = new ArrayList<>();

    public UserAcount getAccountFromEmail(String email){
        for (UserAcount acc: accounts) {
            if (acc.getEmail().equalsIgnoreCase(email)){
                return acc;
            }
        }
        return  null;
    }
    public UserAcount getAccountFromUsername(String username){
        for (UserAcount acc: accounts) {
            if (acc.getUsername().equals(username)){
                return acc;
            }
        }
        return  null;
    }

    public static FirebaseUser getActiveUser(){
    return FirebaseAuth.getInstance().getCurrentUser();
    }



    /*public boolean AddAccount(String username, String email, String password, Activity currentActivity){

        if (username.trim().isEmpty()){
            Toast.makeText(currentActivity, "Enter Username.", Toast.LENGTH_SHORT).show();
            System.out.println("invalid acount : username empty");
            return false;
        }
        if (email.trim().isEmpty()){
            Toast.makeText(currentActivity, "Enter Email.", Toast.LENGTH_SHORT).show();
            System.out.println("invalid acount : email empty");
            return false;
        }
        if (password.trim().isEmpty()){
            Toast.makeText(currentActivity, "Enter Valid Password.", Toast.LENGTH_SHORT).show();
            System.out.println("invalid acount : password empty");
            return false;
        }
        else if (password.trim().length() < 5){
            Toast.makeText(currentActivity, "Password Too Short.", Toast.LENGTH_SHORT).show();
            System.out.println("invalid account : password Short");
            return false;
        }

        if (SQLiteDBHelper.getDataBase(currentActivity).findAccountUserName(username)!=null){
            Toast.makeText(currentActivity, "Username already taken.", Toast.LENGTH_SHORT).show();
            System.out.println("invalid account : username taken");
            return false;
        }
        if (SQLiteDBHelper.getDataBase(currentActivity).findAccountEmail(email)!=null){
            Toast.makeText(currentActivity, "Account already exists, try logging in.", Toast.LENGTH_LONG).show();
            System.out.println("invalid account : Email in use");
            return false;
        }

        // adds user to DB
        SQLiteDBHelper.getDataBase(currentActivity).addAccount(new UserAcount(username,email,password));
        // sets active user to the user that just signed in
        setActiveUser(SQLiteDBHelper.getDataBase(currentActivity).findAccountUserName(username));
        AccDebug(currentActivity);
        return true;
    }*/
    /*public boolean ValidateAccount(String userIn,String passwordIn, Activity activity){
        AccDebug(activity);
        UserAcount acc = SQLiteDBHelper.getDataBase(activity).findAccountUserName(userIn);
        boolean isValid = false;
        if (acc == null){
            acc = SQLiteDBHelper.getDataBase(activity).findAccountEmail(userIn);
        }
        if (acc != null){
            isValid = acc.ValidateAcount(passwordIn);
        }
        if (isValid){
            setActiveUser(acc);
            System.out.println(activeUser.toString());
            return isValid;
        }
        Toast.makeText(activity, "Incorrect Username or Password.", Toast.LENGTH_SHORT).show();
        return isValid;
    }*/
    public void initUsers(){
        userData = FirebaseDatabase.getInstance("https://modernpocket-f5780-default-rtdb.europe-west1.firebasedatabase.app/").getReference("Users");
    }


}
