package com.safeBankAB.safebankapp.httpRequestInput;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;

public class CreateAccountInput {

    @NotBlank(message = "Name cannot be empty")
    private String name;
    @NotBlank(message = "Password cannot be empty")
    private String password;

    @NotBlank(message = "Social security number cannot be empty")
    private String socialSecurityNumber;
    @NotBlank(message = "Bank name cannot be empty")
    private String bankName;
    @Nullable
    private double accountBalance;
    public CreateAccountInput() {

    }

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
