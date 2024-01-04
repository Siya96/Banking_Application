package com.safeBankAB.safebankapp.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/transaction")
public class TransactionRestController {


    @Autowired
    public TransactionRestController() {

    }
    @PutMapping(
            value = "/transfer",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> transferMoneyBetweenAccounts(){

         /*TODO
           1) verify user from source account  and check that target bank account exists
           2) confirm transfer amount <= existing amount
            2.1) if (true) - return amount and update account balance
            2.2) if (false) - return insufficient funds
        */

        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }
    @PutMapping(
            value = "/depositToAccount",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> depositToAccount(){

        /*TODO
        1) Anyone may deposit money - no verification needed
           2) confirm amount is positive and that bank number is existing
            2.1) if (true) - return amount deposited and update account balance
            2.2) if (false) - return bad deposit or non existing account
        */

        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @PutMapping(
            value = "/withdrawFromAccount",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> withdrawFromAccount(){

         /*TODO
           1) verify user before withdrawal
           2) confirm withdrawal amount <= existing amount
            2.1) if (true) - return amount and update account balance
            2.2) if (false) - return insufficient funds
        */

        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }
}
