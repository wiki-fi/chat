create table USERS
(
	ID INTEGER auto_increment
		primary key,
	NAME VARCHAR(256) not null
);

create table MESSAGES
(
	ID INTEGER auto_increment
		primary key,
	TEXT VARCHAR(max),
	TIMESTAMP TIMESTAMP,
	SENDER_ID INTEGER
		constraint MESSAGES_SENDER_ID_FK
			references USERS (ID),
	HASH INTEGER not null
);

create unique index MESSAGES_HASH_UINDEX
	on MESSAGES (HASH);

