package com.safeBankAB.safebankapp.httpRequestInput;

import jakarta.validation.constraints.NotBlank;

public class GetAccountBalanceInput {

    @NotBlank(message = "Name cannot be empty")
    private String name;

    @NotBlank(message = "Social security number cannot be empty")
    private String socialSecurityNumber;
    @NotBlank(message = "Password cannot be empty")
    private String password;

//    @NotBlank(message = "Bank name cannot be empty")
//    private String bankName;



//    @NotBlank(message = "Account number cannot be empty")
//    private String accountNumber;

    public GetAccountBalanceInput() {}

    public String getName() {
        return name;
    }
    public String getSocialSecurityNumber() {
        return socialSecurityNumber;
    }
    public String getPassword() {
        return password;
    }
}
