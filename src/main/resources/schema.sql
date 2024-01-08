create table todo 
(	
	id bigint not null,
	description varchar(255), 
	done boolean not null, 
	target_date Timestamp(6), 	
	username varchar(255), 
	primary key (id)
);