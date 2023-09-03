package repository.impl;

import exception.RepositoryException;
import model.Check;
import model.Transaction;
import repository.AbstractRepository;
import repository.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * This class has methods specific to the Check entity.
 */
public class CheckRepositoryImpl extends AbstractRepository<Check> {
    /**
     * This method provides an SQL query to save the check to the database.
     */
    @Override
    protected String getSaveQuery() {
        return "INSERT INTO checks(transaction_id) VALUES (?)";
    }

    /**
     * This method provides an SQL query to get all checks from the database.
     */
    @Override
    protected String getSelectAllQuery() {
        return "SELECT * FROM checks";
    }
    /**
     * This method provides a SQL query to get an check by id from the database.
     */
    @Override
    protected String getSelectByIdQuery() {
        return "SELECT * FROM checks WHERE id = ?";
    }
    /**
     * This method provides a SQL query to change the check in the database.
     */
    @Override
    protected String getUpdateQuery() {
        return "UPDATE checks SET transaction_id = ? WHERE id = ?";
    }
    /**
     * This method provides an SQL query to remove an check from the database.
     */
    @Override
    protected String getRemoveQuery() {
        return "DELETE FROM checks WHERE id = ?";
    }
    /**
     * This method prepares a request to save the entity to the database.
     */
    @Override
    protected void prepareSaveQuery(PreparedStatement pst, Check check) {
        try {
            pst.setInt(1, check.getTransaction().getId());
        } catch (SQLException e) {
            throw new RepositoryException(e);
        }
    }
    /**
     * This method prepares a change request for an entity in the database.
     */
    @Override
    protected void prepareUpdateQuery(PreparedStatement pst, Check check){
        try {
            prepareSaveQuery(pst, check);
            pst.setInt(2, check.getId());
        } catch (SQLException e) {
            throw new RepositoryException(e);
        }
    }
    /**
     *This method parses the ResultSet into list with entities.
     */
    @Override
    protected List<Check> parseResultSet(ResultSet resultSet){
        try {
            final List<Check> checks = new ArrayList<>();
            while (resultSet.next()) {
                final Integer id = resultSet.getInt(1);
                final Integer transaction_id = resultSet.getInt(2);
                final Repository<Transaction> tr = new TransactionRepositoryImpl();
                final Transaction transaction = tr.getById(transaction_id);
                final Check check = new Check(transaction);
                check.setId(id);
                checks.add(check);
            }
            return checks;
        } catch (SQLException e) {
            throw new RepositoryException(e);
        }
    }
}
