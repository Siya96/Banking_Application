package com.safeBankAB.safebankapp.controllers;


import com.safeBankAB.safebankapp.DataTransferObjects.CreatedAccountDTO;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.safeBankAB.safebankapp.constantsEnumerationsAndPatterns.Constants;
import com.safeBankAB.safebankapp.constantsEnumerationsAndPatterns.Status;
import com.safeBankAB.safebankapp.httpRequestInput.CreateAccountInput;
import com.safeBankAB.safebankapp.httpRequestInput.UserCredentialsInput;
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
    private final AccountService accountService;

    //Constructor-injection
    @Autowired
    public AccountRestController(AccountService accountService) {
        this.accountService = accountService;
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
        else if (createdAccountDTO.getCreatedAccountStatus() == Status.USER_ACCOUNT_ALREADY_EXISTS) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(createdAccountDTO);
        }
        else {
            return ResponseEntity.unprocessableEntity().body(createdAccountDTO);
        }
    }
/*    @GetMapping(
            value = "/checkAccountBalance",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<String> getAccountBalance(@Valid @RequestBody UserCredentialsInput userCredentialInput) {

        if (InputValidator.checkUserCredentialsInput(userCredentialInput)) {

            User user = userService.getUser(userCredentialInput.getName(), userCredentialInput.getSocialSecurityNumber());
            if (user == null) {
                return new ResponseEntity<>(Constants.USER_DO_NOT_EXIST, HttpStatus.ACCEPTED);
            }

            EncryptedUserData encryptedUserData = user.getEncryptedUserData();

            if (encryptedUserDataService.decryptEncryptedUserPassword(encryptedUserData.getEncryptedPassword(), encryptedUserData.getSecretKey(), encryptedUserData.getInitializationVector()).equals(userCredentialInput.getPassword())) {

                return new ResponseEntity<>("Successfull authentication", HttpStatus.ACCEPTED);
            }

            return new ResponseEntity<>("Failed authentication", HttpStatus.ACCEPTED);

        }
        return new ResponseEntity<>("Not valid input" + userCredentialInput.getPassword(),HttpStatus.ACCEPTED);
    }*/


    @GetMapping(
            value = "/checkAccountBalance",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<String> getAccountBalance(@Valid @RequestBody UserCredentialsInput userCredentialInput) {

        double balance = accountService.checkAccountBalance(userCredentialInput);
        if(balance == 0.001) {
            return ResponseEntity.badRequest().body("0.001");
        }
        else if (balance == 0.002) {
            return ResponseEntity.badRequest().body("0.002");
        }
        else if (balance == 0.003) {
            return ResponseEntity.badRequest().body("0.003");
        }
        else {
            return ResponseEntity.ok(""+balance);
        }
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
