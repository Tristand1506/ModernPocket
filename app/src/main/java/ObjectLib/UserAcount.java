package ObjectLib;

public class UserAcount {

    String email;
    String username;
    String phone;
    String gender;
    public UserAcount(){}
    public UserAcount(String username, String email) {
        setEmail(email);
        setUsername(username);
    }
    public UserAcount(String username, String email, String phone, String gender) {
        setEmail(email);
        setUsername(username);
        setPhone(phone);
        setGender(gender);
    }

    public void setUsername(String username) {
        this.username = username;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getUsername(){
        return username;
    }
    public String getEmail(){
        return email;
    }
    public String getGender() {
        return gender;
    }
    public String getPhone() {
        return phone;
    }

    @Override
    public String toString() {
        return "UserAcount{" +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
