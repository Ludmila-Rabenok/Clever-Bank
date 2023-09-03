package repository;

import exception.RepositoryException;
import model.Entity;

import java.util.List;

/**
 * Parameterized repositories interface.
 *
 * @param <T> Classes that extends the abstract Entity class.
 */
public interface Repository<T extends Entity> {
    T save(T entity) throws RepositoryException;

    T getById(Integer id) throws RepositoryException;

    List<T> getAll() throws RepositoryException;

    boolean update(T entity) throws RepositoryException;

    boolean remove(Integer id) throws RepositoryException;

}
