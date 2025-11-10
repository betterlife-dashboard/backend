CREATE DATABASE auth_db;
CREATE DATABASE todo_db;

-- 유저 생성 및 권한 부여
CREATE USER 'auth_user'@'%' IDENTIFIED BY 'authpw';
CREATE USER 'todo_user'@'%' IDENTIFIED BY 'todopw';

GRANT ALL PRIVILEGES ON auth_db.* TO 'auth_user'@'%';
GRANT ALL PRIVILEGES ON todo_db.* TO 'todo_user'@'%';
FLUSH PRIVILEGES;