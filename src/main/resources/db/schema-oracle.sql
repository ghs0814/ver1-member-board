CREATE TABLE members (
                         id NUMBER(19) PRIMARY KEY,
                         name VARCHAR2(100 CHAR) NOT NULL,
                         email VARCHAR2(255 CHAR) NOT NULL,
                         status VARCHAR2(20 CHAR) DEFAULT 'ACTIVE' NOT NULL,
                         created_at TIMESTAMP DEFAULT SYSTIMESTAMP NOT NULL,
                         CONSTRAINT uk_members_email UNIQUE (email),
                         CONSTRAINT ck_members_status CHECK (status IN ('ACTIVE', 'WITHDRAWN'))
);

CREATE SEQUENCE members_seq
    START WITH 1
    INCREMENT BY 1
    NOCACHE
    NOCYCLE;