package com.safeBankAB.safebankapp.controllers;


import com.safeBankAB.safebankapp.DataTransferObjects.CreatedAccountDTO;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.safeBankAB.safebankapp.constantsEnumerationsAndPatterns.Constants;
import com.safeBankAB.safebankapp.constantsEnumerationsAndPatterns.Status;
import com.safeBankAB.safebankapp.httpRequestInput.CreateAccountInput;
import com.safeBankAB.safebankapp.httpRequestInput.GetAccountBalanceInput;
import com.safeBankAB.safebankapp.model.Account;
import com.safeBankAB.safebankapp.model.EncryptedUserData;
import com.safeBankAB.safebankapp.model.User;
import com.safeBankAB.safebankapp.services.AccountService;
import com.safeBankAB.safebankapp.services.EncryptedUserDataService;
import com.safeBankAB.safebankapp.services.UserService;
import com.safeBankAB.safebankapp.utilities.InputValidator;

import java.lang.invoke.MethodHandles;

@RestController
public class AccountRestController {

    private final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private final UserService userService;
    private final AccountService accountService;

    private final EncryptedUserDataService encryptedUserDataService;


    //Constructor-injection
    @Autowired
    public AccountRestController(UserService userService, AccountService accountService, EncryptedUserDataService encryptedUserDataService) {
        this.userService = userService;
        this.accountService = accountService;
        this.encryptedUserDataService = encryptedUserDataService;
    }

    @PostMapping(
            value = "/createAccount",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> createAccount(@Valid @RequestBody CreateAccountInput createAccountInput)  {

         CreatedAccountDTO createdAccountDTO = accountService.createAccount(createAccountInput);
        if (createdAccountDTO.getCreatedAccountStatus() == Status.ACCOUNT_CREATED) {
            return ResponseEntity.ok(createdAccountDTO);
        }
        else if (createdAccountDTO.getCreatedAccountStatus() == Status.USER_ALREADY_EXISTS) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(createdAccountDTO);
        }
        else {
            return ResponseEntity.unprocessableEntity().body(createdAccountDTO);
        }
    }
    @GetMapping(
            value = "/checkAccountBalance",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<String> getAccountBalance(@Valid @RequestBody GetAccountBalanceInput getAccountBalanceInput) {

        if (InputValidator.checkGetAccountBalanceInput(getAccountBalanceInput)) {

            User user = userService.getUser(getAccountBalanceInput.getName(), getAccountBalanceInput.getSocialSecurityNumber());
            if (user == null) {
                return new ResponseEntity<>(Constants.USER_DO_NOT_EXIST, HttpStatus.ACCEPTED);
            }

            EncryptedUserData encryptedUserData = user.getEncryptedUserData();

            if (encryptedUserDataService.decryptEncryptedUserPassword(encryptedUserData.getEncryptedPassword(), encryptedUserData.getSecretKey(), encryptedUserData.getInitializationVector()).equals(getAccountBalanceInput.getPassword())) {

                return new ResponseEntity<>("Successfull authentication", HttpStatus.ACCEPTED);
            }

            return new ResponseEntity<>("Failed authentication", HttpStatus.ACCEPTED);

        }
        return new ResponseEntity<>("Not valid input" + getAccountBalanceInput.getPassword(),HttpStatus.ACCEPTED);
    }

    @PutMapping(
            value = "/depositToAccount",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> depositToAccount(){
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @PutMapping(
            value = "/withdrawFromAccount",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> withdrawFromAccount(){
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }


}
