--liquibase formatted sql

--changeset gznznzjsn:create-roles
create table roles
(
    role_id     bigserial primary key,
    value varchar(40) not null unique
);
--rollback drop table roles;