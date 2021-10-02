-- verificar a quantidade de sites por query. Se for mais de 30 tem duplicidade
select count(*),queryid
from querysite
group by queryid
order by count(*) desc

--deletar os sites duplicados
delete FROM querysite
WHERE id IN
    (SELECT id
    FROM 
        (SELECT id,
         ROW_NUMBER() OVER( PARTITION BY (queryid,site,ranking)
        ORDER BY  id ) AS row_num
        FROM querysite ) t
        WHERE t.row_num > 1 );

--update para popular a tabela de domínio
update querysite 
set dominio =     substring(
	             substring(link,position('https://' in link)+8),
				 0,		
	             position('/' in substring(link,position('https://' in link)+8)) 
	   )
where link like '%https://%' and dominio is null;

update querysite 
set dominio =     substring(
	             substring(link,position('http://' in link)+7),
				 0,		
	             position('/' in substring(link,position('http://' in link)+7)) 
	   )
where link like '%http://%' and dominio is null;

update querysite
set link = substring(link,0,POSITION('&amp' in link))
where link like '%&amp%';

update querysite
set link = substring(link,0,POSITION('#:' in link))
where link like '%#:%';

update querysite
set link = substring(link,0,Length(link))
where substring(link, length(link)) = '/';

-- somar a quantidade de sites por domínio - pode usar top 1, top 5, top 10
select count(*),(count(*) / (select count(*) from querysite where dominio is not null and ranking in (1,2,3,4,5))::float) * 100 as percent,dominio
from querysite
where ranking in (1,2,3,4,5)
group by dominio
order by count(*) desc;

select * from querysite where code is null and dominio = 'docs.oracle.com' order by ranking

update querysite set dominio = null where ranking in (6,7,8,9,10)

select * from query

select * from querysite where code is null and link in (select link from querysite where code is not null)
														
update querysite q1 set code = (select code from tbl q2 where q1.link = q2.link)
where code is null and link in (select link from tbl);
														
CREATE TEMP TABLE tbl AS
SELECT distinct link,code FROM querysite WHERE code is not null;

drop table tbl;

select * from tbl where code = ''													
														
select * from tbl	

select * from querysite where link like '%\%3F%'

select count(*),dominio from querysite where code is not null group by dominio;

select count(*),dominio,ranking from querysite where code is not null group by dominio,ranking order by dominio;

select * from querysite where  dominio is null

select count(distinct queryid),site 
from querysite
group by site

select * from querysite where link like '%\%5C%'

select * from querysite where link like '%\%%' and dominio is not null

update querysite 
set link = REPLACE(link,'%7D','}')
where link like '%\%7D%'

select '%'

update querysite 
set link = REPLACE(link,'%2B','+')
where link like '%\%2B%'


select REPLACE(link,'%2f','/') from querysite
where link like '%\%2f\%2f%'

select * from query where querytext like '%awful%'

delete from querysite where queryid = 9688

update query set 


select count(*),ranking,site
from querysite
where dominio = 'stackoverflow.com'
group by ranking,site

www.baeldung.com

--buscar o id para alguma query e remover indesejadas
select * from query where querytext like '%é numero primo%' and java_related is null;

update query set java_related = 'not_java' where id in (9688);

select * from query where id = 13864

select * from querysite where queryid = 12997;
delete from querysite where queryid = 6443;

--quantas querys já tiveram busca pelos sites
select count(distinct queryid) from querysite

		
select *
from query where java_related is null and id in (select distinct queryid from querysite) 
 and id not in (select distinct queryid from querysite where site = 'Google') 
order by id

select * from querysite
where queryid in (
select queryid
from querysite
group by queryid
having count(distinct site) = 3
)	

"https://www.geeksforgeeks.org/array-get-method-in-java/"

select * from querysite where dominio is null = 'www.geeksforgeeks.org' order by ranking


select distinct link from querysite where dominio = 'stackoverflow.com'
select distinct link from querysite where dominio = 'www.javatpoint.com'

update querysite set code = null where id = 142838

select * from querysite where link = 'https://www.geeksforgeeks.org/design-a-chess-game/&amp;sa=U&amp;ved=2ahUKEwjMjIL78YnyAhXEHLkGHSRUBYkQFjAAegQIChAB&amp;usg=AOvVaw0JXChF42s7_WfAoXBbI2Zz'

select * from query where id = 5614
select * from querysite where id = 13274

"https://www.geeksforgeeks.org/java-string-join-examples//"


select substring(link,0,28) || substring(link,28,POSITION('#' in substring(link,28)) - 1),	   
	   link
from querysite
where dominio = 'www.javatpoint.com' 
  and POSITION('#' in substring(link,28)) > 0
 
select *
from querysite
where dominio = 'www.javatpoint.com'  

select * from querysite where site = 'Google' and queryid not in (
	select distinct queryid from querysite where site in ('Bing'))
	
delete from querysite where site = 'Google' and queryid not in (
	select distinct queryid from querysite where site in ('Bing'));	

select * from query where id = 12405

delete from querysite where queryid = 12405

select substring(link,0,POSITION('#:' in link)),link,site
from querysite 
where link like '%#:%'
  and site = 'Bing';
  
select substring(link,0,Length(link)),link
from querysite
where substring(link, length(link)) = '/'


update querysite 
set link = substring(link,0,31) || substring(link,31,POSITION('/' in substring(link,31)))
where dominio = 'www.geeksforgeeks.org'

update querysite
set link = substring(link,0,28) || substring(link,28,POSITION('/' in substring(link,28)) - 1)
where dominio = 'www.javatpoint.com'
  and POSITION('/' in substring(link,28)) = 0;

update querysite
set link =  substring(link,0,28) || substring(link,28,POSITION('&' in substring(link,28)) - 1)
where dominio = 'www.javatpoint.com' 
  and POSITION('&' in substring(link,28)) > 0;
  
update querysite
set link =  substring(link,0,28) || substring(link,28,POSITION('#' in substring(link,28)) - 1)
where dominio = 'www.javatpoint.com' 
  and POSITION('#' in substring(link,28)) > 0;  
		 
		 select id,querytext as querytext  
				from query where java_related is null and id in (select distinct queryid from querysite) 
				and id not in (select distinct queryid from querysite where site = 'Bing') 
				order by querytext
				
select * from querysite where link = 'https://www.javatpoint.com/prime-number-program-in-java'				


