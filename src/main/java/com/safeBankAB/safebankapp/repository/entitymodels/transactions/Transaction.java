package com.safeBankAB.safebankapp.repository.entitymodels.transactions;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public abstract class Transaction {
    @Id
    private long transActionId;

    private long receivingAccountNumber;

    private double paymentAmount;


}
