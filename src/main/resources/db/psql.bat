rem @echo off

rem "C:\Program Files\PostgreSQL\9.5\bin\psql.exe" -h %server% -U %username% -d %database% -p %port%

cd C:\Program Files\PostgreSQL\9.5\bin
rem "C:\Program Files\PostgreSQL\9.5\bin\psql.exe" -h localhost -U postgres -d postgres -p 5432 -a -f "C:\java\repository.git\hospital\src\main\resources\db\initdb.sql"
"C:\Program Files\PostgreSQL\9.5\bin\psql.exe" -h localhost -U postgres -d postgres -p 5432 -a -f C:\java\repository.git\hospital\src\main\resources\db\initdb.sql

pause