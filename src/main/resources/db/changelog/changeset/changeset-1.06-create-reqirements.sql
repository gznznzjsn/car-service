--liquibase formatted sql

--changeset gznznzjsn:create-requirements
create table requirements
(
    requirement_id     bigserial primary key,
    task_id            bigint references tasks on delete cascade,
    consumable_type_id bigint references consumable_types on delete cascade,
    required_quantity  bigint not null
);
--rollback drop table requirements;