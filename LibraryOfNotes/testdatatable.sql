create table testnotes (
    id integer primary key generated always as identity (start with 1, increment by 1),
    title varchar(64) not null,
    content varchar(2048),
    parentid integer,
    foreign key(parentid) references testnotes(id)
);

insert into testnotes (
    title,
    content
)
values (
    'root note',
    'some text'
);
    