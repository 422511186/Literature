create schema ms;
use ms;

create table _groups
(
	id int auto_increment
		primary key,
	group_name varchar(30) not null,
	owner varchar(30) not null,
	authority varchar(30) not null,
	constraint groups_group_name_uindex
		unique (group_name)
);

create table _roles
(
	id int auto_increment,
	user_name varchar(30) not null,
	user_roles varchar(30) not null,
	constraint roles_id_uindex
		unique (id)
);

alter table _roles
	add primary key (id);

create table user_group
(
	id int auto_increment
		primary key,
	user_name varchar(30) not null,
	group_name varchar(30) not null
);

create table users
(
	id int auto_increment,
	username varchar(30) not null,
	password varchar(30) not null,
	constraint users_id_uindex
		unique (id)
);

alter table users
	add primary key (id);



INSERT INTO ms.users (id, username, password) VALUES (1, 'huangzhenyu', '201921098210');
INSERT INTO ms.user_group (id, user_name, group_name) VALUES (1, 'huangzhenyu', 'admins');
INSERT INTO ms._roles (id, user_name, user_roles) VALUES (1, 'huangzhenyu', 'admin');
INSERT INTO ms._groups (id, group_name, owner, authority) VALUES (1, 'admins', 'huangzhenyu', '13');
INSERT INTO ms._groups (id, group_name, owner, authority) VALUES (2, 'ShareAll', 'huangzhenyu', '0');
INSERT INTO ms._groups (id, group_name, owner, authority) VALUES (3, 'Literature_library', 'huangzhenyu', '0,1');


