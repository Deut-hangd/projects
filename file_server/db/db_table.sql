drop database if exists files_repository;
create database files_repository character set utf8mb4;
use files_repository;
drop table if exists my_file;
create table my_file(
id varchar(36) primary key comment '当前文件名(UUID)',
name varchar(36) not null unique comment '原始文件名',
size bigint(15) default 0 comment '文件大小' ,
type varchar(10) not null comment '文件类型',
save_address varchar(255) not null comment '文件保存地址',
create_time timestamp default now() comment '文件创建时间'
) comment '文件表';
