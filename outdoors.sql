/*
"jdbc:mysql://118.25.108.103:3306/outdoors",
"root",
"123456ok+CE"
*/

create database outdoors;
use outdoors;

-- 管理员表
create table manager
(
	id int AUTO_INCREMENT not null primary key,
	name varchar(20) not null,
	password varchar(32) not null
);
alter table manager AUTO_INCREMENT=1001;

-- 用户表
create table user
(
	id int AUTO_INCREMENT not null primary key,
	name varchar(20) not null,
	mail varchar(20) not null,
	tel char(11),
	password char(32) not null
	photoPath varchar(50),
);
alter table user AUTO_INCREMENT=10001;

-- 关注表
create table follow
(
	id int AUTO_INCREMENT not null primary key,
	id_follower int,
	id_followed int,
	foreign key(id_follower) references user(id),
	foreign key(id_followed) references user(id)
);
alter table follow AUTO_INCREMENT=10001;

-- 大洲表
create table continent
(
	id int AUTO_INCREMENT not null primary key,
	name char(6) not null,
	description text
);
alter table continent AUTO_INCREMENT=10001;

-- 国家表
create table country
(
	id int AUTO_INCREMENT not null primary key,
	name char(20) not null,
	id_continent int,
	description text,
	foreign key(id_continent) references continent(id)
);
alter table country AUTO_INCREMENT=10001;

-- 景点表
create table spot
(
	id int AUTO_INCREMENT not null primary key,
	name varchar(30) not null,
	id_continent int,
	id_country int,
	shortIntro text,-- 主页简介
	introduction text,-- 景点页面详细介绍
	photoPath varchar(50),
	foreign key(id_continent) references continent(id)
	foreign key(id_country) references country(id),
);
alter table spot AUTO_INCREMENT=10001;

-- 攻略表
create table strategy
(
	id int AUTO_INCREMENT not null primary key,
	id_writer int,
	id_spot int,
	title varchar(50),
	content text,
	photoPath varchar(50),
	foreign key(id_writer) references user(id),
	foreign key(id_spot) references spot(id)
);
alter table strategy AUTO_INCREMENT=10001;

-- 标签表（攻略的类别）
create table tag
(
	id int AUTO_INCREMENT not null primary key,
	name varchar(10),
	id_strategy int,
	foreign key(id_strategy) references strategy(id)
);
alter table tag AUTO_INCREMENT=10001;


-- 评论表（对攻略）
create table comment
(
	id int AUTO_INCREMENT not null primary key,
	id_writer int,
	id_strategy int,
	content text,
	photoPath varchar(50),
	foreign key(id_writer) references user(id),
	foreign key(id_strategy) references strategy(id)
);
alter table comment AUTO_INCREMENT=10001;

-- 浏览表
create table browse
(
	id int AUTO_INCREMENT not null primary key,
	id_user int,
	id_spot int,
	count int default 1,
	foreign key(id_user) references user(id),
	foreign key(id_spot) references spot(id)
);
alter table browse AUTO_INCREMENT=10001;

-- 推荐表
create table recommend
(
	id int AUTO_INCREMENT not null primary key,
	id_user int,
	id_spot int,
	foreign key(id_user) references user(id),
	foreign key(id_spot) references spot(id)
);
alter table recommend AUTO_INCREMENT=10001;
