package model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * This is the model class for the Check entity.
 */
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString
public class Check extends Entity {
    /**
     * The transaction for which the check is created.
     */
    private Transaction transaction;

    public Check(Transaction transaction) {
        this.transaction = transaction;
    }

}
