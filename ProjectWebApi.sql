--create database ProjectWebApi
use ProjectWebApi
go

drop table Person
go
create table Person
(
	id int IDENTITY(1,1) PRIMARY KEY ,
	navn varchar(25),
	addresse varchar(100),
	hairFarve int,
	favorit Bit,
	tlf varchar(100),
	programSprog varchar(60)
)
go

insert into  Person values('Afrina', 'pilegårdsvej', 1, 1, '+2233345', 'C#')
insert into  Person values('Amelia', 'København k', 2, 0, '+547896', 'C#')
insert into  Person values('Mimi', 'København ø', 3, 1, '+098765', 'python')
insert into  Person values('Luise', 'Husum', 4, 0, '+6542456' ,'java')
insert into  Person values('Henrik', 'Hellerup', 5, 1, '+2233345', 'C#')


select * from Person

delete from Person where id=22