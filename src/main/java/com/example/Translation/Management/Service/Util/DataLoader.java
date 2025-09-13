package com.example.Translation.Management.Service.Util;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.example.Translation.Management.Service.Entity.TranslationKeyEntity;
import com.example.Translation.Management.Service.Entity.TranslationTextEntity;

@Component
public class DataLoader implements CommandLineRunner {

    @PersistenceContext
    private EntityManager em;

    private static final int KEYS = 20000; // 20k keys
    private static final String[] LOCALES = {"en","fr","es","de","ur"}; // 20k * 5 = 100k
    private static final int BATCH_SIZE = 1000;

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        long existing = (Long) em.createQuery("SELECT COUNT(k) FROM TranslationKeyEntity k").getSingleResult();
        if (existing > 0) return;

        for (int i = 0; i < KEYS; i++) {
            TranslationKeyEntity key = new TranslationKeyEntity("key." + i, i % 2 == 0 ? "mobile" : "web");
            em.persist(key);

            for (String locale : LOCALES) {
                TranslationTextEntity t = new TranslationTextEntity();
                t.setTranslationKey(key);
                t.setLocale(locale);
                t.setContent("Sample content " + i + " (" + locale + ")");
                t.setUpdatedAt(java.time.Instant.now());
                em.persist(t);
            }
            if (i % BATCH_SIZE == 0) {
                em.flush();
                em.clear();
            }
        }
        em.flush();
        em.clear();
    }
}