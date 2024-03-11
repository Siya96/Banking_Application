package com.safeBankAB.safebankapp.repo;

import com.safeBankAB.safebankapp.model.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.safeBankAB.safebankapp.model.entities.Account;

import java.util.Optional;

/*
Defining this interface serves two purposes:
1) --> First by extending 'JpaRepository' we get a bunch of generic CRUD-operations into our type that allows
       saving 'Account'(s), deleting them , modifying them etc.
2) --> Second, this will allow the Spring Data JPA repository infrastructure to scan the classpath for this interface
       and create a Spring bean for it.

 */
@Repository
public interface AccountRepo extends JpaRepository<Account, Long> {

    Optional<Account> findByUser(User user);


}
