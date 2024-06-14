package com.safeBankAB.safebankapp.repo;

import com.safeBankAB.safebankapp.model.transactions.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionsRepo  extends JpaRepository<Transaction, Long> {
}
