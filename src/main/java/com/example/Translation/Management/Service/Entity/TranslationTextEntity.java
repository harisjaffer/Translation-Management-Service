package com.example.Translation.Management.Service.Entity;

import java.time.Instant;

import javax.persistence.*;


@Entity
@Table(name = "translation_text", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"translation_key_id", "locale"})
})
public class TranslationTextEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "translation_key_id", nullable = false)
    private TranslationKeyEntity translationKey;

    @Column(nullable = false, length = 10)
    private String locale;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt = Instant.now();

    public TranslationTextEntity() {}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public TranslationKeyEntity getTranslationKey() {
		return translationKey;
	}

	public void setTranslationKey(TranslationKeyEntity translationKey) {
		this.translationKey = translationKey;
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

	public Instant getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Instant updatedAt) {
		this.updatedAt = updatedAt;
	}

    // getters / setters
    
    
}
