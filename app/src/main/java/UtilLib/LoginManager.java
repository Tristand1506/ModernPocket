package UtilLib;

import android.app.Activity;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.*;
import com.google.android.material.tabs.TabLayout;
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
        Log.e("LoginManager", FirebaseAuth.getInstance().getCurrentUser().getUid());
    return FirebaseAuth.getInstance().getCurrentUser();
    }

    public void UpdateUserData(UserAcount acc){
        System.out.println("Finding User: "+getActiveUser().getUid() );
        userData.child(getActiveUser().getUid()).child("email").setValue(acc.getEmail());
        userData.child(getActiveUser().getUid()).child("username").setValue(acc.getUsername());
        userData.child(getActiveUser().getUid()).child("gender").setValue(acc.getGender());
        userData.child(getActiveUser().getUid()).child("phone").setValue(acc.getPhone());
    }
    public void initUsers(){
        userData = FirebaseDatabase.getInstance("https://modernpocket-f5780-default-rtdb.europe-west1.firebasedatabase.app/").getReference("Users");
    }

    public void LogOut(){
        FirebaseAuth.getInstance().signOut();
        DataManager.getInstance().ClearData();
    }

}
