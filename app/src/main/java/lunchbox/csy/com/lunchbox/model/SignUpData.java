package lunchbox.csy.com.lunchbox.model;

public class SignUpData {
    public String username;
    public String email;
    public String password;

    public SignUpData(String email, String username, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }
}
