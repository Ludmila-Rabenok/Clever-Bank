package service.impl;

import model.Check;
import model.Transaction;
import repository.Repository;
import service.CheckService;

import java.util.List;

/**
 * This is a service class for checks. Contains methods for saving, deleting, getting, creating checks
 * The class contains a reference to an instance of the repository class
 */
public class CheckServiceImpl implements CheckService {
    private final Repository<Check> checkRepository;

    public CheckServiceImpl(Repository<Check> checkRepository) {
        this.checkRepository = checkRepository;
    }

    /**
     * The method calls the method of saving the check in the database in the repository class
     *
     * @param check accepts an check to be stored in the database
     * @return returns the check stored in the database
     */
    @Override
    public Check save(Check check) {
        return checkRepository.save(check);
    }

    /**
     * The method calls a method in the repository class to get all checks from the database
     *
     * @return a list with checks from the database
     */
    @Override
    public List<Check> getAll() {
        return checkRepository.getAll();
    }

    /**
     * The method calls a method in the repository class to get the check by id from the database
     *
     * @return check by Id from the database
     */
    @Override
    public Check getById(Integer id) {
        return checkRepository.getById(id);
    }

    /**
     * The method calls a method in the repository class to remove the check from the database
     *
     * @param id accepts the Integer id of the check to be deleted
     * @return true if the deletion was done correctly
     */
    @Override
    public boolean delete(Integer id) {
        return checkRepository.remove(id);
    }

    @Override
    public Check create(Transaction transaction) {
        Check check = new Check(transaction);
        return save(check);
    }

}
