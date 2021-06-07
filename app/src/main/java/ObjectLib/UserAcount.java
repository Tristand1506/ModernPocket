package ObjectLib;

public class UserAcount {

    public UserAcount(){}
    public UserAcount(int id, String username, String email, String password) {
        setID(id);
        setEmail(email);
        setPassword(password);
        setUsername(username);
    }
    public UserAcount(String username, String email, String password) {
        setEmail(email);
        setPassword(password);
        setUsername(username);
    }

    public void setID(int id) {
        if (_id == null){
            _id = id;
        }

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

    private Integer _id;
    public int getID(){
        return _id;
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

    @Override
    public String toString() {
        return "UserAcount{" +
                "_id=" + _id +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
