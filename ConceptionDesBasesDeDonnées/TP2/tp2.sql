TP2:

Q1)1- Créer les schémas de ces relations avec toutes les contraintes.

drop table  AFFECTATION;


CREATE TABLE PERSONNE (
    nopers NUMBER(6),
    prenom VARCHAR(30) NULL,
    nom2fam VARCHAR(25),
    PRIMARY KEY(nopers)
);

Table created.

CREATE TABLE POSTE (
    noposte VARCHAR(10),
    nomposte VARCHAR(35),
    PRIMARY KEY(noposte)
);

Table created.

CREATE TABLE LOCALISATION (
    noloc NUMBER(6) PRIMARY KEY,
    rue VARCHAR(40) NULL,
    ville VARCHAR(30) DEFAULT 'PARIS'
);

Table created.

CREATE TABLE SERVICE (
    noservice NUMBER(4),
    nomservice VARCHAR(30),
    noloc NUMBER(6),
    nochef NUMBER(6) NULL,
    PRIMARY KEY(noservice),
    FOREIGN KEY(noloc) REFERENCES LOCALISATION (noloc),
    FOREIGN KEY(nochef) REFERENCES PERSONNE (nopers)
);

Table created.

CREATE TABLE SALAIRE (
    noposte VARCHAR(10),
    noservice NUMBER(4),
    salairemin NUMBER(6) NULL,
    salairemax NUMBER(6) NULL,
    PRIMARY KEY (noposte, noservice),
    FOREIGN KEY(noposte) REFERENCES POSTE (noposte),
    FOREIGN KEY(noservice) REFERENCES SERVICE (noservice)
);

Table created.

CREATE TABLE AFFECTATION (
    nopers NUMBER(6) NOT NULL,
    noposte VARCHAR(10) NOT NULL,
    noservice NUMBER(4) NULL,
    debservice DATE,
    finservice DATE NULL,
    salaire NUMBER(8) NULL,
    UNIQUE(nopers, noposte, noservice),
    FOREIGN KEY(nopers) REFERENCES PERSONNE (nopers),
    FOREIGN KEY(noposte) REFERENCES POSTE (noposte),
    FOREIGN KEY(noservice) REFERENCES SERVICE (noservice),
    CHECK (finservice > debservice)
);

Table created.

____________________________________________________________________________________

Q2)Retrouver les informations suivantes sur le schéma de la table PERSONNE en
utilisant la fonction DESCRIBE. Cette commande permet d afficher le nom de chaque
colonne (ou attribut), son type, la possibilité de valeur NULL (nullable ? yes/no):

SQL> 
DESCRIBE PERSONNE;

 Name					   Null?    Type
 ----------------------------------------- -------- ----------------------------
 NOPERS 				   NOT NULL NUMBER(38)
 PRENOM 					    VARCHAR2(20)
 NOM2FAM				   NOT NULL VARCHAR2(25)

____________________________________________________________________________________

Q3)Insérer dans les tables créées des données provenant de certaines tables HR

(a) Insérer dans la table PERSONNE les informations utiles concernant tous
les employés de la table EMPLOYEES de l’utilisateur HR (107 tuples).
N.B. : first_name correspond au prénom

insert into PERSONNE (select EMPLOYEE_ID NOPERS,FIRST_NAME PRENOM,LAST_NAME NOM2FAM from HR.EMPLOYEES);

better :

INSERT INTO PERSONNE (NOPERS, PRENOM, NOM2FAM)
SELECT EMPLOYEE_ID, FIRST_NAME, LAST_NAME FROM HR.EMPLOYEES;


107 row(s) inserted.


_____________________________

(b) Peupler les tables SERVICE, LOCALISATION et POSTE à partir des
tuples des tables HR.DEPARTMENTS, HR.LOCATIONS et HR.JOBS.

/*dans l'ordre localisation puis poste puis service
car il y a des cles étranger*/


insert into LOCALISATION
(select LOCATION_ID noloc,
STREET_ADDRESS rue,
CITY ville
from HR.LOCATIONS);

