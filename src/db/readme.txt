2 database setup instructions
---------------------------------
1 create user and database
---------------------------------

login as root
========================

mysql -u root -p

mysql> create database memribox;

mysql> show databases;

mysql> CREATE USER 'memribox_user'@'localhost' IDENTIFIED BY 'memribox_p@wd';

mysql> GRANT ALL PRIVILEGES ON memribox.* TO 'memribox_user'@'localhost';

mysql> exit

2. Create tables
   change to the folder where memribox_table.sql file locates, in command line login as  memribox_user
=====================================================================================================

mysql -u memribox_user -p

mysql> use memribox;
mysql> SELECT DATABASE();

create tables
=================
mysql> source memribox_table.sql;


populate some users
======================
mysql> source data.sql;



drop user and db
=========================================
> mysql -u root -p
> DROP USER 'memribox_user'@'localhost';
> drop database memribox;
> desc mysql.user;
> select host, user, password from mysql.user;


change password

SET PASSWORD FOR 'memribox_user'@'localhost' = PASSWORD('cleartext password');

REATE USER 'memribox_user'@'%' IDENTIFIED BY 'cloudyweath3r';

