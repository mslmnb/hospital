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
  name            VARCHAR UNIQUE NOT NULL
);
CREATE UNIQUE INDEX lang_unique_name_idx ON lang (name);

CREATE SEQUENCE position_seq;
CREATE TABLE positions
(
  id              INTEGER PRIMARY KEY DEFAULT nextval('position_seq'),
  lang_id         INTEGER NOT NULL,
  name            VARCHAR NOT NULL,
  FOREIGN KEY (lang_id) REFERENCES lang (id) ON DELETE RESTRICT
);

CREATE SEQUENCE staff_seq;
CREATE TABLE staff
(
  id INTEGER PRIMARY KEY DEFAULT nextval('staff_seq'),
  name            VARCHAR NOT NULL,
  additional_name VARCHAR,
  surname         VARCHAR NOT NULL,
  position_id     INTEGER NOT NULL,
  FOREIGN KEY (position_id) REFERENCES positions (id) ON DELETE RESTRICT
);

CREATE TABLE users
(
  staff_id   INTEGER NOT NULL PRIMARY KEY,
  login      VARCHAR NOT NULL,
  password   VARCHAR NOT NULL,
  registered TIMESTAMP DEFAULT now(),
  enabled    BOOL DEFAULT TRUE,
  FOREIGN KEY (staff_id) REFERENCES staff (id) ON DELETE RESTRICT
);
CREATE UNIQUE INDEX users_unique_login_idx ON users (login);

