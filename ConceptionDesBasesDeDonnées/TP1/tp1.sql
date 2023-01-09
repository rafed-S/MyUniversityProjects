TP1:

Q1)1- Afficher les noms des employés dont le salaire est supérieur à 10 000 $. (15)

select last_name from HR.EMPLOYEES where salary > 10000;

15 rows selected.

______________________________________________________________

Q2)Afficher les noms des employés dont la date d’embauche est comprise entre 01/05/2006 et
01/05/2007. (18) SELECT * FROM projet
WHERE date_debut >= to_date('1998/05/31', 'YYYY/MM/DD') ;


select last_name from HR.EMPLOYEES 
where hire_date >= to_date('2006/05/01', 'yyyy/mm/dd') 
and hire_date <= to_date('2007/05/01', 'yyyy/mm/dd');

18 rows selected.

______________________________________________________________

Q3)- Afficher les noms des employés commençant par la lettre ‘J’. (2)

select last_name from HR.EMPLOYEES where last_name like 'J%';

((( OU )))

select last_name from HR.EMPLOYEES where substr(last_name,1 , 1) = 'J';

______________________________________________________________

Q4)Afficher les noms des employés dont le nom contient deux fois la lettre ‘a’. (10)

select last_name from HR.EMPLOYEES where upper(last_name) like '%A%A%';

((( OU )))

select last_name from HR.EMPLOYEES where last_name like '%a%a%';

10 rows selected.

______________________________________________________________

Q5)Afficher les noms des employés dont le numéro du chef est 114. (5)

select last_name from HR.EMPLOYEES where manager_id like '%114%';

((( OU )))
 
select last_name from HR.EMPLOYEES where manager_id like'114';


______________________________________________________________

Q6) - Afficher les noms des départements dont le numéro du chef est 114 ou qui n’ont pas de chef. (17)

select department_name from HR.DEPARTMENTS where manager_id like'%114%' or manager_id is null;

((( OU )))

select department_name from HR.DEPARTMENTS where manager_id like'114' or manager_id  IS NULL;

17 rows selected.


____________________________________________________

Q7)Afficher les noms des départements qui ne sont pas localisés dans la ville (city) de Seattle. (6)

select department_name from HR.DEPARTMENTS d join HR.LOCATIONS l using(location_id) where city not in ('Seattle');

[[[[ error!!!! ]]]
6 rows selected.


((( OU )))

22 rows !

select state_PROVINCE from HR.LOCATIONS where upper(city) not in ('SEATTLE');


22 rows selected.

______________________________________________________________

Q8)- Créer une vue SALARIES_Seattle contenant certaines informations sur les salariés actuellement
affectés dans les départements localisés dans la ville de Seattle. (18)
SALARIES_Seattle (NO_SALARIE, NOM, PRENOM, NO_POSTE, NOM_DEPT)


create view salaries_seattles as select SALARY, LAST_NAME, FIRST_NAME , JOB_ID ,DEPARTMENT_ID from HR.EMPLOYEES JOIN HR.DEPARTMENTS USING (DEPARTMENT_ID) JOIN HR.LOCATIONS USING (LOCATION_ID) WHERE CITY = 'Seattle';

View created.

create view salaries_seattless as select city, state_PROVINCE, location_id from HR.LOCATIONS where uPper(city) in ('SEATTLE');

View created.

* encore une fois ca va donner -> 

ERROR at line 1:
ORA-00955: name is already used by an existing object


______________________________________________________________

Q9)Créer une vue SALARIES_COM comportant cinq colonnes :
SALARIES_COM (NO, NOM, PRENOM, SALAIRE, COMMISSION)

KAM :
create view SALARIES_COM as select EMPLOYEE_ID, LAST_NAME, FIRST_NAME, SALARY, COMMISSION_PCT FROM HR.EMPLOYEES WHERE SALARY IS NOT NULL AND COMMISSION_PCT *100 BETWEEN 0 AND 100;

moi : 
create view SALARIES_COMM as select EMPLOYEE_ID, LAST_NAME, FIRST_NAME, SALARY, COMMISSION_PCT FROM HR.EMPLOYEES WHERE SALARY IS NOT NULL AND COMMISSION_PCT *100 BETWEEN 0 AND 100 AND COMMISSION_PCT IS NOT NULL;

View created.

______________________________________________________________

Q10)En utilisant la vue SALARIES_COM, donner la liste des salariés (qui ont une commission) par ordre
décroissant du SALAIRE. Afficher les attributs NOM, PRENOM et SALAIRE. (35)

SELECT FIRST_NAME, LAST_NAME, SALARY FROM SALARIES_COM ORDER BY COMMISSION_PCT DESC;

35 rows selected.

_____________________________________________________________

Q11)- Afficher les noms des employés, par ordre alphabétique, qui ont un salaire supérieur au salaire
moyen. (51)

alta: better
SELECT FIRST_NAME from HR.EMPLOYEES where salary > (select avg(salary) as salary_moyen from HR.EMPLOYEES) order by first_name;

Kam:
SELECT FIRST_NAME from HR.EMPLOYEES group by first_name, salary having salary > (select avg(salary) from HR.EMPLOYEES);

51 rows selected.

_____________________________________________________________

Q12)Afficher les noms des départements dans lesquels il n’y a aucun employé. (16)

ALTA : better
select department_name from HR.departments d where not exists(select employee_id from HR.Employees e where d.department_id = e.department_id);

KAM : 
select department_id from HR.departments where department_id not in (select department_id from HR.Employees where department_id is not null);

16 rows selected.


_____________________________________________________________

Q13)Donner le nombre d’employés pour chaque poste. (19)

ALTA:
select distinct job_id ,count(employee_id) as em_id from HR.EMPLOYEES group by job_id;

19 rows selected.

_____________________________________________________________

Q14)Afficher pour tous les employés qui ont été embauchés strictement avant ‘Weiss’, leur nom et la
ville de leur département. (21)


select first_name ,last_name from hr.EMPLOYEES where hire_date < (select hire_date from hr.EMPLOYEES where first_name ='Weiss' or last_name='Weiss');


select last_name from hr.employees where hire_date < (select hire_date from hr.employees where upper(last_name) like 'WEISS');

21 rows selected.

_____________________________________________________________

Q15)Pour chaque poste, donner le nombre d’employés dont le salaire se trouve entre le minimum et
le maximum des salaires prévus pour ce poste (table JOBS). (19)


select distinct job_id ,count(employee_id) as em_id from HR.EMPLOYEES join HR.jobs using(job_id) where salary > min_salary and salary<max_salary group by job_id;


select distinct job_title from hr.employees, hr.jobs where salary >= min_salary and salary <= max_salary;

19 rows selected.


_____________________________________________________________



























