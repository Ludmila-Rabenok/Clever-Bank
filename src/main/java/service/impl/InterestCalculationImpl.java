package service.impl;

import model.Account;
import model.Transaction;
import service.AccountService;
import service.InterestCalculation;
import service.TransactionService;
import util.YamlReader;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 * This class starts two threads. Which regularly, according to the schedule,
 * check whether it is necessary to accrue interest on the account balance at the end of the month.
 * The class contains references to instances of service classes.
 */
public class InterestCalculationImpl implements InterestCalculation {

    private final AccountService accountService;
    private final TransactionService transactionService;
    /**
     * The percentage of accruals is taken from the configuration file.
     */
    private final Integer percent = Integer.valueOf(YamlReader.get("percent"));

    public InterestCalculationImpl(AccountService accountService, TransactionService transactionService) {
        this.accountService = accountService;
        this.transactionService = transactionService;
    }

    /**
     * This method is the manager. It starts two threads.
     */
    @Override
    public void interestCalculationManager() {
        InterestCalculationImpl.ThreadCheckInterestCalculation threadCheckInterestCalculation = new ThreadCheckInterestCalculation();
        InterestCalculationImpl.ThreadInterestCalculation threadInterestCalculation = new ThreadInterestCalculation();
        threadCheckInterestCalculation.start();
        threadInterestCalculation.start();
    }

    /**
     * This thread checks once every half a minute whether it is necessary to charge 1%.
     * If necessary, saves one percent of the balance in a variable {@link Account#setMonthlyPercentage(Double)}.
     * And the changes are saved.
     */
    class ThreadCheckInterestCalculation extends Thread {

        @Override
        public void run() {
            while (true) {
                try {
                    Thread.sleep(30000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                LocalDateTime initial = LocalDateTime.now();
                Set<Integer> accountSet = new HashSet<>();
                for (Transaction transaction : transactionService.getAll()) {
                    LocalDateTime dateTimeTransaction = transaction.getDateTime();
                    if (Duration.between(dateTimeTransaction, initial).getSeconds() <= 30) {
                        Account account = transaction.getAccount1();
                        if (!accountSet.contains(account.getId())) {
                            accountSet.add(account.getId());
                            Double percentFromBalance = account.getBalance() / 100 * percent;
                            account.setMonthlyPercentage(account.getMonthlyPercentage() + percentFromBalance);
                            accountService.update(account);
                        }
                    }
                }
            }
        }
    }

    /**
     * This thread at the end of the month adds to the balance the amount of interest
     */
    class ThreadInterestCalculation extends Thread {
        @Override
        public void run() {
            while (true) {
                LocalDate initial = LocalDate.now();
                LocalDate endDay = initial.withDayOfMonth(initial.lengthOfMonth());
                if (initial.equals(endDay)) {
                    for (Account account : accountService.getAll()) {
                        account.setBalance(account.getBalance() + account.getMonthlyPercentage());
                        accountService.update(account);
                    }
                }
                try {
                    Thread.sleep(86400000);//1 день
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
