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


CREATE TABLE `MODESHAPE_REPOSITORY` (
                                        `ID` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
                                        `LAST_CHANGED` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                                        `CONTENT` longblob NOT NULL,
                                        PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci

CREATE TABLE `_groups` (
                           `id` int NOT NULL AUTO_INCREMENT,
                           `group_name` varchar(30) NOT NULL,
                           `owner` varchar(30) NOT NULL,
                           `authority` varchar(30) NOT NULL,
                           `node_id` char(40) DEFAULT NULL,
                           `create_time` datetime DEFAULT NULL,
                           PRIMARY KEY (`id`),
                           UNIQUE KEY `groups_group_name_uindex` (`group_name`),
                           UNIQUE KEY `_groups_node_id_uindex` (`node_id`)
) ENGINE=InnoDB AUTO_INCREMENT=69 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci

CREATE TABLE `_roles` (
                          `id` int NOT NULL AUTO_INCREMENT,
                          `user_name` varchar(30) NOT NULL,
                          `user_roles` varchar(30) NOT NULL,
                          PRIMARY KEY (`id`),
                          UNIQUE KEY `roles_id_uindex` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci

CREATE TABLE `group_invitation_code` (
                                         `group_name` varchar(30) NOT NULL,
                                         `code` varchar(30) DEFAULT NULL,
                                         PRIMARY KEY (`group_name`),
                                         UNIQUE KEY `group_invitation_code_group_name_uindex` (`group_name`),
                                         UNIQUE KEY `group_invitation_code_pk` (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci

CREATE TABLE `user_group` (
                              `id` int NOT NULL AUTO_INCREMENT,
                              `user_name` varchar(30) NOT NULL,
                              `group_name` varchar(30) NOT NULL,
                              PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=122 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci

CREATE TABLE `user_info` (
                             `id` int NOT NULL,
                             `nick_name` varchar(24) DEFAULT NULL,
                             `unit` varchar(20) DEFAULT NULL,
                             `profession` varchar(20) DEFAULT NULL,
                             `mail` varchar(30) DEFAULT NULL,
                             `telephone` varchar(11) DEFAULT NULL,
                             `personal_statement` varchar(200) DEFAULT NULL,
                             PRIMARY KEY (`id`),
                             UNIQUE KEY `user_info_id_uindex` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci

CREATE TABLE `users` (
                         `id` int NOT NULL AUTO_INCREMENT,
                         `username` varchar(30) NOT NULL,
                         `password` varchar(30) NOT NULL,
                         PRIMARY KEY (`id`),
                         UNIQUE KEY `users_id_uindex` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci

