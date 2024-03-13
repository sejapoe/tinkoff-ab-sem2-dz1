-- liquibase formatted sql

-- changeset sejapoe:13-02-0001-add-role-column-to-users-table
alter table users
    add column role varchar(32) check ( role in ('ADMIN', 'USER') ) not null default 'USER'