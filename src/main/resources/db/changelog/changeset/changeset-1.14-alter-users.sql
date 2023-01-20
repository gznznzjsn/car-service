--liquibase formatted sql

--changeset gznznzjsn:alter-users
alter table users
    add column email    varchar(40) unique not null default 'no@email',
    add column password varchar(255)       not null default '123',
    add column role_id  bigserial references roles on delete cascade on update no action;
--rollback alter table users
--  drop column email,
--  drop column password,
--  drop column role_id;