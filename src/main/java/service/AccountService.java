package service;

import model.Account;

import java.util.List;

public interface AccountService {
    Account save(Account account);

    List<Account> getAll();

    Account getById(Integer id);

    Boolean update(Account account);

    boolean delete(Integer id);

}
