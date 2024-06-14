package com.safeBankAB.safebankapp.utilities;

import java.util.regex.Pattern;

public class RegularExpressions {

    /**
     * Only lettters
     */
    public static final Pattern ACCOUNT_NAME = Pattern.compile("^[A-Za-z]+$");

    /**
     * Password must contain at least one digit [0-9].
     * Password must contain at least one lowercase Latin character [a-z].
     * Password must contain at least one uppercase Latin character [A-Z].
     * Password must contain at least one special character like ! @ # & ( ) %.
     * Password must contain a length of at least 8 characters and a maximum of 20 characters.
     */
    //public static final Pattern ACCOUNT_PASSWORD_PATTERN = Pattern.compile("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@%#&()–[{}]:;',?/*~$^+=<>]).{8,20}$");
    public static final Pattern ACCOUNT_PASSWORD_PATTERN = Pattern.compile("^(?=.*\\d.)(?=.*[a-z])(?=.*[A-Z])(?=.*[!@%#&()–[{}]:;',?/*~$^+=<>]).{8,20}$");


    /**
     * Password must contain at least eight digits [0-9].
     */
    public static final Pattern ACCOUNT_NUMBER_PATTERN = Pattern.compile("^[0-9]{16}$");

    public static final Pattern SOCIAL_SECURITY_NUMBER_PATTERN = Pattern.compile("^\\d{6}(?:\\d{2})?[-\\s]?\\d{4}$");

}
