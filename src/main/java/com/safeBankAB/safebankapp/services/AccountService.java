package com.safeBankAB.safebankapp.services;


import com.safeBankAB.safebankapp.DataTransferObjects.CreatedAccountDTO;
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

    public CreatedAccountDTO createAccount(CreateAccountInput createAccountInput){

        if(InputValidator.checkCreateAccountInput(createAccountInput)) {

            EncryptedUserData encryptedUserData = encryptedUserDataService.createEncryptedUserPassword(createAccountInput.getPassword());
            Pair<User, Status> createdUser = userService.createUser(createAccountInput.getName(), createAccountInput.getSocialSecurityNumber(), encryptedUserData);

            if(createdUser.getSecond() == Status.USER_CREATED){
                Account newAccount = new Account(createAccountInput.getBankName());
                newAccount.setUser(createdUser.getFirst());
                return new CreatedAccountDTO(accountRepo.save(newAccount), Status.ACCOUNT_CREATED);
            }
            return new CreatedAccountDTO(null, Status.USER_ACCOUNT_ALREADY_EXISTS);
        }
        return new CreatedAccountDTO(null,Status.BAD_ACCOUNT_REGISTRATION_INPUT);
    }

    public double checkAccountBalance(UserCredentialsInput userCredentialsInput) {
        if(InputValidator.checkUserCredentialsInput(userCredentialsInput)) {
            return switch (userService.verifyUser(userCredentialsInput)) {
                case SUCCESSFUL_AUTHENTICATION ->
                        accountRepo.findByUser(userService.getUser(userCredentialsInput.getName(), userCredentialsInput.getSocialSecurityNumber()).get())
                                .get()
                                .getAccountBalance();
                case FAILED_AUTHENTICATION -> 0.001;
                case USER_DOES_NOT_EXIST -> 0.002;
                default -> 0.002;
            };
        }
        return 0.004;
    }


}
