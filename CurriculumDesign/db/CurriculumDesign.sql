create database CurriculumDesign;
use CurriculumDesign;

drop table if exists USERS;
create table USERS(
USERNAME VARCHAR(30) PRIMARY KEY,
PASSWORDS VARCHAR(30),

RJ_MATCHESNUM INT,
RJ_LOSSNUM INT,
RJ_WINNUM INT,
RJ_WINRATE varchar(4),

LJ_MATCHESNUM INT,
LJ_LOSSNUM INT,
LJ_WINNUM INT,
LJ_WINRATE varchar(4)
);

select * from USERS;

update USERS set RJ_MATCHESNUM = 1,RJ_WINNUM = 1,RJ_WINRATE = '%100',RJ_LOSSNUM = 0,LJ_WINRATE = '%0' where USERNAME = 't1';

delete from USERS where USERNAME = 't2';

insert into USERS values("p0",'111', 20, 1, 12, "%60", 20, 1, 12, "%60");

#朋友表尚未创建
/*drop table if exists FRIENDS;
create table FRIENDS(
USERNAME1 VARCHAR(30),
USERNAME2 VARCHAR(30),

PRIMARY KEY(USERNAME1, USERNAME2),
FOREIGN KEY(USERNAME1) REFERENCES USERS(USERNAME) ON DELETE CASCADE ON UPDATE CASCADE,
FOREIGN KEY(USERNAME2) REFERENCES USERS(USERNAME) ON DELETE CASCADE ON UPDATE CASCADE
);
insert into FRIENDS values("test","test1");
select * from FRIENDS;*/