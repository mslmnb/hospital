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

CREATE SEQUENCE lang_seq ;
CREATE TABLE lang
(
  id              INTEGER PRIMARY KEY DEFAULT nextval('lang_seq'),
  name            VARCHAR NOT NULL
);
CREATE UNIQUE INDEX lang_unique_name_idx ON lang (name);

CREATE SEQUENCE lang_dictionary_seq ;
CREATE TABLE lang_dictionary
(
  id              INTEGER PRIMARY KEY DEFAULT nextval('lang_dictionary_seq'),
  lang_id         INTEGER NOT NULL,
  word            VARCHAR NOT NULL,
  FOREIGN KEY (lang_id) REFERENCES lang (id) ON DELETE RESTRICT
);

CREATE SEQUENCE handbk_items_seq ;
CREATE TABLE handbk_items
(
  id              INTEGER PRIMARY KEY DEFAULT nextval('handbk_items_seq'),
  handbk          VARCHAR NOT NULL
);

CREATE TABLE handbk_item_translate
(
  item_id             INTEGER NOT NULL,
  lang_dictionary_id  INTEGER NOT NULL,
  FOREIGN KEY (item_id) REFERENCES handbk_items (id) ON DELETE RESTRICT,
  FOREIGN KEY (lang_dictionary_id) REFERENCES lang_dictionary (id) ON DELETE RESTRICT
);

-- *********************************
-- CREATE SEQUENCE position_seq;
-- CREATE TABLE positions
-- (
--   id              INTEGER PRIMARY KEY DEFAULT nextval('position_seq'),
--   lang_id         INTEGER NOT NULL,
--   name            VARCHAR NOT NULL,
--   FOREIGN KEY (lang_id) REFERENCES lang (id) ON DELETE RESTRICT
-- );
--
-- CREATE SEQUENCE diagnosis_type_seq;
-- CREATE TABLE diagnosis_type
-- (
--   id              INTEGER PRIMARY KEY DEFAULT nextval('diagnosis_type_seq'),
--   lang_id         INTEGER NOT NULL,
--   name            VARCHAR NOT NULL,
--   FOREIGN KEY (lang_id) REFERENCES lang (id) ON DELETE RESTRICT
--   -- initial первичный, clinical клинический, concomitant сопутствующий, final окончательный
-- );
--
-- CREATE SEQUENCE diagnosis_seq;
-- CREATE TABLE diagnosis
-- (
--   id              INTEGER PRIMARY KEY DEFAULT nextval('diagnosis_seq'),
--   lang_id         INTEGER NOT NULL,
--   name            VARCHAR NOT NULL,
--   FOREIGN KEY (lang_id) REFERENCES lang (id) ON DELETE RESTRICT
-- );
--
-- CREATE SEQUENCE prescription_type_seq;
-- CREATE TABLE prescription_type
-- (
--   id              INTEGER PRIMARY KEY DEFAULT nextval('prescription_type_seq'),
--   lang_id         INTEGER NOT NULL,
--   name            VARCHAR NOT NULL,
--   FOREIGN KEY (lang_id) REFERENCES lang (id) ON DELETE RESTRICT
--   -- лекарственный препарат, процедуру, операцию, обследование
-- );
--
-- CREATE SEQUENCE inspection_type_seq;
-- CREATE TABLE inspection_type
-- (
--   id              INTEGER PRIMARY KEY DEFAULT nextval('prescription_type_seq'),
--   lang_id         INTEGER NOT NULL,
--   name            VARCHAR NOT NULL,
--   FOREIGN KEY (lang_id) REFERENCES lang (id) ON DELETE RESTRICT
--   -- primary(первичный), repeated(повторный),
-- );
--
-- ALTER TABLE positions OWNER TO "user";
-- ALTER TABLE position_seq OWNER TO "user";
-- ALTER TABLE diagnosis_type_seq OWNER TO "user";
-- ALTER TABLE diagnosis_type OWNER TO "user";
-- ALTER TABLE diagnosis_seq OWNER TO "user";
-- ALTER TABLE diagnosis OWNER TO "user";
-- ALTER TABLE prescription_type_seq OWNER TO "user";
-- ALTER TABLE prescription_type OWNER TO "user";
-- ALTER TABLE inspection_type_seq OWNER TO "user";
-- ALTER TABLE inspection_type OWNER TO "user";

-- *********************************
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

CREATE TABLE users
(
  staff_id        INTEGER NOT NULL PRIMARY KEY,
  login           VARCHAR NOT NULL,
  password        VARCHAR NOT NULL,
  registered      TIMESTAMP DEFAULT now(),
  enabled         BOOL DEFAULT TRUE,
  CONSTRAINT users_unique_login_idx UNIQUE (login),
  FOREIGN KEY (staff_id) REFERENCES staff (id) ON DELETE RESTRICT
);
--CREATE UNIQUE INDEX users_unique_login_idx ON users (login);

CREATE TABLE user_roles
(
  staff_id    INTEGER NOT NULL,
  role             VARCHAR,
  CONSTRAINT staff_roles_idx UNIQUE (staff_id, role),
  FOREIGN KEY (staff_id) REFERENCES users (staff_id) ON DELETE RESTRICT
);
-- admin, doctor, norse

