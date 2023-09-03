package model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * This is the model class for the Bank entity.
 */
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString
public class Bank extends Entity {
    /**
     * Bank's name
     */
    private String name;

    public Bank() {
    }

    public Bank(String name) {
        this.name = name;
    }

}
