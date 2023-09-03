package service;

import model.User;

import java.util.List;

public interface UserService {
    User save(User user);

    List<User> getAll();

    User getById(Integer id);

    Boolean update(User user);

    boolean delete(Integer id);
}
