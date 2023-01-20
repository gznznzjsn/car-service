--liquibase formatted sql

--changeset gznznzjsn:fulfill-specializations
insert into specializations (value)
values ('CLEANER'),
       ('REPAIRER'),
       ('INSPECTOR');

--changeset gznznzjsn:fulfill-assignment_statuses
insert into assignment_statuses (value)
values ('NOT_SENT'),
       ('UNDER_CONSIDERATION'),
       ('ACCEPTED'),
       ('DONE');

--changeset gznznzjsn:fulfill-statuses
insert into statuses (value)
values ('NOT_SENT'),
       ('UNDER_CONSIDERATION'),
       ('IN_PROCESS'),
       ('DONE');


--changeset gznznzjsn:fulfill-consumable-types
insert into consumable_types (name, cost)
values ('tire', 1000),
       ('pin', 100),
       ('red paint', 800),
       ('black paint', 900),
       ('headlight', 2000);

--changeset gznznzjsn:fulfill-consumables
insert into consumables (consumable_type_id, available_quantity)
values (1, 20),
       (2, 1000),
       (3, 140),
       (4, 90),
       (5, 15);

--changeset gznznzjsn:fulfill-tasks-and-requirements

insert into tasks (name, duration, cost_per_hour, specialization_id)
values ('painting', 3, 10000, 1),
       ('tire replacement', 1, 2000, 2),
       ('technical inspection', 4, 1000, 3),
       ('headlight replacement', 2, 10000, 1);

insert into requirements (task_id, consumable_type_id, required_quantity)
values (2, 1, 1),
       (2, 2, 4),
       (1, 3, 5),
       (1, 4, 2),
       (4, 5, 1);


--changeset gznznzjsn:fulfill-employees
insert into employees (name, specialization_id)
values ('employee_A', 1),
       ('emloyee_B', 2),
       ('emloyee_C', 1);


--changeset gznznzjsn:fulfill-periods
insert into periods (employee_id, period_date, period_start, period_end)
values (1, '2023-01-11', 8, 20),
       (2, '2023-01-11', 8, 20),
       (2, '2023-01-12', 8, 20),
       (2, '2023-01-13', 8, 20),
       (3, '2023-01-11', 8, 20),
       (2, '2023-01-20', 8, 20);

--changeset gznznzjsn:fulfill-roles
insert into roles (value)
values ('USER'),
       ('ADMIN');

-- --changeset gznznzjsn:fulfill-users
-- insert into users (name)
-- values ('user_A'),
--        ('user_B');