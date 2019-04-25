# Pico Orm in Java
This is a **sketch** of some of the key aspects of the [ORM assignment](https://github.com/datsoftlyngby/soft2019spring-databases/blob/master/assignments/assignment11.md) in the course on [databases spring 2019](https://datsoftlyngby.github.io/soft2019spring/DB_plan.html).

## Things implemented
* It can generate Java classes in a package
* It can generate a SQL schema, with tables and foreign keys
* It translates the query language into SQL

## Things not implemented
* Robustness - one inconsistency in the input and it crash (e.g. refering to an entity which do not exist)
* It does not connect to the database
* It does not instantiate the Java entity classes from the database
* Robustness - a lot of smaller and larger shortcuts are in the code.
* Test - there is a sad lack of test cases 


