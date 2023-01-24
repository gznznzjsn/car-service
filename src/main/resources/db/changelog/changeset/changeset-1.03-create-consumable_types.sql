--liquibase formatted sql

--changeset gznznzjsn:create-consumable_types
create table consumable_types
(
    consumable_type_id bigserial primary key,
    "name"             varchar(40) not null,
    "cost"             numeric
);
--rollback drop table consumable_types;