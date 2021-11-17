drop table if exists roles cascade;
drop table if exists users cascade;
drop table if exists courses cascade;

CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

create table users (
    user_id uuid default uuid_generate_v4 (),
    role varchar(16) default 'student',
    email varchar(255) unique not null check (email <> ''),
    first_name varchar(25) not null check (first_name <> ''),
    last_name varchar(25) not null check (last_name <> ''),
    password varchar(255) not null check (password <> ''),

    constraint users_pk
    primary key (user_id)
);

create table roles (
    role_id serial,
    role varchar(16),
    user_id uuid,

    constraint roles_pk
    primary key (role_id),

    constraint user_id_fk
    foreign key (user_id)
    references users
);

create table courses (
    course_id uuid default uuid_generate_v4 (),
    name varchar(255) unique not null check (name <> ''),
    description varchar(255) not null check (description <> ''),
    program varchar(32) not null check (program <> ''),
    creator_id uuid,

    constraint courses_pk
    primary key (course_id),

    constraint course_creator_fk
    foreign key (creator_id)
    references users
);

create table registrations (
    registration_id serial,
    course_id uuid,
    user_id uuid,

    constraint registration_pk
    primary key (registration_id),

    constraint registration_course_id_fk
    foreign key (course_id)
    references courses,

    constraint registration_user_id_fk
    foreign key (user_id)
    references users
);

insert into users (	email, first_name, last_name, password)
values ( 'teacher@test.com', 'test', 'John', 'Doe');

insert into users (	email, first_name, last_name, password)
values ( 'student@test.com', 'test', 'Jane', 'Doe');

insert into roles



