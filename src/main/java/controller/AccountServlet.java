package controller;


import model.Account;
import model.Bank;
import model.Currency;
import model.User;
import repository.Repository;
import repository.impl.AccountRepositoryImpl;
import repository.impl.BankRepositoryImpl;
import repository.impl.UserRepositoryImpl;
import service.AccountService;
import service.BankService;
import service.UserService;
import service.impl.AccountServiceImpl;
import service.impl.BankServiceImpl;
import service.impl.UserServiceImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * This servlet acts as a page controller for the application, handling all requests from the user.
 */
public class AccountServlet extends BaseServlet {
    private AccountService accountService;
    private BankService bankService;
    private UserService userService;

    public void init() {
        Repository<Account> accountRepository = new AccountRepositoryImpl();
        Repository<Bank> bankRepository = new BankRepositoryImpl();
        Repository<User> userRepository = new UserRepositoryImpl();

        accountService = new AccountServiceImpl(accountRepository);
        bankService = new BankServiceImpl(bankRepository);
        userService = new UserServiceImpl(userRepository);
    }

    /**
     * http://localhost:8080/Account/create?number=1234567&userId=1&bankId=1,currency=BYN&balance=200.05
     */
    public void create(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html;charset=UTF-8");

        Long number = Long.parseLong(request.getParameter("number"));
        Integer userId = Integer.parseInt(request.getParameter("userId"));
        Integer bankId = Integer.parseInt(request.getParameter("bankId"));
        Currency currency = Currency.valueOf(request.getParameter("currency"));
        Double balance = Double.parseDouble(request.getParameter("balance"));

        Account account = new Account();
        account.setNumber(number);
        account.setUser(userService.getById(userId));
        account.setBank(bankService.getById(bankId));
        account.setCurrency(currency);
        account.setBalance(balance);
        if (accountService.save(account) != null) {
            response.getWriter().print("Account was save");
        } else response.getWriter().print("Account was not save");
    }

    /**
     * http://localhost:8080/Account/delete?id=1
     */
    public void delete(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html;charset=UTF-8");
        String accountId = request.getParameter("id");
        int id = Integer.parseInt(accountId);
        if (accountService.delete(id)) {
            response.getWriter().print("Account with id =" + id + " was remove");
        } else
            response.getWriter().print("Account with id =" + id + " was not remove");
    }

}
