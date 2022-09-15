create table testnotes (
    id integer primary key generated always as identity (start with 1, increment by 1),
    title varchar(64) not null,
    content varchar(2048),
    parentid integer,
    foreign key(parentid) references testnotes(id)
);

create table testprojects (
    id integer primary key generated always as identity (start with 1, increment by 1),
    title varchar(64) not null    
);

create table testtasks (
    id integer primary key generated always as identity (start with 1, increment by 1),
    title varchar(64) not null,    
    content varchar(2048),
    noteid integer,
    projectid integer,
    createdate date not null,
    deadline date not null,
    foreign key(noteid) references testnotes(id),
    foreign key(projectid) references testprojects(id)
);

-- test data

insert into testnotes (
    title,
    content
)
values (
    'root note',
    'some text'
);
    

insert into testprojects (
    title    
)
values (
    'main project'    
);

insert into testtasks(
    title,
    content,
    noteid,
    projectid,
    createdate,
    deadline
) values (
    'create some data',
    'data to be created...',
    14,
    1,
    '2022-09-15',
    '2022-09-30'
);