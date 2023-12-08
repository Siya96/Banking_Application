package com.safeBankAB.safebankapp.model;

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

    @NotBlank(message = "secret key can not be empty")
    private String secretKey;

    @NotBlank(message = "Initialization vector can not be empty")
    private String initializationVector;


    /*
        The 'mappedBy' refers to the property name of the association on the owner's side.
     */
    @OneToOne(mappedBy = "encryptedUserData", fetch = FetchType.LAZY)
    private User user;

    public EncryptedUserData (){}
    public EncryptedUserData(String encryptedPassword, String secretKey, String initializationVector) {

        this.encryptedPassword = encryptedPassword;
        this.secretKey = secretKey;
        this.initializationVector = initializationVector;
    }

    public String getEncryptedPassword() {
        return encryptedPassword;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public String getInitializationVector() {
        return initializationVector;
    }

}
