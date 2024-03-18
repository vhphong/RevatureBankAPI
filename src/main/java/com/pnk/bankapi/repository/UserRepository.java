package com.pnk.bankapi.repository;

import com.pnk.bankapi.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {

    User findByReferenceId(UUID refUUID);

}
