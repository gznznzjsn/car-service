--liquibase formatted sql

--changeset gznznzjsn:create-users
create table users
(
    id   bigint primary key,
    name varchar(40) not null
);
--rollback drop table users;

--changeset gznznzjsn:create-specializations
create table if not exists specializations
(
    id    bigint primary key ,
    value varchar(40) not null
);
--rollback drop table specializations;

--changeset gznznzjsn:create-employees
create table employees
(
    id                bigint primary key,
    name              varchar(40) not null,
    specialization_id bigint references specializations (id)

);
--rollback drop table employees;

--changeset gznznzjsn:create-consumable_types
create table consumable_types
(
    id       bigint primary key,
    name     varchar(40) not null,
    cost     bigint
);
--rollback drop table consumable_types;

--changeset gznznzjsn:create-consumables
create table consumables
(
    id       bigint primary key,
    consumable_type_id     bigint references consumable_types(id),
    available_quantity     bigint
);
--rollback drop table consumables;


--changeset gznznzjsn:create-tasks
create table tasks
(
    id                bigint primary key,
    name              varchar(40) not null,
    duration          int,
    cost_per_hour     bigint,
    specialization_id bigint references specializations (id)
);
--rollback drop table tasks;

--changeset gznznzjsn:create-requirements
create table requirements
(
    id          bigint primary key,
    consumable_type_id    bigint references consumable_types (id),
    required_quantity bigint
);
--rollback drop table requirements;

--changeset gznznzjsn:create-tasks_requirements
create table tasks_requirements
(
    task_id       bigint references tasks (id),
    requirement_id bigint references requirements(id),
    constraint tc_pkey primary key (task_id, requirement_id)
);
--rollback drop table tasks_requirements;

--changeset gznznzjsn:create-statuses
create table if not exists statuses
(
    id    bigint primary key,
    value varchar(40) not null
);
--rollback drop table statuses;

--changeset gznznzjsn:create-orders
create table orders
(
    id          bigint primary key,
    status_id   bigint references statuses (id),
    created_at  timestamp not null,
    finished_at timestamp,
    user_id     bigint references users (id)
);
--rollback drop table orders;

--changeset gznznzjsn:create-assignments
create table assignments
(
    id          bigint primary key,
    order_id    bigint references orders (id),
    task_id     bigint references tasks (id),
    employee_id bigint references employees (id),
    commentary  varchar(255)
);
--rollback drop table assignments;