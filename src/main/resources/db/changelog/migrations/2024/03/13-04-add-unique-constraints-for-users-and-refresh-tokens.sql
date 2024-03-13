-- liquibase formatted sql

-- changeset sejapoe:13-04-0001-add-unique-constraints-for-users
alter table users
    add unique (username);

-- changeset sejapoe:13-04-0002-add-unique-constraints-for-users
alter table refresh_tokens
    add unique (token);