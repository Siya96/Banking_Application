package com.safeBankAB.safebankapp.services;


import com.safeBankAB.safebankapp.DataTransferObjects.AccountDTO;
import com.safeBankAB.safebankapp.httpRequestInput.CreateAccountInput;
import com.safeBankAB.safebankapp.httpRequestInput.UserCredentialsInput;
import com.safeBankAB.safebankapp.utilities.InputValidator;
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

    public AccountDTO createAccount(CreateAccountInput createAccountInput){

        if(InputValidator.checkCreateAccountInput(createAccountInput)) {

            EncryptedUserData encryptedUserData = encryptedUserDataService.createEncryptedUserPassword(createAccountInput.getPassword());
            Pair<User, Status> createdUser = userService.createUser(createAccountInput.getName(), createAccountInput.getSocialSecurityNumber(), encryptedUserData);

            if(createdUser.getSecond() == Status.USER_CREATED){
                Account newAccount = new Account(createAccountInput.getBankName());
                newAccount.setUser(createdUser.getFirst());
                return new AccountDTO(accountRepo.save(newAccount), Status.ACCOUNT_CREATED);
            }
            return new AccountDTO(null, Status.USER_ACCOUNT_ALREADY_EXISTS);
        }
        return new AccountDTO(null,Status.BAD_ACCOUNT_REGISTRATION_INPUT);
    }

    public AccountDTO checkAccountBalance(UserCredentialsInput userCredentialsInput) {

        if(InputValidator.checkUserCredentialsInput(userCredentialsInput)) {

            return switch (userService.verifyUser(userCredentialsInput)) {

                case SUCCESSFUL_AUTHENTICATION -> new AccountDTO(accountRepo.findByUser(userService.getUser(userCredentialsInput.getName(),
                                userCredentialsInput.getSocialSecurityNumber()).get())
                        .get(), Status.SUCCESSFUL_AUTHENTICATION);
                case FAILED_AUTHENTICATION -> new AccountDTO(null, Status.FAILED_AUTHENTICATION);
                case USER_DOES_NOT_EXIST -> new AccountDTO(null, Status.USER_DOES_NOT_EXIST);
                default -> new AccountDTO(null, Status.UNKNOWN_ERROR);
            };
        }
        return new AccountDTO(null, Status.BAD_USER_CREDENTIALS_INPUT);
    }


}
