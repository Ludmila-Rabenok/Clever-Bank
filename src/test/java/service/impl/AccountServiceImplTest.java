package service.impl;

import model.Account;
import model.Bank;
import model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import repository.Repository;
import service.AccountService;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AccountServiceImplTest {
    private final Repository<Account> accountRepository = mock(Repository<Account>.class);

    private AccountService accountService;
    private static Account expectedAccount;
    private static List<Account> expectedAccountList;

    @BeforeAll
    public static void init() {
        expectedAccount = new Account();
        expectedAccount.setId(1);
        expectedAccount.setNumber(123456789L);
        User user = new User();
        user.setId(1);
        ;
        user.setFullName("Тестов Тест Тестович");
        expectedAccount.setUser(user);
        Bank bank = new Bank();
        bank.setId(1);
        bank.setName("Test-Bank");
        expectedAccount.setBank(bank);
        expectedAccountList = new ArrayList<>();
        expectedAccountList.add(expectedAccount);
    }

    @BeforeEach
    public void prepareTestData() {
        accountService = new AccountServiceImpl(accountRepository);
    }

    @Test
    void save() {
        when(accountRepository.save(any(Account.class))).thenReturn(expectedAccount);

        Account actualAccount = accountService.save(expectedAccount);

        verify(accountRepository, times(1)).save(expectedAccount);
        assertNotNull(actualAccount);
        assertSame(expectedAccount, actualAccount);
    }

    @Test
    void getAll() {
        when(accountRepository.getAll()).thenReturn(expectedAccountList);

        List<Account> accountList = accountService.getAll();

        assertEquals(1, accountList.size());
        verify(accountRepository, times(1)).getAll();
    }

}
