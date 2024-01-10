package com.safeBankAB.safebankapp.services;


import com.safeBankAB.safebankapp.DataTransferObjects.AccountDTO;
import com.safeBankAB.safebankapp.DataTransferObjects.UserDTO;
import com.safeBankAB.safebankapp.httpRequestInput.CreateAccountInput;
import com.safeBankAB.safebankapp.httpRequestInput.UserCredentialsInput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.safeBankAB.safebankapp.utilities.Status;
import com.safeBankAB.safebankapp.model.entities.Account;
import com.safeBankAB.safebankapp.model.entities.EncryptedUserData;
import com.safeBankAB.safebankapp.repo.AccountRepo;
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional
    public AccountDTO createAccount(CreateAccountInput createAccountInput){

        if(InputValidator.checkCreateAccountInput(createAccountInput)) {

            EncryptedUserData encryptedUserData = encryptedUserDataService.createEncryptedUserPassword(createAccountInput.getPassword());
            UserDTO createdUser = userService.createUser(createAccountInput.getName(), createAccountInput.getSocialSecurityNumber(), encryptedUserData);

            if(createdUser.getStatus() == Status.USER_CREATED){
                Account newAccount = new Account(createdUser.getUser(), createAccountInput.getBankName(), createAccountInput.getAccountBalance());
                return new AccountDTO(accountRepo.save(newAccount), Status.ACCOUNT_CREATED);
            }
            return new AccountDTO(null, Status.USER_ACCOUNT_ALREADY_EXISTS);
        }
        return new AccountDTO(null,Status.BAD_ACCOUNT_REGISTRATION_INPUT);
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
