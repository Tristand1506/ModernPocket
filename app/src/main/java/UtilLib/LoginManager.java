package UtilLib;

import android.app.Activity;
import android.content.Context;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import ObjectLib.UserAcount;

public class LoginManager {

    // Singleton DataManager
    private static final LoginManager Manage = new LoginManager();
    public static LoginManager getInstance() {return Manage;}

    private UserAcount activeUser;
    public UserAcount getActiveUser(){
    return activeUser;
    }

    //private List<UserAcount> accounts = new ArrayList<>();



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

        SQLiteDBHelper.getDataBase(currentActivity).addAccount(new UserAcount(username,email,password));

        AccDebug(currentActivity);
        return true;
    }

    public boolean ValidateAccount(String userIn,String passwordIn, Activity activity){
        UserAcount acc = SQLiteDBHelper.getDataBase(activity).findAccountUserName(userIn);
        boolean isValid = false;
        if (acc == null){
            acc = SQLiteDBHelper.getDataBase(activity).findAccountEmail(userIn);
        }
        if (acc != null){
            isValid = acc.ValidateAcount(passwordIn);
        }
        if (isValid){
            activeUser = acc;
            return isValid;
        }
        Toast.makeText(activity, "Incorrect Username or Password.", Toast.LENGTH_SHORT).show();
        return isValid;
    }

    public void AccDebug(Activity activity){
        System.out.println( SQLiteDBHelper.getDataBase(activity).loadAccounts() );
    }

}
