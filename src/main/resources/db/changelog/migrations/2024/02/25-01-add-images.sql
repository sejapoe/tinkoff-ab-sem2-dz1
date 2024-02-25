-- liquibase formatted sql

-- changeset sejapoe:25-01-0001-init-images-table
create table if not exists files
(
    id            bigserial primary key,
    original_name varchar(255),
    stored_name   varchar(255) unique
);

-- changeset sejapoe:25-01-0002-init-posts-images-table
create table if not exists posts_images
(
    post_id  bigint references posts (id) on update cascade on delete cascade,
    image_id bigint references files (id) on delete cascade,
    constraint posts_images_pk primary key (post_id, image_id)
);