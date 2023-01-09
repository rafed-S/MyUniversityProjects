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

begin
InitProd(600);
end;

