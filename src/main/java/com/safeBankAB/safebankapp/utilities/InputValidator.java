package com.safeBankAB.safebankapp.utilities;

import com.safeBankAB.safebankapp.constantsEnumerationsAndPatterns.RegularExpressions;
import com.safeBankAB.safebankapp.httpRequestInput.CreateAccountInput;
import com.safeBankAB.safebankapp.httpRequestInput.GetAccountBalanceInput;

public class InputValidator {



    public static boolean checkCreateAccountInput(CreateAccountInput c) {
        return (RegularExpressions.ACCOUNT_NAME.matcher(c.getName()).find() &&
                RegularExpressions.SOCIAL_SECURITY_NUMBER_PATTERN.matcher(c.getSocialSecurityNumber()).find() &&
                RegularExpressions.ACCOUNT_PASSWORD_PATTERN.matcher(c.getPassword()).find() &&
                !c.getBankName().isBlank());
    }

    public static boolean checkGetAccountBalanceInput(GetAccountBalanceInput g) {
        return (RegularExpressions.ACCOUNT_NAME.matcher(g.getName()).find() &&
                RegularExpressions.SOCIAL_SECURITY_NUMBER_PATTERN.matcher(g.getSocialSecurityNumber()).find() &&
                RegularExpressions.ACCOUNT_PASSWORD_PATTERN.matcher(g.getPassword()).find());
    }
}
