package com.safeBankAB.safebankapp.repository;

import com.safeBankAB.safebankapp.repository.entitymodels.transactions.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionsRepo  extends JpaRepository<Transaction, Long> {
}
