package com.pnk.bankapi.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.UuidGenerator;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.UUID;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "bank_user")
public class User implements UserDetails, Serializable {

    @Id
    @UuidGenerator
    @Column(name = "user_id")
    private UUID userId;        // patterned: tablenameId

    @Column(name = "reference_id")
    private UUID referenceId;

    @Column(name = "user_name")
    @NotNull
    private String userName;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "created_utc")
    @CreationTimestamp
    @PastOrPresent
    private LocalDate createdUTC;

    @Column(name = "last_updated_utc")
    @PastOrPresent
    @UpdateTimestamp
    private LocalDateTime lastUpdatedUTC;

    public User(UUID userId, @NotNull String userName) {
        this.userId = userId;
        this.userName = userName;
    }

    public User(UUID userId, UUID referenceId, @NotNull String userName) {
        this.userId = userId;
        this.referenceId = referenceId;
        this.userName = userName;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}