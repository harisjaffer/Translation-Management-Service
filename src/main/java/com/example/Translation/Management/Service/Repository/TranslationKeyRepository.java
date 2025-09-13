package com.example.Translation.Management.Service.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.Translation.Management.Service.Entity.TranslationKeyEntity;


public interface TranslationKeyRepository extends JpaRepository<TranslationKeyEntity, Long> {
    Optional<TranslationKeyEntity> findByKeyNameAndTag(String keyName, String tag);
}


