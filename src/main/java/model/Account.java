package model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

/**
 * This is the model class for the Account entity.
 */
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString
public class Account extends Entity {
    /**
     * Account number
     */
    private Long number;
    /**
     * The user is the owner of the account.
     */
    private User user;
    /**
     * The account belongs to this bank.
     */
    private Bank bank;
    /**
     * An account is opened in this currency.
     */
    private Currency currency;
    /**
     * Account opening date.
     */
    private LocalDate dateOpenAccount;
    /**
     * The amount of money in the account.
     */
    private volatile Double balance;
    /**
     * Accrued interest that will be added to the balance at the end of the month.
     */
    private volatile Double monthlyPercentage;

    public Account() {
    }

    public Account(Long number, User user, Bank bank, Currency currency, LocalDate dateOpenAccount,
                   Double balance, Double monthlyPercentage) {
        this.number = number;
        this.user = user;
        this.bank = bank;
        this.currency = currency;
        this.dateOpenAccount = dateOpenAccount;
        this.balance = balance;
        this.monthlyPercentage = monthlyPercentage;
    }

}
