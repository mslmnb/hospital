cd C:\Program Files (x86)\PostgreSQL\10\bin

SET PGCLIENTENCODING=utf-8


psql.exe -h localhost -U postgres -d postgres -p 5432 -a -f C:\java_project\hospital-master\src\main\resources\db\initdb.sql

psql.exe -h localhost -U user -d hospital -p 5432 -a -f C:\java_project\hospital-master\src\main\resources\db\populateDB.sql
