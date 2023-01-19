--liquibase formatted sql

--changeset gznznzjsn:create-statuses
create table statuses
(
    status_id bigserial primary key,
    "value"   varchar(40) not null
);
--rollback drop table statuses;