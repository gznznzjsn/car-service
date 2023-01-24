--liquibase formatted sql

--changeset gznznzjsn:create-consumables
create table consumables
(
    consumable_id      bigserial primary key,
    consumable_type_id bigint references consumable_types on delete cascade,
    available_quantity bigint
);
--rollback drop table consumables;