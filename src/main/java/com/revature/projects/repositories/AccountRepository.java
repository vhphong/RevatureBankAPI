package com.revature.projects.repositories;

import com.revature.projects.models.Account;
import org.springframework.data.jpa.repository.JpaRepository;

// @Repository   : no need @Repository annotation because JpaRepository already defined @Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
}
