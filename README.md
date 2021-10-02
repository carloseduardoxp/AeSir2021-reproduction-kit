Readability and Understandability of Snippets Recommendedby General-purpose Web Search Engines: a Comparative Study
=========================================================================================

```
Readability and Understandability of Snippets Recommendedby General-purpose Web Search Engines: a Comparative Study
Carlos Eduardo C. Dantas; Marcelo A. Maia
```

[![DOI](https://zenodo.org/badge/138428994.svg)](https://zenodo.org/record/5544535#.YR8B4DrQ9H6)

Prerequisites
-----------------------------------------------------------

Softwares:

```
1. Java 1.8+
2. Eclipse IDE for Java EE developers or Spring Tool Suite
3. RStudio and R
4. Postgres 12.7 - Configure your DB to accept local connections. An example of pg_hba.conf configuration:
```

```
..
\# TYPE  DATABASE        USER            ADDRESS                 METHOD
\# "local" is for Unix domain socket connections only
local   all             all                                     md5
\# IPv4 local connections:
host    all             all             127.0.0.1/32            md5
..
```

5. PgAdmin (we used PgAdmin 4) but feel free to use any DB tool for PostgreSQL.


Configuring the dataset
-----------------------------------------------------------

1. On your DB tool, create a new database named stackoverflow2019journaldycrokage. This is a query example:

```
CREATE DATABASE stackoverflow2019journaldycrokage
  WITH OWNER = postgres
       ENCODING = 'UTF8'
       TABLESPACE = pg_default
       LC_COLLATE = 'en_US.UTF-8'
       LC_CTYPE = 'en_US.UTF-8'
       CONNECTION LIMIT = -1;
```
2. Download and import the tables [query and querysite](https://github.com/carloseduardoxp/AeSir2021-reproduction-kit/tree/master/1-tables) inside the database

3. Assert the database is sound. Execute the following SQL command: 
    
```
select count(*) from querysite where readability is not null
```

Import the Application 

3. Import the [QUACER repository](https://github.com/carloseduardoxp/AeSir2021-reproduction-kit/tree/master/2-JavaProject/QUACER)  into your Spring Tool Suite 4 IDE

```
right click on mouse - import - existing maven projects - select the clone directory.
```

4. Change the configurations on src/main/resources/config/application.properties

```
spring.datasource.username = your db user

spring.datasource.password= = your db password

spring.datasource.url= your database URL, as for example: jdbc:postgresql://localhost:5432/stackoverflow2019journaldycrokage

QUACER_HOME = the complete path of the project home folder 
QUACER_TEMP_FOLDER = the complete path for a tmp folder to receive java classes
```

Step 1 - Extract links 
-----------------------------------------------------------

Uncomment the @Qualifier("sitesQueries") line on Principal.java file. And comment the other files with  @Qualifier

After finish, the table postsmin will insert rows in the table querysite with links for each query

Step 2 - Extract Code snippets 
-----------------------------------------------------------

Uncomment the @Qualifier("ExtractCodeSnippet") line on Principal.java file. And comment the other files with  @Qualifier

After finish, the table postsmin will update querysite table including code snippet for each link

Step 3 - Collect understandability and readability metric values
-----------------------------------------------------------

Uncomment the @Qualifier("metricAction") line on Principal.java file. And comment the other files with  @Qualifier

After finish, the table postsmin will update querysite table including understandability and readability metrics

Step 4 - Generate CSV files

1 - run the following script on PgAdmin 4

```
Copy (select site,dominio,ranking,queryId,round(readability::numeric,4) as readability,understandability,understand_normalized from querysite where code is not null order by queryId) 
To '/tmp/saida.csv' With CSV DELIMITER ';' HEADER;
```

2 - run the file ScriptGenerateCSV.java, changing the path to the files saida.csv, readability.csv and understandability.csv.

Step 4 - Results
-----------------------------------------------------------

Location: [3-Results](https://github.com/carloseduardoxp/AeSir2021-reproduction-kit/tree/master/3-Results)


The R scripts are:

- script.R - is a R script to calculate the aNova and generate tukey test.

- pizza.R - is a R script to generate the pizza graph


Something not working as expected?
------------------------------------------------------------------------
Create an issue from [here](https://github.com/carloseduardoxp/AeSir2021-reproduction-kit/issues/new)
