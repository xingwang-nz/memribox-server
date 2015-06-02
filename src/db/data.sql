INSERT INTO role (id,code,description) VALUES (1,'ADMIN','Admin');
INSERT INTO role (id,code,description) VALUES (2,'USER','User');

INSERT INTO user (id,user_name,first_name,last_name,email,password,created_time,gender,birthday,native_tongue,home_country) VALUES (1,'xing','Xing','Wang','xingwang.nz@gmail.com','fe32c7602ee7e9934a31b33d884306bb8c381e12efb89e7007a4a5567ee85ea9','2014-03-28 00:04:45', 'M', '2001-01-01', 'Chinese', 'China');
INSERT INTO user (id,user_name,first_name,last_name,email,password,created_time, gender,birthday,native_tongue,home_country) VALUES (2,'david','David','Chen','david.chen@gmail.com','fe32c7602ee7e9934a31b33d884306bb8c381e12efb89e7007a4a5567ee85ea9','2014-03-28 00:04:45', 'M', '2001-02-02', 'Chinese', 'China');
INSERT INTO user (id,user_name,first_name,last_name,email,password,created_time, gender,birthday,native_tongue,home_country) VALUES (3,'toby','Toby','Liu','tobyqingnan@gmail.com','fe32c7602ee7e9934a31b33d884306bb8c381e12efb89e7007a4a5567ee85ea9','2014-03-28 00:04:45', 'M', '2001-03-03', 'English', 'NZ');
INSERT INTO user (id,user_name,first_name,last_name,email,password,created_time, gender,birthday,native_tongue,home_country) VALUES (4,'jenny','Jenny','Chen','jenny.chen.yes@gmail.com','fe32c7602ee7e9934a31b33d884306bb8c381e12efb89e7007a4a5567ee85ea9','2014-03-28 00:04:45', 'F', '2001-04-04', 'English', 'NZ');


INSERT INTO user_role (user_id, role_id) VALUES (1,1);
INSERT INTO user_role (user_id, role_id) VALUES (1,2);
INSERT INTO user_role (user_id, role_id) VALUES (2,1);
INSERT INTO user_role (user_id, role_id) VALUES (2,2);
INSERT INTO user_role (user_id, role_id) VALUES (3,1);
INSERT INTO user_role (user_id, role_id) VALUES (3,2);
INSERT INTO user_role (user_id, role_id) VALUES (4,1);
INSERT INTO user_role (user_id, role_id) VALUES (4,2);