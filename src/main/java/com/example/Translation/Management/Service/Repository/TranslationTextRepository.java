package com.example.Translation.Management.Service.Repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.Translation.Management.Service.DTO.TranslationExportDto;
import com.example.Translation.Management.Service.Entity.TranslationKeyEntity;
import com.example.Translation.Management.Service.Entity.TranslationTextEntity;

import java.util.Optional;
import java.util.stream.Stream;

@Repository
public interface TranslationTextRepository extends JpaRepository<TranslationTextEntity, Long> {

    Optional<TranslationTextEntity> findByTranslationKeyAndLocale(TranslationKeyEntity key, String locale);

    @Query("SELECT new com.example.Translation.Management.Service.DTO.TranslationExportDto(t.translationKey.keyName,t.translationKey.tag,t.locale, t.content, t.updatedAt) FROM TranslationTextEntity t WHERE (:locale IS NULL OR t.locale = :locale)")
    Stream<TranslationExportDto> streamByLocale(@Param("locale") String locale);
    
    
//    @Query("SELECT t FROM TranslationTextEntity t WHERE (:locale IS NULL OR t.locale = :locale)")
//    Stream<TranslationTextEntity> streamByLocale(@Param("locale") String locale);
//    
    @Query("SELECT t FROM TranslationTextEntity t JOIN t.translationKey k " +
           "WHERE (:locale IS NULL OR t.locale = :locale) " +
           "AND (:tag IS NULL OR k.tag = :tag) " +
           "AND (:q IS NULL OR LOWER(k.keyName) LIKE LOWER(CONCAT('%',:q,'%')) " +
           "OR LOWER(t.content) LIKE LOWER(CONCAT('%',:q,'%')) )")
    Page<TranslationTextEntity> search(@Param("locale") String locale,
                                       @Param("tag") String tag,
                                       @Param("q") String q,
                                       Pageable pageable);

    // Use DTO projection to avoid N+1 and fetch only required columns
    
    @Query("SELECT new com.example.Translation.Management.Service.DTO.TranslationExportDto(k.keyName, k.tag, t.locale, t.content, t.updatedAt) " +
           "FROM TranslationTextEntity t JOIN t.translationKey k " +
           "WHERE (:locale IS NULL OR t.locale = :locale)")
    Stream<TranslationExportDto> streamExportByLocale(@Param("locale") String locale);
}