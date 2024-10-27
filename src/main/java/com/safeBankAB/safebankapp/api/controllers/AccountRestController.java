package com.safeBankAB.safebankapp.api.controllers;


import com.safeBankAB.safebankapp.api.models.responses.AccountDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import com.safeBankAB.safebankapp.utilities.Status;
import com.safeBankAB.safebankapp.api.models.requests.CreateAccountInput;
import com.safeBankAB.safebankapp.api.models.requests.UserCredentialsInput;
import com.safeBankAB.safebankapp.services.AccountService;

import java.util.Objects;

@RestController
@Validated
public class AccountRestController {

    private final Logger logger = LoggerFactory.getLogger(AccountRestController.class);
    private final AccountService accountService;

    //Constructor-injection
    @Autowired
    public AccountRestController(AccountService accountService) {
        this.accountService = accountService;
    }

    @Operation(summary = "Create Account")
    @Tag(name = "Account Ops")
    @PostMapping(
            value = "/createAccount",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseStatus(HttpStatus.OK)
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

    @Operation(summary = "Get Account Balance")
    @Tag(name = "Account Ops")
    @GetMapping(
            value = "/checkAccountBalance",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Fetch account balance", content = @Content(schema = @Schema(examples = "string"))),
            @ApiResponse(responseCode = "400", description = "bad request", content = @Content(schema = @Schema(example = "string"))),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(schema = @Schema(example = "string"))),
            @ApiResponse(responseCode = "404", description = "Not found", content = @Content(schema = @Schema(example = "string"))),
            @ApiResponse(responseCode = "412", description = "Failed validation", content = @Content(schema = @Schema(example = "string")))
    })
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Double> getAccountBalance(@Valid @RequestBody UserCredentialsInput userCredentialInput) {

        AccountDTO accountDTO = accountService.checkAccountBalance(userCredentialInput);
        if(accountDTO.getStatus() == Status.SUCCESSFUL_AUTHENTICATION) {
            return ResponseEntity.badRequest().body(Objects.isNull(accountDTO.getAccount()) ? accountDTO.getAccount().getAccountBalance() : 0);
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
