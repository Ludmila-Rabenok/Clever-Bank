package service.impl;

import model.User;
import repository.Repository;
import service.UserService;

import java.util.List;

/**
 * This is a service class for users. Contains methods for saving, deleting, getting, changing users
 * The class contains a reference to an instance of the repository class
 */
public class UserServiceImpl implements UserService {
    Repository<User> userRepository;

    public UserServiceImpl(Repository<User> userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * The method calls the method of saving the user in the database in the repository class
     *
     * @param user accepts an user to be stored in the database
     * @return returns the user stored in the database
     */
    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    /**
     * The method calls a method in the repository class to get all users from the database
     *
     * @return a list with users from the database
     */
    @Override
    public List<User> getAll() {
        return userRepository.getAll();
    }

    /**
     * The method calls a method in the repository class to get the user by id from the database
     *
     * @return user by Id from the database
     */
    @Override
    public User getById(Integer id) {
        return userRepository.getById(id);
    }

    /**
     * The method calls a method in the repository class to change the user in the database
     *
     * @param user accepts an user to modify in the database.
     * @return returns the user that was changed
     */
    @Override
    public Boolean update(User user) {
        return userRepository.update(user);
    }

    /**
     * The method calls a method in the repository class to remove the user from the database
     *
     * @param id accepts the Integer id of the user to be deleted
     * @return true if the deletion was done correctly
     */
    @Override
    public boolean delete(Integer id) {
        return userRepository.remove(id);
    }

}
