package com.example.Translation.Management.Service.Entity;

import javax.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "translation_key", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"key_name","tag"})
})
public class TranslationKeyEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "key_name", nullable = false, length = 255)
    private String keyName;

    @Column(nullable = false, length = 100)
    private String tag;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt = Instant.now();

    public TranslationKeyEntity() {}

    public TranslationKeyEntity(String keyName, String tag) {
        this.keyName = keyName;
        this.tag = tag;
        this.createdAt = Instant.now();
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getKeyName() {
		return keyName;
	}

	public void setKeyName(String keyName) {
		this.keyName = keyName;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public Instant getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Instant createdAt) {
		this.createdAt = createdAt;
	}
    
    
    
}
