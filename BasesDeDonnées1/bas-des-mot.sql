-
SQL> set linesize180

-
Moyenne du montant des dépôts ->
select avg(montant) as ... from depot

------------------------------------------


------------------------------------------

-join
select .... from usager u join categorie c on C.... = u.... where c... = '...' ;

------------------------------------------

-order by
select distinct u...,u... from usager u join .... where d... >= 20 order by u...;

------------------------------------------

-having count(*)
select distinct u....,u... from usager u,... ti,.... a, ... p where .... and ..... and a... = ti.... and a... = p.... and p... = '...' group by u..., u... having count(*) > 1;

------------------------------------------

-count & to_char
select distinct u...,count(ti...) from .. u , .. ti where u... = ti... and (to_char(ti.dateAchat,'YYYY') = '2014' or to_char(ti.dateAchat, 'YYYY') = '2015') group by u...;

------------------------------------------

-count & to_date
select count(distinct ...) as nb... ,count(...) as nb... from ... where dateAchat = TO_DATE('02-oct-2015','dd-mon-yyyy');

------------------------------------------

-sum & union
select u..., u...,sum(nbprest*prix) as recette From usager u, ... t, .. a, .. ta Where u.numcarte = t.numcarte And t... = a.... And ta.... = u... And ta... = a... Group by u...., nom UNION 
Select numCarte, nom, 0 From usager Where numCarte NOT IN (Select numCarte  From ticket) ;

------------------------------------------

-left join .. using(..)
SELECT
    SUM(Prix * nbPrest) derniereDepenseMichel
FROM
    USAGER
    LEFT JOIN TICKET USING (numCarte)
    LEFT JOIN ACHAT USING (numTicket)
    LEFT JOIN TARIF USING (numCateg, numPrest)
WHERE
    nom = 'Michel'
GROUP BY
    numCarte, nom, dateAchat
ORDER BY
    dateAchat DESC
FETCH FIRST ROW ONLY
;

------------------------------------------

-not exists
SELECT
    numCarte
FROM
    DEPOT D0
WHERE NOT EXISTS (
    SELECT *
    FROM DEPOT D1
    WHERE D0.montant != D1.montant AND D0.numCarte = D1.numCarte
);

------------------------------------------

-creat view & sum
 create view depense1 as Select u.n.., sum(nbprest*prix) recette
         From usager u, ticket t, achat a, tarif ta
         Where u.n.. = t.n..
         And t.n.. = a.n..
         And ta.n.. = u.n..
         And ta.n.. = a.n..
         Group by u.n..;

create view dep as select distinct numcarte, sum(M..) As MTDEPOT from depot group by n...;

select dep.numCarte , dep.MTDEPOT , depense1.Recette ,  dep.MTDEPOT - depense1.Recette As rest 
From dep , depense1 
Where dep.... = depense1.n.. 
order by dep.n...;

------------------------------------------

-max date la liste des .. achetées par '..' la dernière fois
select p.typePrest from prestation p,achat a,ticket ti, usager u
where
p.numPrest = a.numPrest and a.numTicket = ti.numTicket and u.numCarte = ti.numCarte and u.nom ='Pierre' 
and ti.dateAchat = (Select max(dateAchat) from Ticket ti,usager u where ti.numCarte = u.numCarte and u.nom ='Pierre') ;
