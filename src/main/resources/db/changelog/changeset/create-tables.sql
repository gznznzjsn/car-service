--liquibase formatted sql

--changeset gznznzjsn:create-users
create table users
(
    user_id bigserial primary key,
    name    varchar(40) not null
);
--rollback drop table users;

--changeset gznznzjsn:create-specializations
create table if not exists specializations
(
    specialization_id bigserial primary key,
    value             varchar(40) not null
);
--rollback drop table specializations;

--changeset gznznzjsn:create-employees
create table employees
(
    employee_id       bigserial primary key,
    name              varchar(40) not null,
    specialization_id bigint references specializations on delete cascade

);
--rollback drop table employees;

--changeset gznznzjsn:create-consumable_types
create table consumable_types
(
    consumable_type_id bigserial primary key,
    name               varchar(40) not null,
    cost               numeric
);
--rollback drop table consumable_types;

--changeset gznznzjsn:create-consumables
create table consumables
(
    consumable_id      bigserial primary key,
    consumable_type_id bigint references consumable_types on delete cascade,
    available_quantity bigint
);
--rollback drop table consumables;


--changeset gznznzjsn:create-tasks
create table tasks
(
    task_id           bigserial primary key,
    name              varchar(40) not null,
    duration          int,
    cost_per_hour     numeric,
    specialization_id bigint references specializations on delete cascade
);
--rollback drop table tasks;

--changeset gznznzjsn:create-requirements
create table requirements
(
    requirement_id     bigserial primary key,
    consumable_type_id bigint references consumable_types on delete cascade,
    required_quantity  bigint not null
);
--rollback drop table requirements;

--changeset gznznzjsn:create-tasks_requirements
create table tasks_requirements
(
    task_id        bigint references tasks on delete    cascade ,
    requirement_id bigint references requirements on delete cascade ,
    constraint tc_pkey primary key (task_id, requirement_id)
);
--rollback drop table tasks_requirements;

--changeset gznznzjsn:create-statuses
create table statuses
(
    status_id bigserial primary key,
    value     varchar(40) not null
);
--rollback drop table statuses;

--changeset gznznzjsn:create-orders
create table orders
(
    order_id    bigserial primary key,
    status_id   bigint references statuses on delete cascade ,
    created_at  timestamp not null,
    finished_at timestamp,
    user_id     bigint references users on delete cascade
);
--rollback drop table orders;

--changeset gznznzjsn:create-assignments
create table assignments
(
    assignment_id bigserial primary key,
    order_id      bigint references orders on delete cascade ,
    task_id       bigint references tasks on delete cascade ,
    employee_id   bigint references employees on delete cascade ,
    commentary    varchar(255)
);
--rollback drop table assignments;