package com.safeBankAB.safebankapp.DataTransferObjects;

import com.safeBankAB.safebankapp.utilities.Status;
import com.safeBankAB.safebankapp.model.entities.Account;
import jakarta.annotation.Nullable;

public class AccountDTO {

    @Nullable
    private final Account account;
    private final Status status;

    public AccountDTO(@Nullable Account account, Status accountStatus) {
        this.account = account;
        this.status = accountStatus;
    }

    public Status getStatus() {
        return status;
    }

    @Nullable
    public Account getAccount() {
        return account;
    }

    public boolean isAccountPresent() {
        return account != null;
    }
}
