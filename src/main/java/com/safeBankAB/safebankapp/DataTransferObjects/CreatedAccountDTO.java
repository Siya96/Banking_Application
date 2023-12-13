package com.safeBankAB.safebankapp.DataTransferObjects;

import com.safeBankAB.safebankapp.constantsEnumerationsAndPatterns.Status;
import com.safeBankAB.safebankapp.model.Account;
import jakarta.annotation.Nullable;
import lombok.Getter;

@Getter
public class CreatedAccountDTO {

    @Nullable
    private final Account createdAccount;
    private final Status createdAccountStatus;

    public CreatedAccountDTO (Account createdAccount, Status createdAccountStatus) {
        this.createdAccount = createdAccount;
        this.createdAccountStatus = createdAccountStatus;
    }

}
