package com.safeBankAB.safebankapp.utilities;

public final class Constants {

    private Constants(){}
    public static final String USER_ALREADY_EXISTS = "Failed to create user";
    public static final String USER_DO_NOT_EXIST = "User does not exits";

    public static final String ACCOUNT_NOT_CREATED = "Failed to create account";

    public static final String SUCCESSFULLY_CREATED_USER_AND_ACCOUNT = "Successfully created user and account";

    public static final String FAILED_INPUT_VALIDATION = "Unsuccessfully created user and account";

    public static final Long ACCOUNT_NUMBER_SUFFIX_MODULO = 100000000000000L;
    public static final Long ACCOUNT_NUMBER_PREFIX_MODULO = 5200000000000000L;

}
