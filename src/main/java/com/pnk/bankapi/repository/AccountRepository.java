package com.pnk.bankapi.repository;

import com.pnk.bankapi.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.Collection;
import java.util.List;


public interface AccountRepository extends JpaRepository<Account, Long>, AccountCustomRepository {

    List<Account> findByBalanceLessThan(BigDecimal threshold);

    List<Account> findByBalanceLessThanEqual(BigDecimal threshold);

    List<Account> findByBalanceGreaterThan(BigDecimal threshold);

    List<Account> findByBalanceGreaterThanEqual(BigDecimal threshold);

    List<Account> findByBalanceBetween(BigDecimal lowerThreshold, BigDecimal upperThreshold);

    List<Account> findByOpeningDate(ZonedDateTime openingDate);

    List<Account> findByOpeningDateAfter(ZonedDateTime openingDate);

    List<Account> findByOpeningDateBefore(ZonedDateTime openingDate);

    List<Account> findByLastUpdatedDate(ZonedDateTime lastUpdatedDate);

    List<Account> findByLastUpdatedDateAfter(ZonedDateTime lastUpdatedDate);

    List<Account> findByLastUpdatedDateBefore(ZonedDateTime lastUpdatedDate);

    List<Account> findByType(String accountType);

    List<Account> findByTypeIn(Collection<String> accountTypeCollection);

    List<Account> findByActiveStatus(int accountActiveStatus);

    List<Account> findByActiveStatusLessThan(int accountActiveStatus);

    List<Account> findByActiveStatusLessThanEqual(int accountActiveStatus);

    List<Account> findByActiveStatusIn(Collection<Integer> accountActiveStatusCollection);

}