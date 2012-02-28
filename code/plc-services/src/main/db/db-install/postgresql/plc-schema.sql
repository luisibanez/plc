create table challenge_questions (
    patient_data_id int8 not null,
    answer varchar(255) not null,
    question varchar(255) not null
);

create table patient_account (
    id int8 not null,
    email varchar(255) not null,
    password varchar(20) not null,
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

alter table challenge_questions add constraint FKB326D8717646B8F8 foreign key (patient_data_id) references patient_account;
alter table patient_account add constraint FK4B7246F34FE3022B foreign key (patient_data_id) references patient_data;
create sequence hibernate_sequence;
