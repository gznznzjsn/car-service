--liquibase formatted sql

--changeset gznznzjsn:create-assignment_statuses
create table assignment_statuses
(
    assignment_status_id bigserial primary key,
    "value"              varchar(40)
);
--rollback drop table assignment_statuses;