package com.jpmorgan.onlinetest.others;

public class SQL {
}

/*
CREATE TABLE stuscore
(
  name varchar (50),    
  subject varchar (50), 
  score int,
  stuid int
);
insert into stuscore values ('zhang3','math',89,1);
insert into stuscore values ('zhang3','Chinese',71,1);
insert into stuscore values ('zhang3','english',64,1);
insert into stuscore values ('li4','math',80,2);
insert into stuscore values ('li4','Chinese',92,2);
insert into stuscore values ('li4','english',77,2);
insert into stuscore values ('w2','math',99,3);
insert into stuscore values ('w2','Chinese',98,3);
insert into stuscore values ('w2','english',97,3);
-------------------------------------------------------
1.       计算每个人的总成绩并排名
select name,sum(score) as allscore from stuscore group by name order by allscore

2.   计算每个人的总成绩并排名
select distinct t1.name,t1.stuid,t2.allscore from  stuscore t1,
( select stuid,sum(score) as allscore from stuscore group by stuid) t2
where t1.stuid=t2.stuid
order by t2.allscore desc

3. 计算每个人单科的最高成绩
 select t1.stuid,t1.name,t1.subject,t1.score from stuscore t1,
(select stuid,max(score) as maxscore from stuscore group by stuid) t2
where t1.stuid=t2.stuid and t1.score=t2.maxscore

4.计算每个人的平均成绩
select distinct t1.stuid,t1.name,t2.avgscore from stuscore t1,
(select stuid,avg(score) as avgscore from stuscore group by stuid) t2
where t1.stuid=t2.stuid

5.列出各门课程成绩最好的学生
select  t1.stuid,t1.name,t1.subject,t2.maxscore from stuscore t1,
(select subject,max(score) as maxscore from stuscore group by subject) t2
where t1.subject=t2.subject and t1.score=t2.maxscore

6.列出各门课程成绩最好的两位学生
select distinct t1.* from stuscore t1
where t1.stuid in
(select top 2 stuscore.stuid from stuscore where subject = t1.subject order by score desc)
order by t1.subject

7.学号    姓名    语文     数学     英语     总分  平均分
select stuid as 学号,name as 姓名,
sum(case when subject='Chinese' then score else 0 end) as 语文,
sum(case when subject='math' then score else 0 end) as 数学,
sum(case when subject='english' then score else 0 end) as 英语,
sum(score) as 总分, avg(score)as 平均分1
from stuscore
group by stuid,name
order by 总分 desc

10. 列出数学成绩在2-3名的学生
select t3.*  from
(
  select top 2 t2.*  from
   (select top 3 name,subject,score,stuid from stuscore where subject='数学'order by score desc) t2
 order by t2.score
) t3
order by t3.score desc


12. 课程    不及格（-59）   良（-80）   优（-100）
 select subject,
(select count(*) from stuscore where score<60 and subject=t1.subject) as 不及格,
(select count(*) from stuscore where score between 60 and 80 and subject=t1.subject) as 良,
(select count(*) from stuscore where score >80 and subject=t1.subject) as 优
from stuscore t1
group by subject

 */
