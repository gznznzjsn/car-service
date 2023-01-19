--liquibase formatted sql

--changeset gznznzjsn:create-assignments
create table assignments
(
    assignment_id        bigserial primary key,
    order_id             bigint references orders on delete cascade,
    specialization_id    bigint references specializations on delete cascade,
    employee_id          bigint references employees on delete cascade,
    assignment_status_id bigint references assignment_statuses on delete cascade,
    start_time           timestamp,
    final_cost           numeric,
    user_commentary      varchar(255),
    employee_commentary  varchar(255),
    constraint a_un unique (order_id, specialization_id)
);
--rollback drop table assignments;