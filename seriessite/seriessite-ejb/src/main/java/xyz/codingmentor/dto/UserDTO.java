package xyz.codingmentor.dto;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import xyz.codingmentor.entity.User;
import xyz.codingmentor.service.UserService;

/**
 *
 * @author Dávid Kovácsvölgyi <kovacsvolgyi.david@gmail.com>
 */
public class UserDTO {

    private User user;

    @Size(min = 1, message = "This field has to be filled.")
    @Pattern(regexp="^((?=.*\\d)(?=.*\\p{Ll})(?=.*\\p{Lu}).{6,20})$", message="Wrong password format.")
    private String password;

    public User makeUser() {
        String outcome;
        outcome = UserService.hashPassword(password);
        user.setPassword(outcome);
        return this.user;
    }

    public UserDTO() {
        user = new User();
    }

    public UserDTO(User user, String password) {
        this.user = user;
        this.password = password;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
