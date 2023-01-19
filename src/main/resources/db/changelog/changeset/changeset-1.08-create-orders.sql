--liquibase formatted sql

--changeset gznznzjsn:create-orders
create table orders
(
    order_id     bigserial primary key,
    status_id    bigint references statuses on delete cascade,
    user_id      bigint references users on delete cascade,
    arrival_time timestamp not null,
    created_at   timestamp not null,
    finished_at  timestamp
);
--rollback drop table orders;
