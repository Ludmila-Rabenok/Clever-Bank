package repository;

import exception.RepositoryException;
import model.Entity;
import util.DBConnector;

import java.sql.*;
import java.util.List;

/**
 * An abstract class containing implemented parameterized CRUD methods.
 *
 * @param <T> Classes that extends the abstract Entity class.
 */
public abstract class AbstractRepository<T extends Entity> implements Repository<T> {

    /**
     * This method saves the entity passed in the parameters to the database. And returns it.
     *
     * @throws RepositoryException if invalid generated id.
     */
    @Override
    public final T save(T entity) {
        try (Connection connection = DBConnector.getConnection();
             PreparedStatement pst = connection
                     .prepareStatement(getSaveQuery(), Statement.RETURN_GENERATED_KEYS)) {
            prepareSaveQuery(pst, entity);
            final int count = pst.executeUpdate();
            if (count == 1) {
                ResultSet rs = pst.getGeneratedKeys();
                if (rs.next()) {
                    entity.setId(rs.getInt(1));
                } else {
                    throw new RepositoryException("Invalid generated id");
                }
            }
            return entity;
        } catch (SQLException e) {
            throw new RepositoryException(e);
        }
    }

    /**
     * This method retrieves from the database all entities of the type specified in the generic.
     * And returns a list with these entities.
     *
     * @throws RepositoryException when connecting to a database.
     */
    @Override
    public final List<T> getAll() {
        try (Connection connection = DBConnector.getConnection();
             Statement statement = connection.createStatement()) {
            final ResultSet resultSet = statement.executeQuery(getSelectAllQuery());
            return parseResultSet(resultSet);
        } catch (SQLException e) {
            throw new RepositoryException(e);
        }
    }

    /**
     * This method retrieves an entity from the database by its id. And returns it.
     *
     * @throws RepositoryException when connecting to a database.
     */
    @Override
    public final T getById(Integer id) {
        try (Connection connection = DBConnector.getConnection();
             PreparedStatement pst = connection
                     .prepareStatement(getSelectByIdQuery())) {
            pst.setInt(1, id);
            final ResultSet resultSet = pst.executeQuery();
            final List<T> resultSetList = parseResultSet(resultSet);
            if (resultSetList.isEmpty()) {
                return null;
            }
            return resultSetList.iterator().next();
        } catch (SQLException e) {
            throw new RepositoryException(e);
        }
    }

    /**
     * This method changes an entity in the database.
     *
     * @param entity This is a new entity that needs to be replaced with the old one.
     * @return true if the entity has been changed.
     * @throws RepositoryException when connecting to a database.
     */
    @Override
    public final synchronized boolean update(T entity) {
        try (Connection connection = DBConnector.getConnection();
             PreparedStatement pst = connection.prepareStatement(getUpdateQuery())) {
            prepareUpdateQuery(pst, entity);
            return pst.executeUpdate() == 1;
        } catch (SQLException e) {
            throw new RepositoryException(e);
        }
    }

    /**
     * This is a method that removes an entity from the database.
     *
     * @param id of the entity to be removed.
     * @return true if the entity has been remove.
     */
    @Override
    public final boolean remove(Integer id) {
        try (Connection connection = DBConnector.getConnection();
             PreparedStatement pst = connection.prepareStatement(getRemoveQuery())) {
            pst.setInt(1, id);
            return pst.executeUpdate() == 1;
        } catch (SQLException e) {
            throw new RepositoryException(e);
        }
    }

    protected abstract String getSelectAllQuery();

    protected abstract String getSelectByIdQuery();

    protected abstract String getSaveQuery();

    protected abstract String getUpdateQuery();

    protected abstract String getRemoveQuery();

    protected abstract List<T> parseResultSet(ResultSet resultSet);

    protected abstract void prepareSaveQuery(PreparedStatement pst, T entity);

    protected abstract void prepareUpdateQuery(PreparedStatement pst, T entity);
}
