create table research_entity (
    id int8 not null,
    authorized bool,
    description text,
    email varchar(255) not null,
    name varchar(255) not null,
    telephone_number varchar(255) not null,
    url varchar(255),
    token_id int8,
    primary key (id)
);

alter table research_entity add constraint FK5F832B071A081958 foreign key (token_id) references oauth_token;

alter table oauth_token add column authorized bool;
alter table oauth_token add column researcher bool;
update oauth_token set authorized = true;
update oauth_token set researcher = false;

alter table oauth_token alter column authorized set not null;
alter table oauth_token alter column researcher set not null;

