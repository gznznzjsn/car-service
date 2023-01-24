--liquibase formatted sql

--changeset gznznzjsn:create-tasks
create table tasks
(
    task_id           bigserial primary key,
    specialization_id bigint references specializations on delete cascade,
    "name"            varchar(40) not null,
    duration          int,
    cost_per_hour     numeric
);
--rollback drop table tasks;