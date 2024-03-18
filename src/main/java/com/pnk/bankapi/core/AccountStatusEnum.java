package com.pnk.bankapi.core;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class AccountStatusEnum implements Serializable {
    public static final String ACCOUNT_STATUS_DELETED = "DELETED";
    public static final String ACCOUNT_STATUS_DEACTIVATED = "DEACTIVATED";
    public static final String ACCOUNT_STATUS_ACTIVATED = "ACTIVATED";
    public static final String ACCOUNT_STATUS_FROZE = "FROZE";

    protected static final String[] allAccountStatuses = {
            AccountStatusEnum.ACCOUNT_STATUS_DELETED,
            AccountStatusEnum.ACCOUNT_STATUS_DEACTIVATED,
            AccountStatusEnum.ACCOUNT_STATUS_ACTIVATED,
            AccountStatusEnum.ACCOUNT_STATUS_FROZE
    };

    protected static final String[] allAccountStatusesToModifyAccounts = {
            AccountStatusEnum.ACCOUNT_STATUS_DELETED,
            AccountStatusEnum.ACCOUNT_STATUS_DEACTIVATED,
            AccountStatusEnum.ACCOUNT_STATUS_ACTIVATED,
            AccountStatusEnum.ACCOUNT_STATUS_FROZE
    };


    private static final  int NUMBER_OF_ACCOUNT_STATUS = 6;

    private static final List<String> allAccountStatusValuesAsList = new ArrayList<>(NUMBER_OF_ACCOUNT_STATUS);

    protected static final String[] allValues = allAccountStatusValuesAsList.toArray(new String[NUMBER_OF_ACCOUNT_STATUS]);
}