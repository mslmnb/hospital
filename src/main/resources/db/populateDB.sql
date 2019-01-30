DELETE FROM patient_register;
DELETE FROM user_roles;
DELETE FROM users;
DELETE FROM staff;

DELETE FROM handbk_item_translate;
DELETE FROM handbk_items;
DELETE FROM lang_dictionary;
DELETE FROM lang;

ALTER SEQUENCE handbk_items_seq RESTART WITH 100000;
ALTER SEQUENCE lang_dictionary_seq RESTART WITH 100000;
ALTER SEQUENCE lang_seq RESTART WITH 100000;
ALTER SEQUENCE staff_seq RESTART WITH 100000;
ALTER SEQUENCE patient_seq RESTART WITH 100000;

INSERT INTO patient_register (name, additional_name, surname, birth_day, phone, email) VALUES
  ('Анатолий', 'Сергеевич', 'Скоробогатов', '1968-01-05', '+7(721)2345678', 'anatoliy@mail.ru'),
  ('Степанида', 'Федоровна', 'Стеняева', '2002-05-15', '+7(705)6479812', 'step@rambler.ru'),
  ('Полина', 'Андреевна', 'Тиханович', '1950-11-20', '+7(708)7531245', 'pol_tih@gmail.com'),
  ('Оксана', 'Павловна', 'Бебикова', '1966-12-14', '+7(771)8244673', ''),
  ('Игорь', 'Леонидович', 'Логашов', '1980-05-21', '+7(778)3200247', 'logashov@mail.ru'),
  ('Азамат', 'Жолдасулы', 'Кигизбаев', '1999-03-11', '+7(747)7988214', 'cool_az@mail.ru'),
  ('Дмитрий', 'Михайлович', 'Бетин', '2001-04-14', '+7(701)4613521', ''),
  ('Дархан', 'Алиевич', 'Оразалиев', '1980-06-08', '+7(721)2345560', ''),
  ('Таисия', 'Сагындыковна', 'Бейбутова', '1940-05-01', '+7(721)2534571', '');

INSERT INTO lang (name) VALUES
  ('русский'), --100000
  ('english'); --100001

INSERT INTO lang_dictionary (lang_id, word) VALUES
  (100000, 'программист'),                --100000
  (100000, 'врач высшей категории'),      --100001
  (100000, 'врач 1 категории'),           --100002
  (100000, 'старшая медсестра'),          --100003
  (100000, 'медсестра'),                  --100004
  (100001, 'system manager'),             --100005
  (100001, 'doctor of higher category '), --100006
  (100001, 'doctor of first category'),   --100007
  (100001, 'chief nurse'),                --100008
  (100001, 'nurse');                      --100009


INSERT INTO handbk_items (handbk) VALUES
  ('POSITIONS'), --100000
  ('POSITIONS'), --100001
  ('POSITIONS'), --100002
  ('POSITIONS'), --100003
  ('POSITIONS'); --100004


INSERT INTO handbk_item_translate (item_id, lang_dictionary_id) VALUES
  (100000, 100000),
  (100001, 100001),
  (100002, 100002),
  (100003, 100003),
  (100004, 100004),
  (100000, 100005),
  (100001, 100006),
  (100002, 100007),
  (100003, 100008),
  (100004, 100009);


INSERT INTO staff (name, additional_name, surname, position_item_id) VALUES
  ('Валерий', 'Алексеевич', 'Вигель', 100000),
  ('Константинович', 'Ежиков', 'Вигриянов', 100001),
  ('Мубарям', 'Ердебаевна', 'Ершенова', 100002),
  ('Наталья', 'Олеговна', 'Виекаева', 100003),
  ('Нина', 'Евгеньевна', 'Вилисова', 100004);

INSERT INTO users (staff_id, login, password) VALUES
  (100000, 'Vigel_VA', '7c6a180b36896a0a8c02787eeafb0e4c'),
  (100001, 'Vigriyanov_KE', '6cb75f652a9b52798eb6cf2201057c73'),
  (100002, 'Yershenova_ME', '819b0643d6b89dc9b579fdfc9094f28e'),
  (100003, 'Viyekayeva_NO', '34cc93ece0ba9e3f6f235d4af979b16c'),
  (100004, 'Vilisova_NE', 'db0edd04aaac4506f7edab03ac855d56');

INSERT INTO user_roles (role, staff_id) VALUES
  ('ROLE_ADMIN', 100000),
  ('ROLE_DOCTOR', 100001),
  ('ROLE_DOCTOR', 100002),
  ('ROLE_NURSE', 100003),
  ('ROLE_NURSE', 100004);
