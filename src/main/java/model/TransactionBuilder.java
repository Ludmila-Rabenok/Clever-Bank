package model;

import java.time.LocalDateTime;

/**
 * This class is a transaction builder.
 */
public class TransactionBuilder {
    LocalDateTime dateTime;
    TypeTransaction typeTransaction;
    Double sum;
    Account account1;
    Account account2;

    public TransactionBuilder(TypeTransaction typeTransaction) {
        this.typeTransaction = typeTransaction;
    }

    public TransactionBuilder setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
        return this;
    }

    public TransactionBuilder setTypeTransaction(TypeTransaction typeTransaction) {
        this.typeTransaction = typeTransaction;
        return this;
    }

    public TransactionBuilder setSum(Double sum) {
        this.sum = sum;
        return this;
    }

    public TransactionBuilder setAccount1(Account account1) {
        this.account1 = account1;
        return this;
    }

    public TransactionBuilder setAccount2(Account account2) {
        this.account2 = account2;
        return this;
    }

    public Transaction build() {
        return new Transaction(this);
    }
}
