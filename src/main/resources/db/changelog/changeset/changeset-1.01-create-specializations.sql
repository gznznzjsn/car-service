--liquibase formatted sql

--changeset gznznzjsn:create-specializations
create table if not exists specializations
(
    specialization_id bigserial primary key,
    "value"           varchar(40) not null
);
--rollback drop table specializations;