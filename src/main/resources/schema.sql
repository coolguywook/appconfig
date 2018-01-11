create table appcode
(
   code integer not null,
   c_datetime timestamp,
   primary key(code)
);

create table version
(
	version varchar(10) not null,
	code integer not null,
	use	 char(1) default '0',
	data clob,
	c_datetime timestamp,
	u_datetime timestamp,
	primary key (version, code),
	foreign key (code) references appcode(code)
);