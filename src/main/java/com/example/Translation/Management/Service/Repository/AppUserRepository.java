package com.example.Translation.Management.Service.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.Translation.Management.Service.Entity.AppUserEntity;

import java.util.Optional;

public interface AppUserRepository extends JpaRepository<AppUserEntity, Long> {
    Optional<AppUserEntity> findByUsername(String username);
}