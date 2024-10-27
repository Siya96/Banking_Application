package com.safeBankAB.safebankapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.safeBankAB.safebankapp.repository.entitymodels.EncryptedUserData;

@Repository
public interface EncryptedUserDataRepo extends JpaRepository<EncryptedUserData, Long> {

}
