package user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserData {

    private String email = RandomStringUtils.randomAlphanumeric(10).toLowerCase()  + "@" + "gmail.com";
    private String name = RandomStringUtils.randomAlphanumeric(10);
}
