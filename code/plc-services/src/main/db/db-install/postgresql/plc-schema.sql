create table challenge_questions (
    patient_account_id int8 not null,
    answer varchar(255) not null,
    question varchar(255) not null
);

create table patient_account (
    id int8 not null,
    guid varchar(64) not null unique,
    patient_demographics_id int8 not null,
    plc_user_id int8 not null,
    primary key (id),
    unique (plc_user_id),
    unique (patient_demographics_id)
);

create table patient_data (
    id int8 not null,
    data_source varchar(255),
    data_type varchar(255) not null,
    file_data oid not null,
    file_name varchar(255) not null,
    notes varchar(255),
    uploaded_date timestamp not null,
    version varchar(255),
    patient_account_id int8 not null,
    primary key (id)
);

create table patient_data_tags (
    patient_data_id int8 not null,
    tag varchar(255)
);

create table patient_demographics (
    id int8 not null,
    birth_country varchar(255) not null,
    birth_date date not null,
    birth_name varchar(50) not null,
    birth_place varchar(100) not null,
    first_name varchar(50) not null,
    primary key (id)
);

create table plc_user (
    id int8 not null,
    email varchar(255) not null,
    full_name varchar(255) not null,
    password varchar(255) not null,
    salt varchar(16) not null,
    username varchar(20) not null unique,
    primary key (id)
);

alter table challenge_questions add constraint FKB326D87115813BCD foreign key (patient_account_id) references patient_account;
alter table patient_account add constraint FK4B7246F3A30C05A7 foreign key (patient_demographics_id) references patient_demographics;
alter table patient_account add constraint FK4B7246F33F1B414B foreign key (plc_user_id) references plc_user;
alter table patient_data add constraint FKCBD8D8A415813BCD foreign key (patient_account_id) references patient_account;
alter table patient_data_tags add constraint FK8F39DAD4D7010927 foreign key (patient_data_id) references patient_data;
create sequence hibernate_sequence;
