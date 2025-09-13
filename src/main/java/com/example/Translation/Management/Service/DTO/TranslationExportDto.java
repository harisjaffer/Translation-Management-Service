package com.example.Translation.Management.Service.DTO;

import java.time.Instant;

public class TranslationExportDto {
    private String keyName;
    private String tag;
    private String locale;
    private String content;
    private Instant updatedAt;

    public TranslationExportDto(String keyName, String tag, String locale, String content, Instant updatedAt) {
        this.keyName = keyName;
        this.tag = tag;
        this.locale = locale;
        this.content = content;
        this.updatedAt = updatedAt;
    }

    // getters only
    public String getKeyName() { return keyName; }
    public String getTag() { return tag; }
    public String getLocale() { return locale; }
    public String getContent() { return content; }
    public Instant getUpdatedAt() { return updatedAt; }
}