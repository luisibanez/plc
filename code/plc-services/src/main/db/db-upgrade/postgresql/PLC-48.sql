create table oauth_consumer (
    id int8 not null,
    oauth_key text not null unique,
    secret text not null unique,
    primary key (id)
);
    
create table oauth_token (
    id int8 not null,
    token_secret text not null unique,
    token text not null unique,
    consumer_id int8 not null,
    primary key (id)
);

alter table oauth_token add constraint FK1A879E5160D6FDEF foreign key (consumer_id) references oauth_consumer;

insert into oauth_consumer(id, oauth_key, secret) values (nextval('hibernate_sequence'), 'legalconsent', 'oP9WS2QOsPSewnbzJMz0DadqK');