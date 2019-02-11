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
  FOREIGN KEY (handbk_item_id) REFERENCES handbk_items (id) ON DELETE CASCADE
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
  id              INTEGER PRIMARY KEY DEFAULT nextval('patient_seq'),
  name            VARCHAR NOT NULL,
  additional_name VARCHAR,
  surname         VARCHAR NOT NULL,
  birthday        DATE NOT NULL,
  phone           VARCHAR NOT NULL,
  email           VARCHAR,
  admission_datetime    TIMESTAMP NOT NULL DEFAULT now(),
  discharge_datetime    TIMESTAMP,
  final_diagnosis_id    INTEGER,
  FOREIGN KEY (final_diagnosis_id) REFERENCES handbk_items (id) ON DELETE RESTRICT
);

CREATE TABLE diagnosis_register
(
  patient_id             INTEGER NOT NULL,
  datetime               TIMESTAMP NOT NULL DEFAULT now(),
  diagnosis_item_id      INTEGER NOT NULL,
  diagnosis_type_item_id INTEGER NOT NULL,
  staff_id               INTEGER NOT NULL,
  FOREIGN KEY (patient_id) REFERENCES patient_register (id) ON DELETE RESTRICT,
  FOREIGN KEY (staff_id) REFERENCES  staff (id) ON DELETE RESTRICT,
  FOREIGN KEY (diagnosis_item_id) REFERENCES handbk_items (id) ON DELETE RESTRICT,
  FOREIGN KEY (diagnosis_type_item_id) REFERENCES handbk_items (id) ON DELETE RESTRICT
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
ALTER TABLE prescription_register OWNER TO "user";
ALTER TABLE inspection_register OWNER TO "user";



--Общую информацию о поступлении в стационар,заполняет медсестра приемного отделения
--Назначения  делает врач
--выполнение назначений и отмену назначений делает врач или медсестра


-- планы
-- на этапе формирования запросов проверить индексы
-- при добавлении пациента добавить поле дата поступления
-- при редактировании пользователя разрешать изменять только поля телефон? емейл,
-- по нажатию кнопка выписка редактировать поля дата выписки и окончательный диагноз

-- параметры запроса handbk & handbkItemId ложить в модальное окно, тогда не надо их добавлять в save() в хвост заспроса
-- для ресурсов использовать дефолт сервлет
