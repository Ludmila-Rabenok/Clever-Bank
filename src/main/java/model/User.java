package model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * This is the model class for the User entity.
 */
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString
public class User extends Entity {
    /**
     * Full username.
     */
    private String fullName;

    public User() {
    }

    public User(String fullName) {
        this.fullName = fullName;
    }

}
