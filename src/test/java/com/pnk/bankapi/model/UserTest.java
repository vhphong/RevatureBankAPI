package com.pnk.bankapi.model;

import org.junit.Test;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.UUID;

import static org.junit.Assert.*;


public class UserTest {

    @Test
    public void testGettersAndSetters() {
        // Arrange and Act
        User actualUser = new User(UUID.randomUUID(), "janedoe");
        Collection<? extends GrantedAuthority> actualAuthorities = actualUser.getAuthorities();
        String actualPassword = actualUser.getPassword();
        String actualUsername = actualUser.getUsername();
        boolean actualIsAccountNonExpiredResult = actualUser.isAccountNonExpired();
        boolean actualIsAccountNonLockedResult = actualUser.isAccountNonLocked();
        boolean actualIsCredentialsNonExpiredResult = actualUser.isCredentialsNonExpired();

        // Assert
        assertNull(actualPassword);
        assertNull(actualUsername);
        assertNull(actualAuthorities);
        assertFalse(actualIsAccountNonExpiredResult);
        assertTrue(actualIsAccountNonLockedResult);
        assertTrue(actualIsCredentialsNonExpiredResult);
        assertTrue(actualUser.isEnabled());
    }

    /**
     * Methods under test:
     *
     * <ul>
     *   <li>{@link User#User(UUID, UUID, String)}
     *   <li>{@link User#getAuthorities()}
     *   <li>{@link User#getPassword()}
     *   <li>{@link User#getUsername()}
     *   <li>{@link User#isAccountNonExpired()}
     *   <li>{@link User#isAccountNonLocked()}
     *   <li>{@link User#isCredentialsNonExpired()}
     *   <li>{@link User#isEnabled()}
     * </ul>
     */
    @Test
    public void testGettersAndSetters2() {
        // Arrange
        UUID userId = UUID.randomUUID();

        // Act
        User actualUser = new User(userId, UUID.randomUUID(), "janedoe");
        Collection<? extends GrantedAuthority> actualAuthorities = actualUser.getAuthorities();
        String actualPassword = actualUser.getPassword();
        String actualUsername = actualUser.getUsername();
        boolean actualIsAccountNonExpiredResult = actualUser.isAccountNonExpired();
        boolean actualIsAccountNonLockedResult = actualUser.isAccountNonLocked();
        boolean actualIsCredentialsNonExpiredResult = actualUser.isCredentialsNonExpired();

        // Assert
        assertNull(actualPassword);
        assertNull(actualUsername);
        assertNull(actualAuthorities);
        assertFalse(actualIsAccountNonExpiredResult);
        assertTrue(actualIsAccountNonLockedResult);
        assertTrue(actualIsCredentialsNonExpiredResult);
        assertTrue(actualUser.isEnabled());
    }

}