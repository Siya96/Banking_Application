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
    public ResponseEntity<AccountDTO> createAccount(@Valid @RequestBody CreateAccountInput createAccountInput)  {

         AccountDTO accountDTO = accountService.createAccount(createAccountInput);
        if (accountDTO.getStatus() == Status.ACCOUNT_CREATED) {
            return ResponseEntity.ok(accountDTO);
        }
        else if (accountDTO.getStatus() == Status.USER_ACCOUNT_ALREADY_EXISTS) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(accountDTO);
        }
        else {
            return ResponseEntity.unprocessableEntity().body(accountDTO);
        }
    }
    @GetMapping(
            value = "/checkAccountBalance",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Double> getAccountBalance(@Valid @RequestBody UserCredentialsInput userCredentialInput) {

        AccountDTO accountDTO = accountService.checkAccountBalance(userCredentialInput);
        if(accountDTO.getStatus() == Status.SUCCESSFUL_AUTHENTICATION) {
            return ResponseEntity.badRequest().body(accountDTO.getAccount().getAccountBalance());
        }
        else if(accountDTO.getStatus() == Status.FAILED_AUTHENTICATION) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        else if(accountDTO.getStatus() == Status.USER_NOT_FOUND) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        else {
            return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED).build();
        }
    }


}
