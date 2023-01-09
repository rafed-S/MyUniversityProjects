

------------------------------------------- TP --------------------------------------------------

Question 1 : select * from PRESTATION;

NUMPREST
----------------------------------------
TYPEPREST
----------------------------------------
1
repas normal

2
quart de vin rouge

3
biere


NUMPREST
----------------------------------------
TYPEPREST
----------------------------------------
4
supplement frites

5
supplement chantilly

-------------------------------------------------------------------------------------------
Question 2 :  select * from USAGER;

NUMCARTE
----------------------------------------
NOM
----------------------------------------
NUMCATEG				  MTCAUTION DATECARTE
---------------------------------------- ---------- ---------
C1
Pierre
T2						  5 19-APR-14

C2
Suzanne
T1						  5 20-MAY-14

NUMCARTE
----------------------------------------
NOM
----------------------------------------
NUMCATEG				  MTCAUTION DATECARTE
---------------------------------------- ---------- ---------

C3
Michel
T2						  6 22-JUN-15

C4
Nathalie

NUMCARTE
----------------------------------------
NOM
----------------------------------------
NUMCATEG				  MTCAUTION DATECARTE
---------------------------------------- ---------- ---------
T1						  6 30-SEP-15

C5
Gerard
T2						  6 31-JAN-16

C6

NUMCARTE
----------------------------------------
NOM
----------------------------------------
NUMCATEG				  MTCAUTION DATECARTE
---------------------------------------- ---------- ---------
Bernard
T2						  7 01-APR-17


-------------------------------------------------------------------------------------------
Question 3 :  select numCarte, nom from USAGER;

NUMCARTE
----------------------------------------
NOM
----------------------------------------
C1
Pierre

C2
Suzanne

C3
Michel


NUMCARTE
----------------------------------------
NOM
----------------------------------------
C4
Nathalie

C5
Gerard

C6
Bernard


-------------------------------------------------------------------------------------------
Question 4 : select AVG(montant) as depotMoyen from DEPOT;

DEPOTMOYEN
----------
     16.25


-------------------------------------------------------------------------------------------
Question 5 : select numCarte, nom from USAGER where mtCaution = 5;

NUMCARTE
----------------------------------------
NOM
----------------------------------------
C1
Pierre

C2
Suzanne


-------------------------------------------------------------------------------------------
Question 6 : select numCarte, nom from USAGER U JOIN Categorie C on C.numCateg = U.numCateg where C.libCateg = 'petits revenus';

NUMCARTE
----------------------------------------
NOM
----------------------------------------
C2
Suzanne

C4
Nathalie


-------------------------------------------------------------------------------------------
Question 7 :  select DISTINCT U.numCarte, U.nom from USAGER U JOIN DEPOT D on U.numCarte = D.numCarte where D.montant >= 20 ORDER BY U.nom ;

NUMCARTE
----------------------------------------
NOM
----------------------------------------
C6
Bernard

C5
Gerard

C3
Michel


NUMCARTE
----------------------------------------
NOM
----------------------------------------
C4
Nathalie

C1
Pierre

-------------------------------------------------------------------------------------------
Question 8 :  select count(*) from DEPOT D JOIN USAGER U ON U.numCarte = D.numCarte where U.nom = 'Pierre' ;

  COUNT(*)
----------
	 4


-------------------------------------------------------------------------------------------
Question 9 : select U.numCarte, U.nom from USAGER U JOIN TICKET T ON U.numCarte = T.numCarte JOIN ACHAT A ON T.numTicket = A.numTicket JOIN PRESTATION P ON P.numPrest = A.numPrest  where P.typePrest = 'supplement chantilly'; 
------- OU 
select numCarte, nom from usager 
where num carte IN (Select numCarte from ticket t 
JOIN ACHAT A ON t.ticket = a.ticket 
Where numprest = (Select numPrest from prestation 
where type.Prest = 'supplement chantilly'));

NUMCARTE
----------------------------------------
NOM
----------------------------------------
C2
Suzanne

C2
Suzanne

C5
Gerard



-------------------------------------------------------------------------------------------
Question 10 :  select  count(DISTINCT numCarte) As nbPersonnes , count(numTicket) As nbTickets from TICKET 
where dateAchat ='02-OCT-15'; --> il marche pas 
where dateAchat = '02-OCT-15' ;
where dateAchat = TO_DATE('02-OCT-2015', 'dd-mon-yyyy') ;

NBPERSONNES  NBTICKETS
----------- ----------
	  2	     3


-------------------------------------------------------------------------------------------
Question 11 :  select sum(D.montant) As montantNath from USAGER U JOIN DEPOT D ON U.numCarte = D.numCarte where U.nom = 'Nathalie'; 

MONTANTNATH
-----------
	 20
-------------------------------------------------------------------------------------------
Question 12 :  select count(DISTINCT U.numCarte) from USAGER U JOIN TICKET T ON U.numCarte = T.numCarte JOIN ACHAT A ON T.numTicket = A.numTicket JOIN PRESTATION P ON P.numPrest = A.numPrest  where P.TypePrest = 'biere'; 

COUNT(DISTINCTU.NUMCARTE)
-------------------------
			0

----------------------------------------------------------------------------------------
Question 13 :  Select T.prix from Tarif T join Prestation P on P.numPrest = T.numPrest join Categorie C on C.numCateg = T.numCateg where P.typePrest = 'supplement chantilly' and C.libCateg ='petits revenus'    ;

no rows selected

----------------------------------------------------------------------------------------
Question 14 : 
create view v1 as select max(dateAchat) as DateMax from Ticket ;
 select distinct dateAchat from ticket where dateAchat = (select DateMax from v1);
 
 ----------------------------------------------------------------------------------------
 Question 15 :
 select numcarte, nom from usager where numCarte IN (Select numcarte from ticket where dateAchat = (select DateMax from v1));
TO_DATE('02-OCT-2015', 'dd-mon-yyyy');
 
 ----------------------------------------------------------------------------------------
 Question 16 :
create VIEW v2 as select max(dateAchat) As LastDay from ticket where numCarte = (Select numCarte from usager where nom = 'Pierre');
select p.typeprest, v.lastDay from prestation p, v2 v  where p.numPrest = (select numPrest from Achat A JOIN Ticket T On A.numTicket = T.numTicket and numCarte = (Select numCarte from usager where nom = 'Pierre') and DateAchat = (Select LastDay from v2));

