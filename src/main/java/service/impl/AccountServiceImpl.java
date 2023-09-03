package service.impl;

import model.Account;
import repository.Repository;
import service.AccountService;

import java.util.List;

/**
 * This is a service class for accounts. Contains methods for saving, deleting, getting, changing accounts
 * The class contains a reference to an instance of the repository class
 */
public class AccountServiceImpl implements AccountService {
    private final Repository<Account> accountRepository;

    public AccountServiceImpl(Repository<Account> accountRepository) {
        this.accountRepository = accountRepository;
    }

    /**
     * The method calls the method of saving the account in the database in the repository class
     *
     * @param account accepts an account to be stored in the database
     * @return returns the account stored in the database
     */
    @Override
    public Account save(Account account) {
        return accountRepository.save(account);
    }

    /**
     * The method calls a method in the repository class to get all accounts from the database
     *
     * @return a list with accounts from the database
     */
    @Override
    public List<Account> getAll() {
        return accountRepository.getAll();
    }

    /**
     * The method calls a method in the repository class to get the account by id from the database
     *
     * @return account by Id from the database
     */
    @Override
    public Account getById(Integer id) {
        return accountRepository.getById(id);
    }

    /**
     * The method calls a method in the repository class to change the account in the database
     *
     * @param account accepts an account to modify in the database.
     * @return returns the account that was changed
     */
    @Override
    public synchronized Boolean update(Account account) {
        return accountRepository.update(account);
    }

    /**
     * The method calls a method in the repository class to remove the account from the database
     *
     * @param id accepts the Integer id of the account to be deleted
     * @return true if the deletion was done correctly
     */
    @Override
    public boolean delete(Integer id) {
        return accountRepository.remove(id);
    }

}
