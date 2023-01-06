--liquibase formatted sql

--changeset gznznzjsn:fulfill-specializations
insert into specializations (value)
values ('CLEANER'),
       ('REPAIRER'),
       ('INSPECTOR');

--changeset gznznzjsn:fulfill-statuses
insert into statuses (value)
values ('under_consideration'),
       ('in_process'),
       ('done'),
       ('rejected');


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
values ('painting', 8, 10000, 1),
       ('tire replacement', 1, 2000, 2),
       ('technical inspection', 4, 1000, 3),
       ('headlight replacement', 8, 10000, 1);

insert into requirements (consumable_type_id, required_quantity)
values (1, 1),
       (2, 4),
       (3, 5),
       (4, 2),
       (5, 1);

insert into tasks_requirements
values (1, 3),
       (1, 4),
       (2, 1),
       (2, 2),
       (4, 5);

--changeset gznznzjsn:fulfill-employees
insert into employees (name, specialization_id)
values ('employee_A', 1),
       ('emloyee_B', 2),
       ('emloyee_C', 2);


--changeset gznznzjsn:fulfill-users
insert into users (name)
values ('user_A'),
       ('user_B')
