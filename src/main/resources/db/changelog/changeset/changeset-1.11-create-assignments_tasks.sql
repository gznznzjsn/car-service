--liquibase formatted sql

--changeset gznznzjsn:create-assignments_tasks
create table assignments_tasks
(
    task_id       bigint references tasks on delete cascade,
    assignment_id bigint references assignments on delete cascade,
    constraint at_pkey primary key (task_id, assignment_id)
);
--rollback drop table assignments_tasks;