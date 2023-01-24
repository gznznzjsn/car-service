--liquibase formatted sql

--changeset gznznzjsn:create-employees
create table employees
(
    employee_id       bigserial primary key,
    specialization_id bigint references specializations on delete cascade,
    "name"            varchar(40) not null

);
--rollback drop table employees;