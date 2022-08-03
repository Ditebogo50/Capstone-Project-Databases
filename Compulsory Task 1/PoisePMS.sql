create database if not exists PoisePMS;
use PoisePMS;
drop table if exists project;
create table project (
    num int,
    proj_name varchar(250),
    address_id int(250),
    total_fee float,
    paid_amount float,
    deadline date,
    engineer_id int,
    contractor_id int,
    architect_id int,
    customer_id int,
    primary key (num)
);

insert into project values (01, 'A Property', 01, 500000, 250000, '2022-09-09', 01, 01, 01,01), 
(02, 'B Property', 0, 600000, 300000, '2022-11-11', 02, 02, 02,02);


select * from project;

drop table if exists project_address;
create table project_address (
    id int,
    num int,
    street varchar(250),
    city varchar(250),
    postal varchar(250),
    building_type varchar(250),
    ERF varchar(250),
    primary key (id)
);

insert into project_address values (01, 77, 'Peters Strret', 'Pretoria', '0988', 'House', '9907'),
(02, 22, 'Kay Street', 'Cape Town', '0689', 'House', '2907');


select * from project_address;

drop table if exists engineer;
create table engineer (
    id int,
    eng_name varchar(250),
    tel_number varchar(250),
    email varchar(250),
    primary key (id)
);

insert into engineer values (01, 'Mike', '0112234787', 'mike@gmail.com'),
(02, 'Kate', '0110434788', 'kate@gmail.com');


select * from engineer;

drop table if exists contractor;
create table contractor (
    id int,
    contractor_name varchar(250),
    tel_number varchar(250),
    email varchar(250),
    primary key (id)
);

insert into contractor values(01, 'John', '0917034787', 'john@gmail.com'),
(02, 'Pearl', '0875463379', 'pearl@gmail.com');


select * from contractor;

drop table if exists architect;
create table architect (
    id int,
    architect_name varchar(250),
    tel_number varchar(250),
    email varchar(250),
    primary key (id)
);

insert into architect values (01, 'Sue', '09170864787', 'sue@gmail.com'),
(02, 'Mary', '0214567836', 'mary@gmail.com');


select * from architect;

drop table if exists customer;
create table customer (
    id int,
    customer_name varchar(250),
    tel_number varchar(250),
    email varchar(250),
    primary key (id)
);

insert into customer values (01, 'Patrick', '00314567945', 'patrick@gmail.com'),
(02, 'Hendrik', '0987675435', 'hendrik@gmail.com');


select * from customer;
