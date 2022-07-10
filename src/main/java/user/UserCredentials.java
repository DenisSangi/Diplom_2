package user;

import lombok.Data;

@Data
public class UserCredentials {
    String email;
    String password;
    String name;

    public UserCredentials(User user) {
        this.email = user.getEmail();
        this.password = user.getPassword();
        this.name = user.getName();
    }

    public static UserCredentials from(User user) {
        return new UserCredentials(user);
    }

}