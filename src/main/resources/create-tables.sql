drop table if exists registrations;
drop table if exists courses;
drop table if exists users;


create table users (
    user_id varchar check (user_id <> ''),
    role varchar(16) default 'student',
    email varchar(255) unique not null check (email <> ''),
    first_name varchar(25) not null check (first_name <> ''),
    last_name varchar(25) not null check (last_name <> ''),
    password varchar(255) not null check (password <> ''),

    constraint users_pk
    primary key (user_id)
);

create table courses (
    course_id varchar check (course_id <> ''),
    level varchar not null check (level <> ''),
    program varchar not null check (program <> ''),
    name varchar(255) unique not null check (name <> ''),
    description varchar(255) not null check (description <> ''),
    creator_id varchar not null check (creator_id <> ''),

    constraint courses_pk
    primary key (course_id),

    constraint course_creator_fk
    foreign key (creator_id)
    references users
);

create table registrations (
    registration_id serial,
    course_id varchar not null check (course_id <> ''),
    user_id varchar not null check (user_id <> ''),

    constraint registration_pk
    primary key (registration_id),

    constraint registration_course_id_fk
    foreign key (course_id)
    references courses,

    constraint registration_user_id_fk
    foreign key (user_id)
    references users
);

insert into users (	user_id, role, email, first_name, last_name, password)
values ( '33956d94-a4a0-4c88-b924-c4af2edb4c49', 'teacher', 'teacher@test.com', 'John', 'Doe', 'test');

insert into users (	user_id, role, email, first_name, last_name, password)
values ( '1fa658ad-a8fd-424a-8a82-27e492ff858e', 'student', 'student@test.com', 'Jane', 'Doe', 'test');

insert into courses ( course_id, level, program, name, description, creator_id)
values ('1394fe42-f6c9-40c2-8432-09e74f1a7dd1', '4000', 'Psych', 'Seminar on Cognitive Science', 'Self-directed study targeted to thesis track graduate students.', '33956d94-a4a0-4c88-b924-c4af2edb4c49');

insert into courses ( course_id, level, program, name, description, creator_id)
values ('1394fe42-f6c9-40c3-8432-09e74f1a7dd1', '1000', 'Bio', 'Intro to Health Science', 'Introduction, overview, and history of the Health Sciences field', '33956d94-a4a0-4c88-b924-c4af2edb4c49');

insert into registrations ( course_id, user_id )
values ('1394fe42-f6c9-40c2-8432-09e74f1a7dd1', '1fa658ad-a8fd-424a-8a82-27e492ff858e');

update courses set level = '4000', program = 'Crim',  name = 'Seminar on Cognitive Science'
where course_id = '1394fe42-f6c9-40c2-8432-09e74f1a7dd1';