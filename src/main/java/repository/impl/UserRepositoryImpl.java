package repository.impl;

import exception.RepositoryException;
import model.User;
import repository.AbstractRepository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * This class has methods specific to the User entity.
 */
public class UserRepositoryImpl extends AbstractRepository<User> {

    /**
     * This method provides an SQL query to save the user to the database.
     */
    @Override
    protected String getSaveQuery() {
        return "INSERT INTO users(full_name) VALUES (?)";
    }

    /**
     * This method provides an SQL query to get all users from the database.
     */
    @Override
    protected String getSelectAllQuery() {
        return "SELECT * FROM users";
    }

    /**
     * This method provides a SQL query to get an user by id from the database.
     */
    @Override
    protected String getSelectByIdQuery() {
        return "SELECT * FROM users WHERE id = ?";
    }

    /**
     * This method provides a SQL query to change the user in the database.
     */
    @Override
    protected String getUpdateQuery() {
        return "UPDATE users SET full_name = ? WHERE id = ?";
    }

    /**
     * This method provides an SQL query to remove an user from the database.
     */
    @Override
    protected String getRemoveQuery() {
        return "DELETE FROM users WHERE id = ?";
    }

    /**
     * This method prepares a request to save the entity to the database.
     */
    @Override
    protected void prepareSaveQuery(PreparedStatement pst, User user) {
        try {
            pst.setString(1, user.getFullName());
        } catch (SQLException e) {
            throw new RepositoryException(e);
        }
    }

    /**
     * This method prepares a change request for an entity in the database.
     */
    @Override
    protected void prepareUpdateQuery(PreparedStatement pst, User user) {
        try {
            prepareSaveQuery(pst, user);
            pst.setInt(2, user.getId());
        } catch (SQLException e) {
            throw new RepositoryException(e);
        }
    }

    /**
     * This method parses the ResultSet into list with entities.
     */
    @Override
    protected List<User> parseResultSet(ResultSet resultSet) {
        try {
            final List<User> users = new ArrayList<>();
            while (resultSet.next()) {
                final Integer id = resultSet.getInt(1);
                final String fullName = resultSet.getString(2);
                final User user = new User(fullName);
                user.setId(id);
                users.add(user);
            }
            return users;
        } catch (SQLException e) {
            throw new RepositoryException(e);
        }
    }
}
