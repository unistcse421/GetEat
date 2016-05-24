/*
    Server
*/
create table restaurant(
    Rest_Name   varchar(30) primary key,
    Category    varchar(30) not null,
    Location    varchar(30),
    Number  varchar(30),
    Hours_Start varchar(30),
    Hours_End   varchar(30),
    Delivery_Fee varchar(10),
    Delivery_Min varchar(10),
	URL			varchar(150)
);
create table menu(
    Rest_Name   varchar(30),
    Menu_ID     varchar(10),
    Menu_Name   varchar(50) not null,
    Category    varchar(50) not null,
    Price   varchar(10) not null,
    Frequency   int,
    primary key (Menu_ID),
    foreign key (Rest_Name) references restaurant
);

create table review(
    Rest_name varchar(30),
    Score   varchar(1),
    People  int,
    foreign key (Rest_name) references restaurant
);

create table users(
    User_ID varchar(10) primary key,
    Name    varchar(30),
    Phone   varchar(30),
    Account varchar(30),
    Bank    varchar(30)
);

create table party(
    Party_Name  varchar(30),
    Leader_ID   varchar(30),
    Sustainability  varchar(30),
    User_ID     varchar(10),
    Location    varchar(10),
    Price       varchar(10),
    primary key (Party_Name, Leader_ID)
);
create table ordered(
    Order_ID varchar(10) primary key,
    Date    varchar(30),
    Name    varchar(30),
    Leader_ID   varchar(10),
    Rest_Name   varchar(30),
    Menu_ID     varchar(10),
    foreign key (Rest_Name) references restaurant
    );

/*
    Local
*/
create table history(
    Date    varchar(30),
    Rest_Name varchar(30),
    Price   varchar(10),
    Score   varchar(1),
    primary key (Date, Rest_Name)
);
create table friend(
    User_ID varchar(10) primary key,
    Name    varchar(10),
    Account varchar(15),
    Bank    varchar(10),
    Dept    varchar(10)
);
