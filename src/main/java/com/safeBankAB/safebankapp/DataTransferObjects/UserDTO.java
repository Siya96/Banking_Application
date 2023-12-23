package com.safeBankAB.safebankapp.DataTransferObjects;

import com.safeBankAB.safebankapp.constantsEnumerationsAndPatterns.Status;
import com.safeBankAB.safebankapp.model.entities.User;
import lombok.Getter;

@Getter
public class UserDTO {

    private final User user;
    private final Status status;

    public UserDTO(User user, Status status) {
        this.user = user;
        this.status = status;
    }
}
