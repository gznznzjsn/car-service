--liquibase formatted sql

--changeset gznznzjsn:create-periods
create table periods
(
    period_id    bigserial primary key,
    employee_id  bigint references employees on delete cascade,
    period_date  date     not null,
    period_start smallint not null,
    period_end   smallint not null
);
--rollback drop table assignments_periods;