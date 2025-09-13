package com.example.Translation.Management.Service.DTO;

public class TranslationUpdateRequestDto {

	private String locale;
	private String content;

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
