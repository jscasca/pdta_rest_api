# Create the DB and the users

# Create the basic user
GRANT USAGE ON *.* TO 'api'@'localhost';
DROP USER 'api'@'localhost';
CREATE USER 'api'@'localhost' IDENTIFIED BY 'notapi';

# Create the user for queries
GRANT USAGE ON *.* TO 'query'@'localhost';
DROP USER 'query'@'localhost';
CREATE USER 'query'@'localhost' IDENTIFIED BY 'notquery';

# Create the database
DROP DATABASE IF EXISTS posdta_1_0;
CREATE DATABASE posdta_1_0 DEFAULT CHARACTER SET utf8 DEFAULT COLLATE utf8_general_ci;
USE posdta_1_0;

# Grant privileges to user
GRANT FILE ON *.* TO 'api'@'localhost';
GRANT ALL ON posdta_1_0.* TO 'api'@'localhost';

# Grant sleect privileges for queries
GRANT SELECT ON posdta_1_0.* TO 'query'@'localhost';
