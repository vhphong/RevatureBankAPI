package com.pnk.bankapi.repository;

import com.pnk.bankapi.model.Account;

import java.math.BigDecimal;
import java.util.List;

public interface AccountCustomRepository {

    List<Account> findAccountsBalanceGreaterThanOrEqual(BigDecimal lowerLimitInput);

    List<Account> findAccountsBalanceLessThanOrEqual(BigDecimal upperLimitInput);

    List<Account> findAccountsBalanceRange(BigDecimal lowerLimitInput, BigDecimal upperLimitInput);

    List<Account> findAccountsOwnedByCustomerNameExact(String customerNameInput);

    List<Account> findAccountsOwnedByCustomerNameContaining(String customerNameInput);

    List<Account> findAccountsOwnedByCustomerEmail(String customerEmailInput);
}