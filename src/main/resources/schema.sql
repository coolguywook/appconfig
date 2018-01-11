create table appcode
(
   code varchar(100) not null,
   c_datetime timestamp,
   primary key(code)
);

create table version
(
	version varchar(10) not null,
	code varchar(100) not null,
	use	 char(1) default '0',
	data clob,
	c_datetime timestamp,
	u_datetime timestamp,
	primary key (version, code),
	foreign key (code) references appcode(code)
);