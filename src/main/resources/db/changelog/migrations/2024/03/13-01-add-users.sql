-- liquibase formatted sql

-- changeset sejapoe:13-01-0001-init-users-table
create table if not exists users
(
    id       bigserial primary key,
    username varchar(255),
    password varchar(255),
    enabled  bool default false
);

-- changeset sejapoe:13-01-0002-add-author-column-to-posts
alter table posts
    add column author_id bigint references users (id)