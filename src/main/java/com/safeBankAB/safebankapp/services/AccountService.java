package com.safeBankAB.safebankapp.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;
import com.safeBankAB.safebankapp.constantsEnumerationsAndPatterns.Status;
import com.safeBankAB.safebankapp.model.Account;
import com.safeBankAB.safebankapp.model.EncryptedUserData;
import com.safeBankAB.safebankapp.model.User;
import com.safeBankAB.safebankapp.repo.AccountRepo;

@Service
public class AccountService {


    private final AccountRepo accountRepo;
    private final UserService userService;
    private final EncryptedUserDataService encryptedUserDataService;

    @Autowired
    public AccountService(AccountRepo accountRepo, UserService userService, EncryptedUserDataService encryptedUserDataService) {
        this.accountRepo = accountRepo;
        this.userService = userService;
        this.encryptedUserDataService = encryptedUserDataService;
    }



    public Account createAccount(User user, String bankName){


        /* TODO
        Verify if user exists
         */

        Account newAccount = new Account(bankName);
        newAccount.setUser(user);
        return accountRepo.save(newAccount);

    }
    public void CreateAccountTest() {

        String s = "Siya";
        String socsecnum = "960511-6847";
        String password = "hello";
        String bankName = "SEB";
        double accountBalance = 120000;

        String s2 = "Jackie";
        String socsecnum2 = "940218-7483";
        String password2 = "bye";
        String bankName2 = "Swedbank";
        double accountBalance2 = 109020000;

        EncryptedUserData e = encryptedUserDataService.createEncryptedUserPassword(password);
        EncryptedUserData e2 = encryptedUserDataService.createEncryptedUserPassword(password2);

        Pair<User, Status> pair = userService.createUser(s, socsecnum, e);
        Pair<User, Status> pair2 = userService.createUser(s2, socsecnum2, e2);

        Account asccc = new Account(bankName);


        Account acc = createAccount(pair.getFirst(), bankName);
        Account acc2 = createAccount(pair2.getFirst(), bankName2);


    }

}
