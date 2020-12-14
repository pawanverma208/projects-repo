drop table if exists query_param;
drop table if exists query_request;
drop table if exists product;


create table query_request (
	query_id serial primary key,
	name varchar(30),
	query varchar(255),
	user_name varchar(30)
);

create table query_param (
	id serial identity primary key,
	query_request integer references query_request(query_id),
	name varchar(30),
	value varchar(30)
);

create table product(
	id integer,
	name varchar(30),
	type varchar(30),
	description text
);

insert into product values (1,'p1','t1','desc1');
insert into product values (2,'p2','t1','desc2');
insert into product values (3,'p3','t3','desc3');
insert into product values (4,'p4','t4','desc4');