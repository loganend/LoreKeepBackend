package lorekeep.user;

import javax.persistence.*;

@Entity
@Table(name = "\"user\"")
public class User {

    @Id
    @GeneratedValue
    private long userId;
    @Column(unique = true)
    private String username;
    private String password;
    @Column(unique = true)
    private String email;
    @Column(unique = true)
    private String phoneNumber;

    public User() {
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {

        return password;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public long getUserId() {

        return userId;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }


}
