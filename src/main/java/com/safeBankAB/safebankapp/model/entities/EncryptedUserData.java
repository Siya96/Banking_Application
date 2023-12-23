package com.safeBankAB.safebankapp.model.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
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
    @OneToOne(mappedBy = "encryptedUserData", fetch = FetchType.LAZY)
    private User user;

    public EncryptedUserData (){}
    public EncryptedUserData(String encryptedPassword) {

        this.encryptedPassword = encryptedPassword;

    }

    public String getEncryptedPassword() {
        return encryptedPassword;
    }


}
