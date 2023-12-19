package com.safeBankAB.safebankapp.DataTransferObjects;

import com.safeBankAB.safebankapp.constantsEnumerationsAndPatterns.Status;
import com.safeBankAB.safebankapp.model.Account;
import jakarta.annotation.Nullable;
import lombok.Getter;

@Getter
public class AccountDTO {

    @Nullable
    private final Account account;
    private final Status createdAccountStatus;

    public AccountDTO(Account account, Status accountStatus) {
        this.account = account;
        this.createdAccountStatus = accountStatus;
    }

}
