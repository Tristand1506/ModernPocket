package ObjectLib;

public class UserAcount {

    public UserAcount(String username, String email, String password) {
        setEmail(email);
        setPassword(password);
        setUsername(username);
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

    String username;
    String email;
    String password;

    public boolean ValidateAcount (String password){
        return password.equals(this.password);
    }
}