CREATE SEQUENCE patient_seq;
CREATE TABLE patient_register
(
  id              INTEGER PRIMARY KEY DEFAULT nextval('patient_seq'),
  name            VARCHAR NOT NULL,
  additional_name VARCHAR,
  surname         VARCHAR NOT NULL,
  birth_day       DATE NOT NULL,
  phone           VARCHAR NOT NULL,
  email           VARCHAR
  -- лекарственная непереносимость
  -- группа крови
  -- место работы, учебы
);

CREATE TABLE reception
(
  patient_id   INTEGER NOT NULL,
  admission_datetime    TIMESTAMP NOT NULL DEFAULT now(),
  discharge_datetime    TIMESTAMP,
  FOREIGN KEY (patient_id) REFERENCES patient_register (id) ON DELETE RESTRICT

--  признак поступления
--  если истина, то надо указать в какое отделение направлен
--  если ложь, то указать причину отказа
);

CREATE TABLE diagnosis_register
(
  patient_id        INTEGER NOT NULL,
  datetime          TIMESTAMP NOT NULL DEFAULT now(),
  diagnosis_id      INTEGER NOT NULL,
  diagnosis_type_id INTEGER NOT NULL,
  staff_id          INTEGER NOT NULL,
  FOREIGN KEY (patient_id) REFERENCES patient_register (id) ON DELETE RESTRICT,
  FOREIGN KEY (staff_id) REFERENCES  staff (id) ON DELETE RESTRICT,
  FOREIGN KEY (diagnosis_id) REFERENCES handbk_items (id) ON DELETE RESTRICT,
  FOREIGN KEY (diagnosis_type_id) REFERENCES handbk_items (id) ON DELETE RESTRICT
);

-- назначения (дата, заключение, кто провел, кто назначил
CREATE TABLE prescription_register (
  patient_id                 INTEGER NOT NULL,
  prescrptn_type_item_id     INTEGER NOT NULL,
  application_datetime       TIMESTAMP NOT NULL DEFAULT now(),
  applicant_id               INTEGER NOT NULL,
  description                TEXT NOT NULL,
  execution_datetime         TIMESTAMP,
  executor_id                INTEGER,
  cancelled                  BOOLEAN,   -- признак отмены назначения
  result                     TEXT,
  FOREIGN KEY  (prescrptn_type_item_id) REFERENCES handbk_items (id) ON DELETE RESTRICT,
  FOREIGN KEY (applicant_id) REFERENCES staff (id) ON DELETE RESTRICT,
  FOREIGN KEY (executor_id) REFERENCES staff (id) ON DELETE RESTRICT,
  FOREIGN KEY (patient_id) REFERENCES patient_register (id) ON DELETE RESTRICT
);

-- осмотры
CREATE TABLE inspection_register
(
  patient_id             INTEGER NOT NULL,
  datetime               TIMESTAMP NOT NULL DEFAULT now(),
  inspectn_type_item_id  INTEGER NOT NULL,
  inspection             TEXT NOT NULL,
  complaints             TEXT NOT NULL,   -- жалобы
  staff_id               INTEGER NOT NULL,
  FOREIGN KEY (patient_id) REFERENCES patient_register (id) ON DELETE RESTRICT,
  FOREIGN KEY (staff_id) REFERENCES  staff (id) ON DELETE RESTRICT,
  FOREIGN KEY  (inspectn_type_item_id) REFERENCES handbk_items (id) ON DELETE RESTRICT
);


ALTER TABLE handbk_items_seq OWNER TO "user";
ALTER TABLE handbk_items OWNER TO "user";

ALTER TABLE handbk_item_translate OWNER TO "user";

ALTER TABLE lang_dictionary_seq OWNER TO "user";
ALTER TABLE lang_dictionary OWNER TO "user";

ALTER TABLE lang OWNER TO "user";
ALTER TABLE lang_seq OWNER TO "user";

ALTER TABLE staff  OWNER TO "user";
ALTER TABLE staff_seq  OWNER TO "user";

ALTER TABLE users OWNER TO "user";
ALTER TABLE user_roles OWNER TO "user";

ALTER TABLE patient_register OWNER TO "user";
ALTER TABLE patient_seq OWNER TO "user";

ALTER TABLE reception OWNER TO "user";
ALTER TABLE diagnosis_register OWNER TO "user";
ALTER TABLE prescription_register OWNER TO "user";
ALTER TABLE inspection_register OWNER TO "user";


-- изсенить собственника для всех объектов БД: таблицы и счетчики
-- REASSIGN OWNED BY postgres TO user



--Общую информацию о поступлении в стационар,заполняет медсестра приемного отделения
--Назначения  делает врач
--выполнение назначений и отмену назначений делает врач или медсестра



-- вопросы на консультации
-- приемлимо ли ON DELETE RESTRICT
-- для сообщений логера использовать только английский и напрямую строковые литералы, без создания строковых констант


-- планы
-- добавить кеширование пароля
-- мапу (роли -> доступ к ресурсу) перенести из SecutityFilter в security.xml файл
-- на этапе формирования запросов проверить индексы
-- сделать отдельно языкой фильтр
