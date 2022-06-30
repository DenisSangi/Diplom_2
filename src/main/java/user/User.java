package user;

import lombok.Data;
import org.apache.commons.lang3.RandomStringUtils;

@Data
public class User {

    String email;
    String password;
    String name;

    public User(String email, String password, String name) {
        this.email = email;
        this.password = password;
        this.name = name;
    }

    public static User getRandom() {
        String email = RandomStringUtils.randomAlphanumeric(10) + "@gmail.com";
        String password = RandomStringUtils.randomAlphanumeric(10);
        String name = RandomStringUtils.randomAlphanumeric(10);

        return new User(email, password, name);
    }

    public static User noEmail() {
        String password = RandomStringUtils.randomAlphanumeric(10);
        String name = RandomStringUtils.randomAlphanumeric(10);

        return new User(null, password, name);
    }

    public static User noPassword() {
        String email = RandomStringUtils.randomAlphanumeric(10) + "@gmail.com";
        String name = RandomStringUtils.randomAlphanumeric(10);

        return new User(email, null, name);
    }

    public static User noName() {
        String email = RandomStringUtils.randomAlphanumeric(10) + "@gmail.com";
        String password = RandomStringUtils.randomAlphanumeric(10);

        return new User(email, password, null);
    }

    public static User wrongEmail() {
        String email = "WrongLoginCourier@gmail.com";
        String password = RandomStringUtils.randomAlphanumeric(10);
        String name = RandomStringUtils.randomAlphanumeric(10);

        return new User(email, password, name);
    }

    public static User wrongPassword() {
        String email = RandomStringUtils.randomAlphanumeric(10) + "@gmail.com";
        String password = "WrongPasswordCourier";
        String name = RandomStringUtils.randomAlphanumeric(10);

        return new User(email, password, name);
    }

}