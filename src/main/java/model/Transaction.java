package model;


import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

/**
 * This is the model class for the Transaction entity.
 */
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString
public class Transaction extends Entity {
    /**
     * The time and date the transaction was created.
     */
    private LocalDateTime dateTime;
    /**
     * Transaction type.
     */
    private TypeTransaction typeTransaction;
    /**
     * Transaction amount.
     */
    private Double sum;
    /**
     * This is the main account. The owner of this account makes a transaction.
     */
    private Account account1;
    /**
     * This account is optional. It is needed when funds are transferred between two accounts.
     */
    private Account account2;

    public Transaction(TransactionBuilder builder) {
        this.dateTime = builder.dateTime;
        this.typeTransaction = builder.typeTransaction;
        this.sum = builder.sum;
        this.account1 = builder.account1;
        this.account2 = builder.account2;
    }

}
