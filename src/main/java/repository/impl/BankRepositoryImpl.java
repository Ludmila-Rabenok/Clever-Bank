package repository.impl;

import exception.RepositoryException;
import model.Bank;
import repository.AbstractRepository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * This class has methods specific to the Bank entity.
 */
public class BankRepositoryImpl extends AbstractRepository<Bank> {
    /**
     * This method provides an SQL query to save the bank to the database.
     */
    @Override
    protected String getSaveQuery() {
        return "INSERT INTO banks(name) VALUES (?)";
    }

    /**
     * This method provides an SQL query to get all banks from the database.
     */
    @Override
    protected String getSelectAllQuery() {
        return "SELECT * FROM banks";
    }
    /**
     * This method provides a SQL query to get an bank by id from the database.
     */
    @Override
    protected String getSelectByIdQuery() {
        return "SELECT * FROM banks WHERE id = ?";
    }
    /**
     * This method provides a SQL query to change the bank in the database.
     */
    @Override
    protected String getUpdateQuery() {
        return "UPDATE banks SET name = ? WHERE id = ?";
    }
    /**
     * This method provides an SQL query to remove an bank from the database.
     */
    @Override
    protected String getRemoveQuery() {
        return "DELETE FROM banks WHERE id = ?";
    }
    /**
     * This method prepares a request to save the entity to the database.
     */
    @Override
    protected void prepareSaveQuery(PreparedStatement pst, Bank bank){
        try {
            pst.setString(1, bank.getName());
        } catch (SQLException e) {
            throw  new RepositoryException(e);
        }
    }
    /**
     * This method prepares a change request for an entity in the database.
     */
    @Override
    protected void prepareUpdateQuery(PreparedStatement pst, Bank bank) {
        try {
            prepareSaveQuery(pst, bank);
            pst.setInt(2, bank.getId());
        } catch (SQLException e) {
            throw new RepositoryException(e);
        }

    }
    /**
     *This method parses the ResultSet into list with entities.
     */
    @Override
    protected List<Bank> parseResultSet(ResultSet resultSet) {
        try {
            final List<Bank> banks = new ArrayList<>();
            while (resultSet.next()) {
                final Integer id = resultSet.getInt(1);
                final String name = resultSet.getString(2);
                final Bank bank = new Bank(name);
                bank.setId(id);
                banks.add(bank);
            }
            return banks;
        } catch (SQLException e) {
            throw new RepositoryException(e);
        }
    }
}
