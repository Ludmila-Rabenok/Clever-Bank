package service;

import model.Bank;

import java.util.List;

public interface BankService {
    Bank save(Bank bank);

    List<Bank> getAll();

    Bank getById(Integer id);

    Boolean update(Bank bank);

    boolean delete(Integer id);
}
