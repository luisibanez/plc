create table challenge_questions (
    patient_data_id int8 not null,
    answer varchar(255) not null,
    question varchar(255) not null
);

create table patient_account (
    id int8 not null,
    email varchar(255) not null,
    guid varchar(64) not null,
    password varchar(255) not null,
    salt varchar(16) not null,
    username varchar(20) not null unique,
    patient_data_id int8 not null,
    primary key (id),
    unique (patient_data_id)
);

create table patient_data (
    id int8 not null,
    birth_country varchar(100) not null,
    birth_date date not null,
    birth_name varchar(50) not null,
    birth_place varchar(100) not null,
    first_name varchar(50) not null,
    primary key (id)
);

alter table challenge_questions add constraint FKB326D8713595807C foreign key (patient_data_id) references patient_account;
alter table patient_account add constraint FK4B7246F3D7010927 foreign key (patient_data_id) references patient_data;
create sequence hibernate_sequence;