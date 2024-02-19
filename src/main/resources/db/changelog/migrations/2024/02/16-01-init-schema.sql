-- liquibase formatted sql

-- changeset sejapoe:16-01-0001-init-posts-table
create table if not exists posts
(
    id    bigserial primary key,
    title varchar(255),
    text  varchar(1023)
);