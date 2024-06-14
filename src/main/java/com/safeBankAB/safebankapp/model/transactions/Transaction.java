package com.safeBankAB.safebankapp.model.transactions;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public abstract class Transaction {
    @Id
    private long transActionId;

    private long receivingAccountNumber;

    private double paymentAmount;


}
