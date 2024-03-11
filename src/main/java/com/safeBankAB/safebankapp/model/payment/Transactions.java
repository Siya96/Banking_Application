package com.safeBankAB.safebankapp.model.payment;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Transactions {
    @Id
    private long transActionId;

}
