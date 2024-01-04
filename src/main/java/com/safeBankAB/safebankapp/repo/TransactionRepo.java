package com.safeBankAB.safebankapp.repo;

import com.safeBankAB.safebankapp.model.payment.Transactions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepo  extends JpaRepository<Transactions, Long> {
}
