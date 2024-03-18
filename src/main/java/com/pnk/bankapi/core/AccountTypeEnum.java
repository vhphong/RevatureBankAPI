package com.pnk.bankapi.core;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class AccountTypeEnum implements Serializable {
    public static final String ACCOUNT_TYPE_DEBIT = "DEBIT";
    public static final String ACCOUNT_TYPE_CREDIT = "CREDIT";
    public static final String ACCOUNT_TYPE_SAVING = "SAVING";
    protected static final String[] allAccountTypes = {
            AccountTypeEnum.ACCOUNT_TYPE_DEBIT,
            AccountTypeEnum.ACCOUNT_TYPE_CREDIT,
            AccountTypeEnum.ACCOUNT_TYPE_SAVING
    };
    protected static final String[] allAccountTypesToOpenAccounts = {
            AccountTypeEnum.ACCOUNT_TYPE_DEBIT,
            AccountTypeEnum.ACCOUNT_TYPE_CREDIT,
            AccountTypeEnum.ACCOUNT_TYPE_SAVING
    };
    public static final int NUMBER_OF_ACCOUNT_TYPES = 6;
    protected static final List<String> allAccountTypeValuesAsList = new ArrayList<>(NUMBER_OF_ACCOUNT_TYPES);
    protected static final String[] allValues = allAccountTypeValuesAsList.toArray(new String[NUMBER_OF_ACCOUNT_TYPES]);
}