CREATE TABLE user_roles
(
  staff_id    INTEGER NOT NULL,
  role        VARCHAR,
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

CREATE SEQUENCE diagnosis_type_seq;
CREATE TABLE diagnosis_type
(
  id              INTEGER PRIMARY KEY DEFAULT nextval('diagnosis_type_seq'),
  lang_id         INTEGER NOT NULL,
  name            VARCHAR NOT NULL,
  FOREIGN KEY (lang_id) REFERENCES lang (id) ON DELETE RESTRICT
  -- initial первичный, clinical клинический, concomitant сопутствующий, final окончательный
);

CREATE SEQUENCE diagnosis_seq;
CREATE TABLE diagnosis
(
  id              INTEGER PRIMARY KEY DEFAULT nextval('diagnosis_seq'),
  lang_id         INTEGER NOT NULL,
  name            VARCHAR NOT NULL,
  FOREIGN KEY (lang_id) REFERENCES lang (id) ON DELETE RESTRICT
);

CREATE TABLE diagnosis_register
(
  patient_id        INTEGER NOT NULL,
  datetime          TIMESTAMP NOT NULL DEFAULT now(),
  diagnosis_id      INTEGER NOT NULL,
  diagnosis_type_id INTEGER NOT NULL,
  staff_id          INTEGER NOT NULL,
  FOREIGN KEY (patient_id) REFERENCES patient_register (id) ON DELETE RESTRICT,
  FOREIGN KEY (staff_id) REFERENCES  staff (id) ON DELETE RESTRICT
);

CREATE SEQUENCE prescription_type_seq;
CREATE TABLE prescription_type
(
  id              INTEGER PRIMARY KEY DEFAULT nextval('prescription_type_seq'),
  lang_id         INTEGER NOT NULL,
  name            VARCHAR NOT NULL,
  FOREIGN KEY (lang_id) REFERENCES lang (id) ON DELETE RESTRICT
  -- лекарственный препарат, процедуру, операцию, обследование
);

-- назначения (дата, заключение, кто провел, кто назначил
CREATE TABLE prescription_register (
  patient_id            INTEGER NOT NULL,
  prescription_type_id  INTEGER NOT NULL,
  application_datetime  TIMESTAMP NOT NULL DEFAULT now(),
  applicant_id          INTEGER NOT NULL,
  description           TEXT NOT NULL,
  execution_datetime    TIMESTAMP,
  executor_id           INTEGER,
  cancelled             BOOLEAN,   -- признак отмены назначения
  result                TEXT,
  FOREIGN KEY  (prescription_type_id) REFERENCES prescription_type (id) ON DELETE RESTRICT,
  FOREIGN KEY (applicant_id) REFERENCES staff (id) ON DELETE RESTRICT,
  FOREIGN KEY (executor_id) REFERENCES staff (id) ON DELETE RESTRICT,
  FOREIGN KEY (patient_id) REFERENCES patient_register (id) ON DELETE RESTRICT
);

CREATE SEQUENCE inspection_type_seq;
CREATE TABLE inspection_type
(
  id              INTEGER PRIMARY KEY DEFAULT nextval('prescription_type_seq'),
  lang_id         INTEGER NOT NULL,
  name            VARCHAR NOT NULL,
  FOREIGN KEY (lang_id) REFERENCES lang (id) ON DELETE RESTRICT
  -- primary(первичный), repeated(повторный),
);

-- осмотры
CREATE TABLE inspection_register
(
  patient_id          INTEGER NOT NULL,
  datetime            TIMESTAMP NOT NULL DEFAULT now(),
  inspection_type_id  INTEGER NOT NULL,
  inspection          TEXT NOT NULL,
  complaints          TEXT NOT NULL,   -- жалобы
  staff_id            INTEGER NOT NULL,
  FOREIGN KEY (patient_id) REFERENCES patient_register (id) ON DELETE RESTRICT,
  FOREIGN KEY (staff_id) REFERENCES  staff (id) ON DELETE RESTRICT

);


ALTER TABLE lang OWNER TO "user";
ALTER TABLE lang_seq OWNER TO "user";

ALTER TABLE positions OWNER TO "user";
ALTER TABLE position_seq OWNER TO "user";


ALTER TABLE staff  OWNER TO "user";
ALTER TABLE staff_seq  OWNER TO "user";

ALTER TABLE users OWNER TO "user";
ALTER TABLE user_roles OWNER TO "user";

ALTER TABLE patient_register OWNER TO "user";
ALTER TABLE patient_seq OWNER TO "user";

ALTER TABLE reception OWNER TO "user";

ALTER TABLE diagnosis_type_seq OWNER TO "user";
ALTER TABLE diagnosis_type OWNER TO "user";
ALTER TABLE diagnosis_seq OWNER TO "user";
ALTER TABLE diagnosis OWNER TO "user";
ALTER TABLE diagnosis_register OWNER TO "user";

ALTER TABLE prescription_type_seq OWNER TO "user";
ALTER TABLE prescription_type OWNER TO "user";

ALTER TABLE prescription_register OWNER TO "user";

ALTER TABLE inspection_type_seq OWNER TO "user";
ALTER TABLE inspection_type OWNER TO "user";
ALTER TABLE inspection_register OWNER TO "user";




-- изсенить собственника для всех объектов БД: таблицы и счетчики
-- REASSIGN OWNED BY postgres TO user



--Общую информацию о поступлении в стационар,заполняет медсестра приемного отделения
--Назначения  делает врач
--выполнение назначений и отмену назначений делает врач или медсестра

-- на этапе формирования запросов добавить индексы

-- воспросы на консультации
-- приемлимо ли ON DELETE RESTRICT
-- почему не работает  REASSIGN OWNED BY postgres TO user
-- пул коннектов. где закрывать соединения  (finalize() в классе ConnectionPool)
-- пул коннектов. допустимо ли хранить имя проперти файла как константу в классе PropertiesUtil
-- инициализацию пула вынести с уровня репозитория на уровень контроллера(?)
-- как резолвить ссылки api webjars
--  <link rel="stylesheet" href="webjars/bootstrap/3.3.7-1/css/bootstrap.min.css">


-- планы
-- добавить логирование
-- добавить фильтр пациентов по буквам алфавита (patients.jsp)
-- написать фабрику, которая будет создавать репозитории, сервисы и контроллеры
-- выделять выписанных пациентов други м цветом (patients.jsp)
-- инициализацию пула вынести с уровня репозитория на уровень контроллера(?)