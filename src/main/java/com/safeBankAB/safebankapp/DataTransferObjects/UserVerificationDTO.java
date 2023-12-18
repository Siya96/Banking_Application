package com.safeBankAB.safebankapp.DataTransferObjects;

import com.safeBankAB.safebankapp.constantsEnumerationsAndPatterns.Status;
import com.safeBankAB.safebankapp.model.User;

public class UserVerificationDTO {

    private final User user;
    private final Status status;

    public UserVerificationDTO(User user, Status status) {
        this.user = user;
        this.status = status;
    }
}
