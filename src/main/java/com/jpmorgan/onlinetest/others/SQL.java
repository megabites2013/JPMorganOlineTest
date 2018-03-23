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

SQL: select * into b from a where 1<>1
(where1=1，拷贝表结构和数据内容)


用一条SQL 语句 查询出每门课都大于80 分的学生姓名
select name from table group by name having min(fenshu)>80

stuid	name	math	Chinese	english
1	zhang3	49	71	64
2	li4	80	92	77
3	w2	99	98	97

select a.stuid, a.name,
(select b.score from stuscore b where subject='math' and b.name = a.name) as math,
(select b.score from stuscore b where subject='Chinese' and b.name = a.name) as Chinese,
(select b.score from stuscore b where subject='english' and b.name = a.name) as english
from stuscore a
group by a.stuid,a.name


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

原表:
courseid coursename score
-------------------------------------
1 Java 70
2 oracle 90
3 xml 40
4 jsp 30
5 servlet 80

显示成
1 Java 70 pass
2 oracle 90 pass
3 xml 40 fail
4 jsp 30 fail
5 servlet 80 pass
---------------------------------------------------
写出此查询语句
select courseid, coursename ,score ,decode（sign(score-60),-1,'fail','pass') as mark from course

 decode（columnname，值1,翻译值1,值2,翻译值2,...值n,翻译值n,缺省值）
 sign()函数根据取正负号，某个值是0、正数还是负数，分别返回0、1、-1，

12.通过 SQL，您如何按字母顺序选取 Persons 表中 LastName 介于 Adams 和 Carter 的所有记录？
正确答案：SELECT * FROM Persons WHERE LastName BETWEEN 'Adams' AND 'Carter'







 */
