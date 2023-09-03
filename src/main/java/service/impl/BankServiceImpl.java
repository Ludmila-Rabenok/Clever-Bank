package service.impl;

import model.Bank;
import repository.Repository;
import service.BankService;

import java.util.List;

/**
 * This is a service class for banks. Contains methods for saving, deleting, getting, changing banks
 * The class contains a reference to an instance of the repository class
 */
public class BankServiceImpl implements BankService {
    private final Repository<Bank> bankRepository;

    public BankServiceImpl(Repository<Bank> bankRepository) {
        this.bankRepository = bankRepository;
    }

    /**
     * The method calls the method of saving the bank in the database in the repository class
     *
     * @param bank accepts an bank to be stored in the database
     * @return returns the bank stored in the database
     */
    @Override
    public Bank save(Bank bank) {
        return bankRepository.save(bank);
    }

    /**
     * The method calls a method in the repository class to get all banks from the database
     *
     * @return a list with banks from the database
     */
    @Override
    public List<Bank> getAll() {
        return bankRepository.getAll();
    }

    /**
     * The method calls a method in the repository class to get the bank by id from the database
     *
     * @return bank by Id from the database
     */
    @Override
    public Bank getById(Integer id) {
        return bankRepository.getById(id);
    }

    /**
     * The method calls a method in the repository class to change the bank in the database
     *
     * @param bank accepts an bank to modify in the database.
     * @return returns the bank that was changed
     */
    @Override
    public Boolean update(Bank bank) {
        return bankRepository.update(bank);
    }

    /**
     * The method calls a method in the repository class to remove the bank from the database
     *
     * @param id accepts the Integer id of the bank to be deleted
     * @return true if the deletion was done correctly
     */
    @Override
    public boolean delete(Integer id) {
        return bankRepository.remove(id);
    }

}
