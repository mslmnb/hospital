-- CREATE ROLE "user" LOGIN
--  ENCRYPTED PASSWORD 'md54d45974e13472b5a0be3533de4666414'
--  NOSUPERUSER INHERIT CREATEDB NOCREATEROLE NOREPLICATION;

CREATE DATABASE hospital
  ENCODING = 'UTF8'
  TABLESPACE = pg_default
  LC_COLLATE = 'Russian_Russia.1251'
  LC_CTYPE = 'Russian_Russia.1251'
  CONNECTION LIMIT = -1;
ALTER DATABASE hospital OWNER TO "user";

\connect hospital

CREATE TABLE lang
(
  locale           VARCHAR PRIMARY KEY
);

CREATE SEQUENCE handbk_items_seq ;
CREATE TABLE handbk_items
(
  id              INTEGER PRIMARY KEY DEFAULT nextval('handbk_items_seq'),
  name            VARCHAR NOT NULL,
  type     VARCHAR NOT NULL

);

CREATE SEQUENCE handbk_item_translations_seq ;
CREATE TABLE handbk_item_translations
(
  id              INTEGER PRIMARY KEY DEFAULT nextval('handbk_item_translations_seq'),
  handbk_item_id  INTEGER NOT NULL,
  locale          VARCHAR NOT NULL,
  translation     VARCHAR NOT NULL,
  CONSTRAINT translate_unique_item_locale_idx UNIQUE (handbk_item_id, locale),
  FOREIGN KEY (handbk_item_id) REFERENCES handbk_items (id) ON DELETE CASCADE,
  FOREIGN KEY (locale) REFERENCES lang (locale) ON DELETE RESTRICT
);

CREATE SEQUENCE staff_seq;
CREATE TABLE staff
(
  id INTEGER PRIMARY KEY DEFAULT nextval('staff_seq'),
  name                 VARCHAR NOT NULL,
  additional_name      VARCHAR,
  surname              VARCHAR NOT NULL,
  position_item_id     INTEGER NOT NULL,
  FOREIGN KEY (position_item_id) REFERENCES handbk_items (id) ON DELETE RESTRICT
);

CREATE SEQUENCE users_seq;
CREATE TABLE users
(
  id              INTEGER PRIMARY KEY DEFAULT nextval('users_seq'),
  staff_id        INTEGER NOT NULL,
  login           VARCHAR NOT NULL,
  password        VARCHAR NOT NULL,
  role            VARCHAR,
  CONSTRAINT users_unique_login_idx UNIQUE (login),
  CONSTRAINT users_unique_staff_role_idx UNIQUE (staff_id, role),
  FOREIGN KEY (staff_id) REFERENCES staff (id) ON DELETE RESTRICT
);

CREATE SEQUENCE patient_seq;
CREATE TABLE patient_register
(
  id                            INTEGER PRIMARY KEY DEFAULT nextval('patient_seq'),
  name                          VARCHAR NOT NULL,
  additional_name               VARCHAR,
  surname                       VARCHAR NOT NULL,
  birthday                      DATE NOT NULL,
  phone                         VARCHAR NOT NULL,
  email                         VARCHAR,
  admission_date                DATE NOT NULL DEFAULT now(),
  discharge_date                DATE,
  primary_inspection            TEXT,
  primary_complaints            TEXT,   -- жалобы
  primary_diagnosis_item_id     INTEGER,
  final_diagnosis_item_id       INTEGER,
  FOREIGN KEY (final_diagnosis_item_id) REFERENCES handbk_items (id) ON DELETE RESTRICT,
  FOREIGN KEY (primary_diagnosis_item_id) REFERENCES handbk_items (id) ON DELETE RESTRICT
);

CREATE SEQUENCE diagnosis_register_seq;
CREATE TABLE diagnosis_register
(
  id                     INTEGER PRIMARY KEY DEFAULT nextval('diagnosis_register_seq'),
  patient_id             INTEGER NOT NULL,
  date                   DATE NOT NULL DEFAULT now(),
  diagnosis_item_id      INTEGER NOT NULL,
  diagnosis_type_item_id INTEGER NOT NULL,
  FOREIGN KEY (patient_id) REFERENCES patient_register (id) ON DELETE RESTRICT,
  FOREIGN KEY (diagnosis_item_id) REFERENCES handbk_items (id) ON DELETE RESTRICT,
  FOREIGN KEY (diagnosis_type_item_id) REFERENCES handbk_items (id) ON DELETE RESTRICT
);

CREATE SEQUENCE prescription_register_seq;
CREATE TABLE prescription_register (
  id                         INTEGER PRIMARY KEY DEFAULT nextval('prescription_register_seq'),
  patient_id                 INTEGER NOT NULL,
  prescrptn_type_item_id     INTEGER NOT NULL,
  application_date           DATE NOT NULL DEFAULT now(),
  description                TEXT NOT NULL,
  execution_date             DATE,
  result                     TEXT,
  FOREIGN KEY  (prescrptn_type_item_id) REFERENCES handbk_items (id) ON DELETE RESTRICT,
  FOREIGN KEY (patient_id) REFERENCES patient_register (id) ON DELETE RESTRICT
);

CREATE SEQUENCE inspection_register_seq;
CREATE TABLE inspection_register
(
  id                     INTEGER PRIMARY KEY DEFAULT nextval('inspection_register_seq'),
  patient_id             INTEGER NOT NULL,
  date                   DATE NOT NULL DEFAULT now(),
  inspection             TEXT NOT NULL,
  complaints             TEXT NOT NULL,   -- жалобы
  FOREIGN KEY (patient_id) REFERENCES patient_register (id) ON DELETE RESTRICT
);

ALTER TABLE lang OWNER TO "user";

ALTER TABLE handbk_items_seq OWNER TO "user";
ALTER TABLE handbk_items OWNER TO "user";

ALTER TABLE handbk_item_translations_seq OWNER TO "user";
ALTER TABLE handbk_item_translations OWNER TO "user";

ALTER TABLE staff  OWNER TO "user";
ALTER TABLE staff_seq  OWNER TO "user";

ALTER TABLE users OWNER TO "user";
ALTER TABLE users_seq  OWNER TO "user";

ALTER TABLE patient_register OWNER TO "user";
ALTER TABLE patient_seq OWNER TO "user";

ALTER TABLE diagnosis_register OWNER TO "user";
ALTER TABLE diagnosis_register_seq OWNER TO "user";
ALTER TABLE prescription_register OWNER TO "user";
ALTER TABLE prescription_register_seq OWNER TO "user";
ALTER TABLE inspection_register OWNER TO "user";
ALTER TABLE inspection_register_seq OWNER TO "user";



