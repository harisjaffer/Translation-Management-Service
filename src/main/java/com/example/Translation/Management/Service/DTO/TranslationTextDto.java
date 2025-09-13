package com.example.Translation.Management.Service.DTO;

import java.time.Instant;

import com.example.Translation.Management.Service.Entity.TranslationTextEntity;

public class TranslationTextDto {
    private Long id;
    private String keyName;
    private String tag;
    private String locale;
    private String content;
    private Instant updatedAt;

    public TranslationTextDto(TranslationTextEntity entity) {
        this.id = entity.getId();
        this.keyName = entity.getTranslationKey().getKeyName();
        this.tag = entity.getTranslationKey().getTag();
        this.locale = entity.getLocale();
        this.content = entity.getContent();
        this.updatedAt = entity.getUpdatedAt();
    }

	public Long getId() {
		return id;
	}

	public String getKeyName() {
		return keyName;
	}


	public String getTag() {
		return tag;
	}


	public String getLocale() {
		return locale;
	}


	public String getContent() {
		return content;
	}


	public Instant getUpdatedAt() {
		return updatedAt;
	}


    
    
}
