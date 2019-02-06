DELETE FROM patient_register;
DELETE FROM users;
DELETE FROM staff;

DELETE FROM handbk_item_translate;
DELETE FROM handbk_items;
DELETE FROM lang_dictionary;

ALTER SEQUENCE handbk_items_seq RESTART WITH 100000;
ALTER SEQUENCE lang_dictionary_seq RESTART WITH 100000;
ALTER SEQUENCE users_seq RESTART WITH 100000;
ALTER SEQUENCE staff_seq RESTART WITH 100000;
ALTER SEQUENCE patient_seq RESTART WITH 100000;

INSERT INTO patient_register (name, additional_name, surname, birthday, phone, email) VALUES
  ('Анатолий', 'Сергеевич', 'Скоробогатов', '1968-01-05', '+7(721)2345678', 'anatoliy@mail.ru'),
  ('Степанида', 'Федоровна', 'Стеняева', '2002-05-15', '+7(705)6479812', 'step@rambler.ru'),
  ('Полина', 'Андреевна', 'Тиханович', '1950-11-20', '+7(708)7531245', 'pol_tih@gmail.com'),
  ('Оксана', 'Павловна', 'Бебикова', '1966-12-14', '+7(771)8244673', ''),
  ('Игорь', 'Леонидович', 'Логашов', '1980-05-21', '+7(778)3200247', 'logashov@mail.ru'),
  ('Азамат', 'Жолдасулы', 'Кигизбаев', '1999-03-11', '+7(747)7988214', 'cool_az@mail.ru'),
  ('Дмитрий', 'Михайлович', 'Бетин', '2001-04-14', '+7(701)4613521', ''),
  ('Дархан', 'Алиевич', 'Оразалиев', '1980-06-08', '+7(721)2345560', ''),
  ('Таисия', 'Сагындыковна', 'Бейбутова', '1940-05-01', '+7(721)2534571', '');

INSERT INTO lang_dictionary (lang, word) VALUES
  ('ru', 'программист'),                --100000
  ('ru', 'врач высшей категории'),      --100001
  ('ru', 'врач 1 категории'),           --100002
  ('ru', 'старшая медсестра'),          --100003
  ('ru', 'медсестра'),                  --100004
  ('en', 'system manager'),             --100005
  ('en', 'doctor of higher category '), --100006
  ('en', 'doctor of first category'),   --100007
  ('en', 'chief nurse'),                --100008
  ('en', 'nurse');                      --100009


INSERT INTO handbk_items (handbk) VALUES
  ('POSITION'), --100000
  ('POSITION'), --100001
  ('POSITION'), --100002
  ('POSITION'), --100003
  ('POSITION'); --100004


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
  ('Константин', 'Николаевич', 'Вигриянов', 100001),
  ('Мубарям', 'Ердебаевна', 'Ершенова', 100002),
  ('Наталья', 'Олеговна', 'Виекаева', 100003),
  ('Нина', 'Евгеньевна', 'Вилисова', 100004);

INSERT INTO users (staff_id, login, password, role) VALUES
  (100000, 'Vigel_VA', '7c6a180b36896a0a8c02787eeafb0e4c', 'ROLE_ADMIN'),
  (100001, 'Vigriyanov_KE', '6cb75f652a9b52798eb6cf2201057c73', 'ROLE_DOCTOR'),
  (100002, 'Yershenova_ME', '819b0643d6b89dc9b579fdfc9094f28e', 'ROLE_DOCTOR'),
  (100003, 'Viyekayeva_NO', '34cc93ece0ba9e3f6f235d4af979b16c', 'ROLE_NURSE'),
  (100004, 'Vilisova_NE', 'db0edd04aaac4506f7edab03ac855d56', 'ROLE_NURSE');

