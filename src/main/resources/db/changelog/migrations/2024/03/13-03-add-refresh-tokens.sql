-- liquibase formatted sql

-- changeset sejapoe:13-03-0001-init-refresh-tokens-table
create table if not exists refresh_tokens
(
    id      bigserial primary key,
    user_id bigint       not null references users (id),
    token   varchar(255) not null
);