-- ==============================
-- Sequence and table: translation_key
-- ==============================
CREATE SEQUENCE translation_key_seq START WITH 1 INCREMENT BY 1 NOCACHE NOCYCLE;

CREATE SEQUENCE app_user_seq START WITH 1 INCREMENT BY 1 NOCACHE NOCYCLE;


CREATE TABLE translation_key (
    id NUMBER(19) PRIMARY KEY,
    key_name VARCHAR2(255) NOT NULL,
    tag VARCHAR2(100) NOT NULL,
    created_at TIMESTAMP DEFAULT SYSTIMESTAMP,
    CONSTRAINT uq_translation_key UNIQUE (key_name, tag)
);

-- Trigger for auto-increment
CREATE OR REPLACE TRIGGER trg_translation_key
BEFORE INSERT ON translation_key
FOR EACH ROW
BEGIN
    IF :NEW.id IS NULL THEN
        SELECT translation_key_seq.NEXTVAL INTO :NEW.id FROM dual;
    END IF;
END;
/

-- Indexes
CREATE INDEX idx_tk_keyname ON translation_key (key_name);
CREATE INDEX idx_tk_tag ON translation_key (tag);

-------------------------------------------------------
-- Sequence and table: translation_text
-------------------------------------------------------
CREATE SEQUENCE translation_text_seq START WITH 1 INCREMENT BY 1 NOCACHE NOCYCLE;

CREATE TABLE translation_text (
    id NUMBER(19) PRIMARY KEY,
    translation_key_id NUMBER(19) NOT NULL,
    locale VARCHAR2(10) NOT NULL,
    content CLOB NOT NULL,
    updated_at TIMESTAMP DEFAULT SYSTIMESTAMP NOT NULL,
    CONSTRAINT fk_translation_key FOREIGN KEY (translation_key_id)
        REFERENCES translation_key(id) ON DELETE CASCADE,
    CONSTRAINT uq_translation_text UNIQUE (translation_key_id, locale)
);

-- Trigger for auto-increment
CREATE OR REPLACE TRIGGER trg_translation_text
BEFORE INSERT ON translation_text
FOR EACH ROW
BEGIN
    IF :NEW.id IS NULL THEN
        SELECT translation_text_seq.NEXTVAL INTO :NEW.id FROM dual;
    END IF;
END;
/

-- Indexes
CREATE INDEX idx_tt_locale ON translation_text (locale);
CREATE INDEX idx_tt_key_locale ON translation_text (translation_key_id, locale);


commit;