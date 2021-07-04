package ObjectLib;

public class UserAcount {

    public UserAcount(){}
    public UserAcount(String username, String email) {
        setEmail(email);
        setUsername(username);
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    String username;
    public String getUsername(){
        return username;
    }

    String email;
    public String getEmail(){
        return email;
    }


    @Override
    public String toString() {
        return "UserAcount{" +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
