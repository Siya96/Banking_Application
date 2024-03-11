package com.safeBankAB.safebankapp.model.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

@Entity
public class EncryptedUserData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotBlank(message = "Encrypted password can not be empty")
    private String encryptedPassword;


    /*
        The 'mappedBy' refers to the property name of the association on the owner's side.
     */
    @OneToOne(mappedBy = "encryptedUserData", fetch = FetchType.LAZY) /*
    mappedBy creates a bidirectional relationship between Account and
     User. The value of mapped by is the name of the attribute on the owning-side (the side that owns the foreign key).
    */
    private User user;

    public EncryptedUserData (){}
    public EncryptedUserData(String encryptedPassword) {

        this.encryptedPassword = encryptedPassword;

    }

    public long getId() { return id; }

    public String getEncryptedPassword() {
        return encryptedPassword;
    }


}
