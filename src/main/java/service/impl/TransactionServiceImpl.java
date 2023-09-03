package service.impl;

import model.Account;
import model.Transaction;
import model.TransactionBuilder;
import model.TypeTransaction;
import repository.Repository;
import service.AccountService;
import service.PrintCheckService;
import service.TransactionService;

import java.time.LocalDateTime;
import java.util.List;

/**
 * This is a service class for transactions. Contains methods for saving, deleting, getting, creating transactions.
 * The class contains a reference to an instance of the repository class and two services.
 */
public class TransactionServiceImpl implements TransactionService {
    private final Repository<Transaction> transactionRepository;
    private final AccountService accountService;
    private final PrintCheckService printCheckService;

    /**
     * This constant is needed for verification. Whether the account is a client of our bank.
     */
    private static final String BANK_NAME = "Clever-Bank";
    /**
     * This constant is needed to report an error
     * if the user tries to perform actions on an account that does not belong to our bank.
     */
    private static final String NOT_CLIENT = "You are not a client of our bank";
    /**
     * This constant is needed to report an error if there is not enough money on the balance.
     */
    private static final String NOT_ENOUGH_FUNDS = "There are not enough funds on the account";
    /**
     * This constant is needed to display an error if the transaction type is entered incorrectly.
     */
    private static final String INCORRECT_TYPE = "Incorrect transaction type";

    public TransactionServiceImpl(Repository<Transaction> transactionRepository, AccountService accountService,
                                  PrintCheckService printCheckService) {
        this.transactionRepository = transactionRepository;
        this.accountService = accountService;
        this.printCheckService = printCheckService;
    }

    /**
     * The method calls the method of saving the transaction in the database in the repository class
     *
     * @param transaction accepts an transaction to be stored in the database
     * @return returns the transaction stored in the database
     */
    @Override
    public Transaction save(Transaction transaction) {
        return transactionRepository.save(transaction);
    }

    /**
     * The method calls a method in the repository class to get all transactions from the database
     *
     * @return a list with transactions from the database
     */
    @Override
    public List<Transaction> getAll() {
        return transactionRepository.getAll();
    }

    /**
     * The method calls a method in the repository class to get the transaction by id from the database
     *
     * @return transaction by Id from the database
     */
    @Override
    public Transaction getById(Integer id) {
        return transactionRepository.getById(id);
    }

    /**
     * The method calls a method in the repository class to remove the transaction from the database
     *
     * @param id accepts the Integer id of the transaction to be deleted
     * @return true if the deletion was done correctly
     */
    @Override
    public boolean delete(Integer id) {
        return transactionRepository.remove(id);
    }

    /**
     * The method is a manager for creating transactions depending on the type.
     * The method checks whether the account is a client of our bank.
     *
     * @param account1 This is the main account. The owner of this account makes a transaction.
     * @param account2 This account is optional. It is needed when funds are transferred between two accounts.
     * @param type     Transaction type (WITHDRAWALS, REPLENISHMENT, TRANSFER_TO_CARD)
     * @param sum      The amount of money transferred or received.
     * @see TransactionServiceImpl#createTransaction(Account, Account, TypeTransaction, Double)
     */
    @Override
    public void doTransaction(Account account1, Account account2, TypeTransaction type, Double sum) {
        if (account1.getBank().getName().equals(BANK_NAME)) {
            switch (type) {
                case WITHDRAWALS:
                case REPLENISHMENT:
                    createTransaction(account1, account2, type, sum);
                    break;
                case TRANSFER_TO_CARD:
                    boolean b = createTransaction(account1, account2, type, sum);
                    if (checkBankSame(account1, account2) && b) {
                        createTransaction(account2, account1, TypeTransaction.REPLENISHMENT_FROM, sum);
                    } else replenishBalance(account2, sum);
                    break;
                default:
                    System.err.println(INCORRECT_TYPE);
                    break;
            }
        } else System.err.println(NOT_CLIENT);
    }

    /**
     * This method checks whether the accounts have the same banks or not.
     */
    @Override
    public Boolean checkBankSame(Account account1, Account account2) {
        return account1.getBank().equals(account2.getBank());
    }

    /**
     * This method creates a transaction. And depending on the type of transaction,
     * it calls the method{@link TransactionServiceImpl#replenishBalance(Account, Double) }
     * or the method{@link TransactionServiceImpl#withdrawalsBalance(Account, Double) }.
     * This method saves the transaction to the database and saves the receipt.
     *
     * @return true if everything went well. And return false if there is not enough money in the account for the transfer.
     */
    @Override
    public Boolean createTransaction(Account account1, Account account2, TypeTransaction type, Double sum) {
        switch (type) {
            case WITHDRAWALS:
            case TRANSFER_TO_CARD:
                if (!withdrawalsBalance(account1, sum)) {
                    return false;
                }
                break;
            case REPLENISHMENT:
            case REPLENISHMENT_FROM:
                replenishBalance(account1, sum);
                break;
        }
        Transaction transaction = new TransactionBuilder(type).setSum(sum).setDateTime(LocalDateTime.now())
                .setAccount1(account1).setAccount2(account2).build();
        save(transaction);
        printCheckService.saveCheckToFile(transaction);
        return true;
    }

    /**
     * This method increases the account balance by the amount of the transaction and saves the changes.
     */
    @Override
    public void replenishBalance(Account account, Double sum) {
        Double balance = account.getBalance() + sum;
        account.setBalance(balance);
        accountService.update(account);
    }

    /**
     * This method reduces the account balance by the amount of the transaction and saves the changes.
     * It also checks that the balance is greater than the transaction amount
     *
     * @return true if everything went well. And return false if there is not enough money in the account for the transfer.
     */
    @Override
    public boolean withdrawalsBalance(Account account, Double sum) {
        Double balance = account.getBalance();
        if (balance < sum) {
            System.err.println(NOT_ENOUGH_FUNDS);
            return false;
        }
        Double newBalance = account.getBalance() - sum;
        account.setBalance(newBalance);
        accountService.update(account);
        return true;
    }

}
