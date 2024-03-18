package com.pnk.bankapi.service;

import com.pnk.bankapi.exception.ResourceNotFoundException;
import com.pnk.bankapi.model.Account;
import com.pnk.bankapi.model.Customer;
import com.pnk.bankapi.repository.AccountRepository;
import com.pnk.bankapi.repository.CustomerRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;


@Service
public class AccountServiceImpl implements AccountService {

    private static final Logger logger = LogManager.getLogger(AccountServiceImpl.class);

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private CustomerServiceImpl customerService;

    @Autowired
    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }


    @Override
    public Account insertAccount(Account accountInput) {
        Customer existingCustomer = customerRepository.findById(accountInput.getCustomer().getCustomerId()).orElseThrow(
                () -> new ResourceNotFoundException("Customer with Id: " + accountInput.getCustomer().getCustomerId() + " is not found."));

        accountInput.setCustomer(existingCustomer);
        accountInput.setOpeningDate(LocalDateTime.now());
        accountInput.setLastUpdatedDate(LocalDateTime.now());

        return accountRepository.save(accountInput);
    }


    @Override
    public List<Account> listAllAccounts() {
        return accountRepository.findAll();
    }


    @Override
    public List<Account> listAllAccountsBalanceGreaterThanOrEqual(BigDecimal minBalanceInput) {
        return accountRepository.findByBalanceGreaterThanEqual(minBalanceInput);
    }


    @Override
    public List<Account> listAllAccountsBalanceLessThanOrEqual(BigDecimal maxBalanceInput) {
        return accountRepository.findByBalanceLessThanEqual(maxBalanceInput);
    }


    @Override
    public List<Account> listAllAccountsBalanceInRange(BigDecimal minBalanceInput, BigDecimal maxBalanceInput) {
        return accountRepository.findByBalanceBetween(minBalanceInput, maxBalanceInput);
    }


    @Override
    public List<Account> listAllAccountsByType(String accountTypeInput) {
        return accountRepository.findByType(accountTypeInput);
    }


    @Override
    public List<Account> listAllAccountsByOwnerId(Long customerIdInput) {
        Customer retrievedCustomer = customerRepository.findById(customerIdInput).orElseThrow(
                () -> new ResourceNotFoundException("Customer with Id: " + customerIdInput + " is not found."));

        return retrievedCustomer.getAccountList();
    }


    @Override
    public List<Account> listAllAccountsByCustomerName(String customerNameInput) {
        List<Account> accountList = new ArrayList<>();

        List<Customer> customerList = customerRepository.findByNameContaining(customerNameInput);

        for (Customer eachCustomer : customerList) {
            accountList.addAll(eachCustomer.getAccountList());
        }

        return accountList;
    }


    @Override
    public List<Account> listAllAccountsByCustomerEmail(String customerEmailInput) {
        List<Account> accountList = new ArrayList<>();

        List<Customer> customerList = customerRepository.findByEmail(customerEmailInput);

        for (Customer eachCustomer : customerList) {
            accountList.addAll(eachCustomer.getAccountList());
        }

        return accountList;
    }


    @Override
    public List<Account> listAllAccountsByAccountActiveStatus(int accountStatusInput) {
        List<Account> accountList = accountRepository.findByActiveStatus(accountStatusInput);

        if (accountList.isEmpty()) {
            throw new ResourceNotFoundException("Accounts with status #" + accountStatusInput + " are not found.");
        }

        return accountList;
    }


    @Override
    public List<Account> listAccountByAccountId(Long accountIdInput) {
        List<Account> accountList = new ArrayList<>();
        Optional<Account> account = accountRepository.findById(accountIdInput);

        if (account.isPresent()) {
            accountList.add(account.get());
        } else {
            throw new ResourceNotFoundException("Account with Id: " + accountIdInput + " is not found.");
        }

        return accountList;
    }


    @Override
    public Account modifyAccount(Account newAccount, Long accountIdInput) throws IllegalAccessException {
        // check whether account with given id is existing in the database or not
        Account existingAccount = accountRepository.findById(accountIdInput).orElseThrow(
                () -> new ResourceNotFoundException("Account with Id: " + accountIdInput + " is not found."));

        if (existingAccount == null || newAccount == null) {
            throw new IllegalArgumentException("Source and target objects must not be null.");
        }

        // Get declared fields of the class
        Field[] fields = newAccount.getClass().getDeclaredFields();

        // Loop through fields to copy properties
        for (Field field : fields) {
            field.setAccessible(true); // Make private fields accessible
            Object value = field.get(newAccount);
            if (value != null) {
                field.set(existingAccount, value);
            }
        }

        existingAccount.setLastUpdatedDate(LocalDateTime.now());

        // save existingAccount to the database
        return accountRepository.save(existingAccount);
    }


    @Override
    public Account setAccountWithStatus(Long accountIdInput, int accountStatusInput) {
        Account retrievedAccount = accountRepository.findById(accountIdInput).orElseThrow(
                () -> new ResourceNotFoundException("Account with Id: " + accountIdInput + " is not found."));
        retrievedAccount.setActiveStatus(accountStatusInput);
        retrievedAccount.setLastUpdatedDate(LocalDateTime.now());

        return accountRepository.save(retrievedAccount);
    }


    @Override
    public boolean removeAccount(Long accountIdInput) {
        if (accountRepository.existsById(accountIdInput)) {
            try {
                accountRepository.deleteById(accountIdInput);
                return true;
            } catch (Exception error) {
                logger.error(error.getMessage());
                return false;
            }
        }
        throw new ResourceNotFoundException("The account ID " + accountIdInput + " is not found.");
    }


    @Override
    public Account verifyOwnershipOfAccount(Long accountOwnerIdInput, Long accountIdInput) {
        Account retrievedAccount = accountRepository.findById(accountIdInput).orElseThrow(
                () -> new ResourceNotFoundException("Account with ID# " + accountIdInput + " is not found."));

        // return the validated account if it is being owned by accountOwnerIdInput
        return (Objects.equals(accountOwnerIdInput, retrievedAccount.getCustomer().getCustomerId()))
                ? retrievedAccount
                : null;
    }


    @Override
    public boolean validateTransferAmount(Long accountIdInput, BigDecimal amount) {
        Account retrievedAccount = accountRepository.findById(accountIdInput).orElseThrow(
                () -> new ResourceNotFoundException("Account with Id: " + accountIdInput + " is not found."));

        return (amount.compareTo(BigDecimal.ZERO) >= 0) && (amount.compareTo(retrievedAccount.getBalance()) <= 0);
    }
}
