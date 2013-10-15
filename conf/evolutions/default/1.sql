# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table devices (
  id                        bigint auto_increment not null,
  unique_id                 varchar(255),
  name                      varchar(255),
  constraint uq_devices_unique_id unique (unique_id),
  constraint pk_devices primary key (id))
;

create table users (
  id                        bigint auto_increment not null,
  login                     varchar(255),
  password                  varchar(255),
  constraint uq_users_login unique (login),
  constraint pk_users primary key (id))
;


create table users_devices (
  users_id                       bigint not null,
  devices_id                     bigint not null,
  constraint pk_users_devices primary key (users_id, devices_id))
;



alter table users_devices add constraint fk_users_devices_users_01 foreign key (users_id) references users (id) on delete restrict on update restrict;

alter table users_devices add constraint fk_users_devices_devices_02 foreign key (devices_id) references devices (id) on delete restrict on update restrict;

# --- !Downs

SET FOREIGN_KEY_CHECKS=0;

drop table devices;

drop table users;

drop table users_devices;

SET FOREIGN_KEY_CHECKS=1;

