package com.pnk.bankapi.service;

import com.pnk.bankapi.exception.ResourceNotFoundException;
import com.pnk.bankapi.model.User;
import com.pnk.bankapi.repository.UserRepository;
import com.pnk.bankapi.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public User insertUser(User user) {
        if (Objects.isNull(user)) {
            throw new ResourceNotFoundException("User data input is null.");
        }
        user.setReferenceId(Utils.generateUUID());
        user.setCreatedUTC(LocalDate.now());

        return userRepository.save(user);
    }


    @Override
    public User getUserByReferenceUUID(UUID refUUID) {
        if (refUUID == null) {
            throw new ResourceNotFoundException("Reference UUID input is null.");
        }

        return userRepository.findByReferenceId(refUUID);
    }


    @Override
    public User getUserByUserId(Long userId) {
        return null;
    }


    @Override
    public User updateUser(User user, UUID uuid) {
        user.setLastUpdatedUTC(LocalDateTime.now());
        return null;
    }


    @Override
    public void deleteUser(UUID uuid) {
    }

}
