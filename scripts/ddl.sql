/* To SQLITE */
/*
create table notas (
    id integer not null primary key autoincrement
    ,titulo text not null unique
    ,data_ocorr datetime not null
    ,texto text not null
    ,projeto text not null
);
*/

/* To PostgreSQL */
create table notas (
    id serial not null primary key
    ,titulo varchar(50) not null unique
    ,data_ocorr timestamp not null
    ,texto text not null
    ,projeto varchar(50) not null
);