package repository.impl;

import exception.RepositoryException;
import model.Account;
import model.Bank;
import model.Currency;
import model.User;
import repository.AbstractRepository;
import repository.Repository;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * This class has methods specific to the Account entity.
 */
public class AccountRepositoryImpl extends AbstractRepository<Account> {
    /**
     * This method provides an SQL query to save the account to the database.
     */
    @Override
    protected String getSaveQuery() {
        return "INSERT INTO accounts(number, user_id, bank_id, currency, balance) " +
                "VALUES (?, ?, ?, ?, ?)";
    }

    /**
     * This method provides an SQL query to get all accounts from the database.
     */
    @Override
    protected String getSelectAllQuery() {
        return "SELECT * FROM accounts";
    }

    /**
     * This method provides a SQL query to get an account by id from the database.
     */
    @Override
    protected String getSelectByIdQuery() {
        return "SELECT * FROM accounts WHERE id = ?";
    }

    /**
     * This method provides a SQL query to change the account in the database.
     */
    @Override
    protected String getUpdateQuery() {
        return "UPDATE accounts SET number = ?, user_id = ?, bank_id = ?, currency = ?,date_open_account = ?," +
                "balance = ?, monthly_percentage = ?" + "WHERE id = ?";
    }

    /**
     * This method provides an SQL query to remove an account from the database.
     */
    @Override
    protected String getRemoveQuery() {
        return "DELETE FROM accounts WHERE id = ?";
    }

    /**
     * This method prepares a request to save the entity to the database.
     */
    @Override
    protected void prepareSaveQuery(PreparedStatement pst, Account account) {
        try {
            pst.setLong(1, account.getNumber());
            pst.setInt(2, account.getUser().getId());
            pst.setInt(3, account.getBank().getId());
            pst.setString(4, String.valueOf(account.getCurrency()));
            pst.setDouble(5, account.getBalance());
        } catch (SQLException e) {
            throw new RepositoryException(e);
        }
    }

    /**
     * This method prepares a change request for an entity in the database.
     */
    @Override
    protected void prepareUpdateQuery(PreparedStatement pst, Account account) {
        try {
            pst.setLong(1, account.getNumber());
            pst.setInt(2, account.getUser().getId());
            pst.setInt(3, account.getBank().getId());
            pst.setString(4, String.valueOf(account.getCurrency()));
            pst.setDate(5, Date.valueOf(account.getDateOpenAccount()));
            pst.setDouble(6, account.getBalance());
            pst.setDouble(7, account.getMonthlyPercentage());
            pst.setInt(8, account.getId());
        } catch (SQLException e) {
            throw new RepositoryException(e);
        }

    }

    /**
     *This method parses the ResultSet into list with entities.
     */
    @Override
    protected List<Account> parseResultSet(ResultSet resultSet) {
        try {
            final List<Account> accounts = new ArrayList<>();
            while (resultSet.next()) {
                final Integer id = resultSet.getInt(1);
                final Long number = resultSet.getLong(2);
                final Integer userId = resultSet.getInt(3);
                final Integer bankId = resultSet.getInt(4);
                final Currency currency = Currency.valueOf(resultSet.getString(5));
                final LocalDate dateOpenAccount = resultSet.getDate(6).toLocalDate();
                final Double balance = resultSet.getDouble(7);
                final Double monthlyPercentage = resultSet.getDouble(8);
                final Repository<User> userRepository = new UserRepositoryImpl();
                final User user = userRepository.getById(userId);
                final Repository<Bank> bankRepository = new BankRepositoryImpl();
                final Bank bank = bankRepository.getById(bankId);
                final Account account = new Account(number, user, bank, currency,
                        dateOpenAccount, balance, monthlyPercentage);
                account.setId(id);
                accounts.add(account);
            }
            return accounts;
        } catch (SQLException e) {
            throw new RepositoryException(e);
        }
    }

}
