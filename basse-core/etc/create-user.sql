CREATE USER 'basse'@'localhost' IDENTIFIED BY 'basselda';
GRANT ALL PRIVILEGES ON basse.* TO 'basse'@'localhost';
GRANT ALL PRIVILEGES ON bassetest.* TO 'basse'@'localhost';
FLUSH PRIVILEGES;
