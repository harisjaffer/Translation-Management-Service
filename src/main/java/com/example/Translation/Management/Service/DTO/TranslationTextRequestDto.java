package com.example.Translation.Management.Service.DTO;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "TranslationTextRequest", description = "Request payload for creating a translation")
public class TranslationTextRequestDto {
	
	
	@Schema(description = "Translation key", example = "welcome.message")
    public String key;

    @Schema(description = "Tag for context", example = "web")
    public String tag;

    @Schema(description = "Locale code", example = "fr")
    public String locale;

    @Schema(description = "Translated text content", example = "Bienvenue")
    public String content;

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public String getLocale() {
		return locale;
	}

	public void setLocale(String locale) {
		this.locale = locale;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
    
    
}
