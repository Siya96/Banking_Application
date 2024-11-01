package com.safeBankAB.safebankapp.api.models.requests;

import com.safeBankAB.safebankapp.utilities.annotations.MutuallyExclusiveWith;
import jakarta.annotation.Nullable;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class CreateAccountInput {

    @NotBlank(message = "Name cannot be empty")
    //@MutuallyExclusiveWith()
    private String name;
    @NotBlank(message = "Password cannot be empty")
    private String password;

    @NotBlank(message = "Social security number cannot be empty")
    private String socialSecurityNumber;
    @NotBlank(message = "Bank name cannot be empty")
    private String bankName;
    @Nullable
    private double accountBalance;

    public String getName() {
        return name;
    }

    public String getSocialSecurityNumber() {
        return socialSecurityNumber;
    }

    public String getPassword() {
        return password;
    }

    public String getBankName() {
        return bankName;
    }

    public double getAccountBalance() {
        return accountBalance;
    }
}
