package com.safeBankAB.safebankapp.repository.entitymodels;


import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import java.util.Set;


/*
Parent entity
 */
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

    @Valid
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
    public User(String name, String socialSecurityNumber, EncryptedUserData encryptedUserData)  {
        this.name = name;
        this.socialSecurityNumber = socialSecurityNumber;
        this.encryptedUserData = encryptedUserData;
    }

    public String getSocialSecurityNumber() { return socialSecurityNumber; }
    public String getName() { return this.name; }

    public EncryptedUserData getEncryptedUserData() {
        return encryptedUserData;
    }
}
