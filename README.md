#Spring batch job

Generic Abstract implementation which can be used to run many jobs based on the requirements conditons.

Contains 2 datasource connections
1. oracle
2. postgres

Steps:

1. DB: 


      Oracle:
      docker run -d --name xe -p 1521:1521 oracleinanutshell/oracle-xe-11g
      username : system 
      password: oracle

      Postgres:
      docker run -d --name postgres -e POSTGRES_PASSWORD=postgres -e PGDATA=/var/lib/postgresql/data/pgdata -p 5432:5432 postgres
      username: postgres
      passowrd: postgres

2. SQL cmds
   
        CREATE DATABASE postgres;
        create table emp(id number(10),name varchar2(40),age number(3));
        insert INTO  emp values (123,'dfadf',10);
        select * from emp;
        TRUNCATE TABLE emp;
        commit;


Banner : https://manytools.org/hacker-tools/ascii-banner/

End points:

      http://localhost:8080/job/first