package com.safeBankAB.safebankapp.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.Set;


/*
Parent entity
 */
@Data
@Entity
public class User {

//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private long id;

    @Id()
    @NotBlank(message = "Social security number cannot be empty")
    private String socialSecurityNumber;

    @NotBlank(message = "Name cannot be empty")
    private String name;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "encryptedUserData_id")
    private EncryptedUserData encryptedUserData;

    /*
        To make this association bidirectional, all we have to do is to define the referencing side (i.e. the "User" entity).
        We do so by using the mappedBy attribute of the @OneToMany annotation.
        The value of mappedBy is the name of the association-mapping attribute on the owning side (owning side --> the side with the many annotation)
     */

    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private Set<Account> userAccounts;

    public User() {

    }
    public User(String name, String socialSecurityNumber)  {
        this.name = name;
        this.socialSecurityNumber = socialSecurityNumber;
    }

    public String getName() {

        return this.name;
    }


}