insert into POSTE
(select JOB_ID noposte,
JOB_TITLE nomposte
from HR.JOBS);

insert into SERVICE
(select DEPARTMENT_ID noservice,
DEPARTMENT_NAME nomservice,
LOCATION_ID noloc,
MANAGER_ID nochef
from HR.DEPARTMENTS);



row(s) inserted.


_____________________________

(c) Initialiser la table SALAIRE à partir du produit cartésien des tables
HR.JOBS et SERVICE. On supposera , dans un premier temps, que les
salaires minimum et maximum ne dépendent que du numéro du poste (et
pas du service) .

insert into salaire
(select JOB_ID noposte,
NOSERVICE noservice,
MIN_SALARY salairemin,
MAX_SALARY salairemax
from HR.JOBS, service);

513 row(s) inserted.

_____________________________

(d) Peupler la table AFFECTATION à partir de la table HR.EMPLOYEES en
initialisant à NULL la date de fin de service.

ALTA:
insert into AFFECTATION
(select EMPLOYEE_ID nopers,
JOB_ID noposte,
DEPARTMENT_ID noservice,
HIRE_DATE debservice,
FIN_DAT finservice
SALARY salaire
from HR.EMPLOYEES);


KAM:
INSERT INTO AFFECTATION(noPERS, noPOSTE, NOSERVICE, DEBSERVICE,FINSERVICE, SALAIRE)
SELECT EMPLOYEE_ID, JOB_ID, DEPARTMENT_ID,HIRE_DATE, NULL, SALARY FROM HR.EMPLOYEES;
____________________________________________________________________________________

Q4)

(a) Augmenter de 2% le salaire minimum pour tous les postes du service ‘IT
Support’.

update salaire
set salairemin = salairemin * 1.02
where noservice = (select
noservice from service
where nomservice = 'IT Support');

/* pour vérifier l'augumentation **/
select salairemin , nomservice
from salaire join service using(noservice)
where nomservice = 'IT Support';

19 rows selected.
_____________________________

(b)Supprimer de la table SALAIRE les tuples concernant les postes dont le nom
commence par ‘sales’ dans les services dont le nom ne comporte pas
‘sales’.

delete from salaire
where noposte in (select noposte
from poste
where nomposte like 'Sales%');

54 row(s) deleted.

delete from poste
where nomposte like 'Sales%';

2 row(s) deleted.

_____________________________

(c) Supprimer le poste 'intitulé ‘poste' de la table POSTE. Que se
passe-t-il ? Renoncer à la mise à jour

delete from poste
where nomposte like 'Purchasing Manager';

ORA-02292: integrity constraint (SQL_LEQXOEWBFBRJMWGOECGSBAYEB.SYS_C00107312074) violated - child record found ORA-06512: at "SYS.DBMS_SQL", line 1721

_____________________________

(d) Ajouter un attribut COURRIEL à la table PERSONNE dont ‘confidentiel’ sera
la valeur par défaut. Vérifier.

alter table personne
add COURRIEL varchar(50) default 'confidentiel';

Table altered.

/*Vérifier.*/
select * from personne;

_____________________________

(e) Renseigner l’attribut COURRIEL à partir de l’attribut EMAIL de la table
HR.EMPLOYEES en complétant chaque valeur de email par la chaîne
‘@technissimo.fr’. Vérifier.

/* premieré étape pour copie les valeurs de employees(email) dans courriel de personne**/
update personne p
set COURRIEL =(select email
from HR.EMPLOYEES e
where p.nopers = e.EMPLOYEE_ID ) ;

107 row(s) updated.


/*** deuxiéme étape change les  valeurs de courille de personne à mail@technissimo.fr***/
update personne set COURRIEL = REPLACE( courriel ,  courriel , courriel+'@technissimo.fr' );

ORA-01722: invalid number ORA-06512: at "SYS.DBMS_SQL", line 1721

____________________________________________________________________________________















