CREATE DATABASE Assignment_1
USE Assignment_1

create table userTable(
	username varchar(20) not null,
	password varchar(30) null,
	lastname nvarchar(50) null,
	isAdmin bit,
	primary key(username)
)
insert into userTable(username,password,lastname,isAdmin)
	values('tienhung','se140340','Nguyen Tien Hung','true'),
		  ('thyyang','se140999','Nguyen Thi Thy Yang','false'),
		  ('hungnt','se140340','Tien Hung Nguyen','false')

create table cartIdTable(
	cartID int identity(1,1) not null,
	customerName varchar(50),
	customerAddress varchar(50),
	primary key (cartID)
)

create table itemDetailsTable(
	name varchar(50),
	quantity int,
	cartID int
	foreign key (cartID) references cartIdTable(cartID) 

)

create table product(
	id int identity(1,1) primary key,
	name varchar(50)
)

insert into product(name) 
	values('Java'),
		  ('Bean'),
		  ('Javax'),
		  ('Servlet'),
		  ('JSP')
select * from product