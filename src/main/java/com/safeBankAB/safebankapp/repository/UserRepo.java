package com.safeBankAB.safebankapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.safeBankAB.safebankapp.repository.entitymodels.User;

import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {

    Optional<User> findByNameAndSocialSecurityNumber(String name, String socialSecurityNumber);
}
