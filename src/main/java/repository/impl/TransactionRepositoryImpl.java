package repository.impl;

import exception.RepositoryException;
import model.Account;
import model.Transaction;
import model.TransactionBuilder;
import model.TypeTransaction;
import repository.AbstractRepository;
import repository.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * This class has methods specific to the Transaction entity.
 */
public class TransactionRepositoryImpl extends AbstractRepository<Transaction> {
    /**
     * This method provides an SQL query to save the transaction to the database.
     */
    @Override
    protected String getSaveQuery() {
        return "INSERT INTO transactions(type_transaction, sum, account1_id, account2_id) " +
                "VALUES (?, ?, ?, ?)";
    }

    /**
     * This method provides an SQL query to get all transaction from the database.
     */
    @Override
    protected String getSelectAllQuery() {
        return "SELECT * FROM transactions";
    }

    /**
     * This method provides a SQL query to get an transaction by id from the database.
     */
    @Override
    protected String getSelectByIdQuery() {
        return "SELECT * FROM transactions WHERE id = ?";
    }

    /**
     * This method provides a SQL query to change the transaction in the database.
     */
    @Override
    protected String getUpdateQuery() {
        return "UPDATE transactions SET type_transaction = ?, sum = ?, account1_id = ?, account2_id = ?" +
                "WHERE id = ?";
    }

    /**
     * This method provides an SQL query to remove an transaction from the database.
     */
    @Override
    protected String getRemoveQuery() {
        return "DELETE FROM transactions WHERE id = ?";
    }

    /**
     * This method prepares a request to save the entity to the database.
     */
    @Override
    protected void prepareSaveQuery(PreparedStatement pst, Transaction transaction) {
        try {
            pst.setString(1, String.valueOf(transaction.getTypeTransaction()));
            pst.setDouble(2, transaction.getSum());
            pst.setInt(3, transaction.getAccount1().getId());
            pst.setInt(4, transaction.getAccount2().getId());
        } catch (SQLException e) {
            throw new RepositoryException(e);
        }
    }

    /**
     * This method prepares a change request for an entity in the database.
     */
    @Override
    protected void prepareUpdateQuery(PreparedStatement pst, Transaction transaction) {
        try {
            prepareSaveQuery(pst, transaction);
            pst.setInt(5, transaction.getId());
        } catch (SQLException e) {
            throw new RepositoryException(e);
        }
    }

    /**
     * This method parses the ResultSet into list with entities.
     */
    @Override
    protected List<Transaction> parseResultSet(ResultSet resultSet) {
        try {
            final List<Transaction> transactions = new ArrayList<>();
            while (resultSet.next()) {
                final Integer id = resultSet.getInt(1);
                final LocalDateTime dateTime = resultSet.getObject(2, LocalDateTime.class);
                final TypeTransaction type = TypeTransaction.valueOf(resultSet.getString(3));
                final Double sum = resultSet.getDouble(4);
                final Integer account1Id = resultSet.getInt(5);
                final Integer account2Id = resultSet.getInt(6);
                final Repository<Account> accountRepository = new AccountRepositoryImpl();
                final Account account1 = accountRepository.getById(account1Id);
                final Account account2 = accountRepository.getById(account2Id);
                final Transaction transaction = new TransactionBuilder(type).setDateTime(dateTime).setSum(sum)
                        .setAccount1(account1).setAccount2(account2).build();
                transactions.add(transaction);
            }
            return transactions;
        } catch (SQLException e) {
            throw new RepositoryException(e);
        }
    }
}
