package com.safeBankAB.safebankapp.services;


import com.safeBankAB.safebankapp.api.models.responses.AccountDTO;
import com.safeBankAB.safebankapp.api.models.responses.UserDTO;
import com.safeBankAB.safebankapp.api.models.requests.CreateAccountInput;
import com.safeBankAB.safebankapp.api.models.requests.UserCredentialsInput;
import com.safeBankAB.safebankapp.utilities.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.safeBankAB.safebankapp.utilities.Status;
import com.safeBankAB.safebankapp.repository.entitymodels.Account;
import com.safeBankAB.safebankapp.repository.entitymodels.EncryptedUserData;
import com.safeBankAB.safebankapp.repository.AccountRepo;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

@Service
public class AccountService {

    private final AccountRepo accountRepo;
    private final UserService userService;
    private final EncryptedUserDataService encryptedUserDataService;

    private final Random random;

    @Autowired
    public AccountService(AccountRepo accountRepo, UserService userService, EncryptedUserDataService encryptedUserDataService) {
        this.accountRepo = accountRepo;
        this.userService = userService;
        this.encryptedUserDataService = encryptedUserDataService;
        this.random = new Random();
    }

    @Transactional
    public AccountDTO createAccount(CreateAccountInput createAccountInput){

        if(InputValidator.checkCreateAccountInput(createAccountInput)) {

            EncryptedUserData encryptedUserData = encryptedUserDataService.createEncryptedUserPassword(createAccountInput.getPassword());
            UserDTO createdUser = userService.createUser(createAccountInput.getName(), createAccountInput.getSocialSecurityNumber(), encryptedUserData);

            if(createdUser.getStatus() == Status.USER_CREATED){
                Account newAccount = new Account(createdUser.getUser(), createAccountInput.getBankName(), assignUniqueAccountNumber(), createAccountInput.getAccountBalance());
                return new AccountDTO(accountRepo.save(newAccount), Status.ACCOUNT_CREATED);
            }
            return new AccountDTO(null, Status.USER_ACCOUNT_ALREADY_EXISTS);
        }
        return new AccountDTO(null,Status.BAD_ACCOUNT_REGISTRATION_INPUT);
    }

    private long assignUniqueAccountNumber() {
        Set<Long> accountNumbers = new HashSet<>();
        accountRepo.findAll()
                .forEach(account -> accountNumbers.add(account.getAccountNumber()));
        long newAccountNumber = Math.abs(random.nextLong() % Constants.ACCOUNT_NUMBER_SUFFIX_MODULO) + Constants.ACCOUNT_NUMBER_PREFIX_MODULO;
        while(accountNumbers.contains(newAccountNumber)) {
            newAccountNumber = Math.abs(random.nextLong() % Constants.ACCOUNT_NUMBER_SUFFIX_MODULO) + Constants.ACCOUNT_NUMBER_PREFIX_MODULO;
        }

        return newAccountNumber;
    }

    public AccountDTO checkAccountBalance(UserCredentialsInput userCredentialsInput) {

        if(InputValidator.checkUserCredentialsInput(userCredentialsInput)) {

            //Maybe let .verifyUserreturn Optional and use ifPrsentOrEse() from there to perform necessary action
            return switch (userService.verifyUser(userCredentialsInput)) {

                case SUCCESSFUL_AUTHENTICATION -> createAccountDTO(userCredentialsInput.getName(), userCredentialsInput.getSocialSecurityNumber(), Status.SUCCESSFUL_AUTHENTICATION);
                case FAILED_AUTHENTICATION -> createAccountDTO(null, null, Status.FAILED_AUTHENTICATION);
                case USER_NOT_FOUND -> createAccountDTO(null, null, Status.USER_NOT_FOUND);
                default -> createAccountDTO(null, null, Status.UNKNOWN_ERROR);
            };
        }
        return createAccountDTO(null, null, Status.BAD_USER_CREDENTIALS_INPUT);
    }

    private AccountDTO createAccountDTO(String username, String socialSecurityNumber, Status status) {
        return userService.getUser(username, socialSecurityNumber).isPresent() ?
                        accountRepo.findByUser(userService.getUser(username, socialSecurityNumber).get()).isPresent() ?
                                new AccountDTO(accountRepo.findByUser(userService.getUser(username, socialSecurityNumber).get()).get(), status)
                                :  new AccountDTO(null, Status.ACCOUNT_NOT_FOUND)
                        : new AccountDTO(null, status);

    }


}
