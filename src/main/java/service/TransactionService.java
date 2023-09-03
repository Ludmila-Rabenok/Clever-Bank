package service;

import model.Account;
import model.Transaction;
import model.TypeTransaction;

import java.util.List;

public interface TransactionService {
    Transaction save(Transaction transaction);

    List<Transaction> getAll();

    Transaction getById(Integer id);

    boolean delete(Integer id);

    void doTransaction(Account account1, Account account2, TypeTransaction type, Double sum);

    Boolean createTransaction(Account account1, Account account2, TypeTransaction type, Double sum);

    Boolean checkBankSame(Account account1, Account account2);

    void replenishBalance(Account account, Double sum);

    boolean withdrawalsBalance(Account account, Double sum);

}
