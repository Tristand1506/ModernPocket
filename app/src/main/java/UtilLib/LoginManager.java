package UtilLib;

import android.app.Activity;
import android.widget.Toast;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;

import ObjectLib.UserAcount;

public class LoginManager {

    // Singleton DataManager
    private static final LoginManager Manage = new LoginManager();
    public static LoginManager getInstance() {return Manage;}

    private List<UserAcount> accounts = new ArrayList<>();

    public boolean AddAccount(String username, String email, String password, Activity currentActivity){

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

        // repetition check
        for (UserAcount acc : accounts) {

            if (username.equals(acc.Username())){
                Toast.makeText(currentActivity, "Username already taken.", Toast.LENGTH_SHORT).show();
                System.out.println("invalid account : username taken");
                return false;
            }

            if (email.equals(acc.Email())){
                Toast.makeText(currentActivity, "Account already exists, try logging in.", Toast.LENGTH_LONG).show();
                System.out.println("invalid account : Email in use");
                return false;
            }
        }
        accounts.add(new UserAcount(username, email,password));
        AccDebug();
        return true;
    }

    public void AccDebug(){
        for (UserAcount acc : accounts) {
            System.out.printf(" NEXT ACCOUNT... ");
            System.out.println("Username: " +acc.Username());
            System.out.println("Email: " +acc.Email());
            System.out.println("\n");

        }
    }

}
