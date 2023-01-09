TP4 :

Q1)Créer une copie de la table
CREATE TABLE toto AS (SELECT * FROM SMAIL5.PRODUIT)


drop table toto;
CREATE TABLE toto AS (SELECT * FROM PRODUIT);

_______________________________________________________

Q2) Ecrire une procédure PL/SQL nommée balai qui supprime les m produits les moins vendus
de la table PRODUIT. Réaliser la suppression à travers un curseur (CREATE CURSOR
FOR UPDATE). La procédure aura m comme paramètre IN et retournera dans deux
paramètres OUT le nombre de tuples effectivement supprimés et le nombre de tuples restants
après la suppression.


CREATE OR REPLACE PROCEDURE balai(m in integer, supp out integer, reste out integer)  as
    CURSOR C1 IS
      SELECT PROD#, LIBELLE, PU , TOTAL_VENTES, rownum
        FROM toto
        where rownum <= m
        order by total_ventes asc
        for update;

begin

    for cur1 in C1

    loop
        --supp := cur1;
        delete from toto
        where prod# =cur1.prod#;
        reste:=  cur1.rownum;

        end loop;
        commit;
       select count(rownum) into reste from toto;
       select count(rownum) -reste  into supp from produit;


end balai;


Procedure created.

_______________________________________________________

(3)Une fois la procédure mise au point, donner le droit d’exécution à un(e) camarade (GRANT
EXECUTE ON nom_proc TO nom_camarade). La faire tester par ce(tte) camarade.

declare
res integer;
supp integer;
begin
balai(598, supp, res) ;
dbms_output.put_line(res);
dbms_output.put_line(supp);

end;


Statement processed.
0
0

_______________________________________________________

(4) Définir un trigger nommé SET_TV qui se déclenche avant l’insertion d’un nouveau produit.
Ce trigger teste si l’attribut TOTAL_VENTES est NULL et le positionne à zéro si c’est le
cas. Tester que le trigger fonctionne dans au moins deux cas différents.

drop table produit;

Table dropped.

create table produit
       ( prod#   int not null,
         libelle varchar(20),
         pu      float not null,
         total_ventes number(10));

create or replace procedure InitProd(NbrProd IN int)
as
   prix  produit.pu%TYPE;
   i int;
   totalv produit.total_ventes%TYPE;
begin
   i := 1;
   while (i <= NbrProd) loop
     totalv := trunc(dbms_random.value(1,10))*1000;
     prix := trunc(dbms_random.value(1,10))*2.5;
     insert into produit
     values(i, 'Produit'||TO_CHAR(i,'999999'), prix, totalv);
     i := i + 1;
   end loop;
end;

Table created.
Procedure created.


_______________________________________________________






























