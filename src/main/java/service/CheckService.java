package service;

import model.Check;
import model.Transaction;

import java.util.List;

public interface CheckService {
    Check save(Check check);

    List<Check> getAll();

    Check getById(Integer id);

    boolean delete(Integer id);

    Check create(Transaction transaction);
}
