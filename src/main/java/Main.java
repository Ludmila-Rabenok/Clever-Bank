import model.*;
import repository.Repository;
import repository.impl.*;
import service.*;
import service.impl.*;

public class Main {
    public static void main(String[] args) {
        Repository<User> userRepository = new UserRepositoryImpl();
        UserService userService = new UserServiceImpl(userRepository);
        Repository<Bank> bankRepository = new BankRepositoryImpl();
        BankService bankService = new BankServiceImpl(bankRepository);
        Repository<Check> checkRepository = new CheckRepositoryImpl();
        CheckService checkService = new CheckServiceImpl(checkRepository);
        Repository<Account> accountRepository = new AccountRepositoryImpl();
        AccountService accountService = new AccountServiceImpl(accountRepository);
        PrintCheckService printCheckService = new PrintCheckServiceImpl(checkService);
        Repository<Transaction> transactionRepository = new TransactionRepositoryImpl();
        TransactionService transactionService = new TransactionServiceImpl(transactionRepository,
                accountService, printCheckService);
        InterestCalculation interestCalculation = new InterestCalculationImpl(accountService, transactionService);

        interestCalculation.interestCalculationManager();

        transactionService.doTransaction(accountService.getById(1), accountService.getById(2),
                TypeTransaction.TRANSFER_TO_CARD, 20D);
    }
}
