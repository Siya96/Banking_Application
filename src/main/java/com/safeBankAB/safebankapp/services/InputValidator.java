package com.safeBankAB.safebankapp.services;

import com.safeBankAB.safebankapp.utilities.RegularExpressions;
import com.safeBankAB.safebankapp.model.httpRequestInput.CreateAccountInput;
import com.safeBankAB.safebankapp.model.httpRequestInput.UserCredentialsInput;

public class InputValidator {



    public static boolean checkCreateAccountInput(CreateAccountInput c) {
        return (RegularExpressions.ACCOUNT_NAME.matcher(c.getName()).find() &&
                RegularExpressions.SOCIAL_SECURITY_NUMBER_PATTERN.matcher(c.getSocialSecurityNumber()).find() &&
                RegularExpressions.ACCOUNT_PASSWORD_PATTERN.matcher(c.getPassword()).find() &&
                !c.getBankName().isBlank());
    }

    public static boolean checkUserCredentialsInput(UserCredentialsInput g) {
        return (RegularExpressions.ACCOUNT_NAME.matcher(g.getName()).find() &&
                RegularExpressions.SOCIAL_SECURITY_NUMBER_PATTERN.matcher(g.getSocialSecurityNumber()).find() &&
                RegularExpressions.ACCOUNT_PASSWORD_PATTERN.matcher(g.getPassword()).find());
    }
}
