--liquibase formatted sql

--changeset gznznzjsn:create-users
create table users
(
    user_id bigserial primary key,
    "name"  varchar(40) not null
);
--rollback drop table users;