package com.safeBankAB.safebankapp.controllers;


import com.safeBankAB.safebankapp.DataTransferObjects.AccountDTO;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.safeBankAB.safebankapp.utilities.Status;
import com.safeBankAB.safebankapp.httpRequestInput.CreateAccountInput;
import com.safeBankAB.safebankapp.httpRequestInput.UserCredentialsInput;
import com.safeBankAB.safebankapp.services.AccountService;

@RestController
public class AccountRestController {

    private final Logger logger = LoggerFactory.getLogger(AccountRestController.class);
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

         AccountDTO createdAccountDTO = accountService.createAccount(createAccountInput);
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
    @GetMapping(
            value = "/checkAccountBalance",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> getAccountBalance(@Valid @RequestBody UserCredentialsInput userCredentialInput) {

        AccountDTO accountDTO = accountService.checkAccountBalance(userCredentialInput);
        if(accountDTO.getCreatedAccountStatus() == Status.SUCCESSFUL_AUTHENTICATION) {
            return ResponseEntity.badRequest().body(accountDTO.getAccount().getAccountBalance());
        }
        else if(accountDTO.getCreatedAccountStatus() == Status.FAILED_AUTHENTICATION) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Status.FAILED_AUTHENTICATION);
        }
        else if(accountDTO.getCreatedAccountStatus() == Status.USER_DOES_NOT_EXIST) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Status.USER_DOES_NOT_EXIST);
        }
        else {
            return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED).body(Status.BAD_USER_CREDENTIALS_INPUT);
        }
    }


}
