package com.pnk.bankapi.service;

import com.pnk.bankapi.model.User;

import java.util.UUID;

public interface UserService {

    User insertUser(User user);

    User getUserByUserId(Long userId);

    User updateUser(User user, UUID uuid);

    void deleteUser(UUID uuid);

    User getUserByReferenceUUID(UUID refUUID);

}
