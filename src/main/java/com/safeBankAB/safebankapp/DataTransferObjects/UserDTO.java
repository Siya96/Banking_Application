package com.safeBankAB.safebankapp.DataTransferObjects;

import com.safeBankAB.safebankapp.utilities.Status;
import com.safeBankAB.safebankapp.model.entities.User;

public class UserDTO {

    private final User user;
    private final Status status;

    public UserDTO(User user, Status status) {
        this.user = user;
        this.status = status;
    }

    public Status getStatus() {
        return status;
    }
    public User getUser() {
        return user;
    }
}
