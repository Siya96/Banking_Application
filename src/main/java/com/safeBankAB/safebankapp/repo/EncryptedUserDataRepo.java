package com.safeBankAB.safebankapp.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.safeBankAB.safebankapp.model.entities.EncryptedUserData;

@Repository
public interface EncryptedUserDataRepo extends JpaRepository<EncryptedUserData, Long> {

}
