package com.safeBankAB.safebankapp.api.models.responses;

import com.safeBankAB.safebankapp.utilities.Status;
import com.safeBankAB.safebankapp.repository.entitymodels.Account;
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
