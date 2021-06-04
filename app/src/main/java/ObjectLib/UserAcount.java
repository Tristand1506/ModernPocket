package ObjectLib;

public class UserAcount {

    public UserAcount(){}
    public UserAcount(int id, String username, String email, String password) {
        setID(id);
        setEmail(email);
        setPassword(password);
        setUsername(username);
    }

    public void setID(int id) {
        this.accountID = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    private int accountID;
    public int getAccountID(){
        return accountID;
    }

    String username;
    public String getUsername(){
        return username;
    }

    String email;
    public String getEmail(){
        return email;
    }

    String password;
    public String getPassword(){
        return password;
    }
    public boolean ValidateAcount (String password){
        return password.equals(this.password);
    }
}
