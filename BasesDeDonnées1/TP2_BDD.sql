-- 1. Liste des prestations jamais achetées. (typePrest) 1 tuple attendu

SELECT typePrest
FROM PRESTATION
WHERE NOT EXISTS
    (SELECT *
     FROM ACHAT
     WHERE ACHAT.numPrest = PRESTATION.numPrest);

-- TYPEPREST
-- ---------
-- biere


-- 2. Liste des prestations achetées par 'Gerard'. (typePrest) 3 tuples attendus

SELECT
    DISTINCT typePrest
FROM
    PRESTATION
    INNER JOIN ACHAT USING (numPrest)
    INNER JOIN TICKET USING (numTicket)
    INNER JOIN USAGER USING (numCarte)
WHERE
    USAGER.Nom = 'Gerard';
    
-- TYPEPREST
-- ---------
-- repas normal
-- supplement frites
-- supplement chantilly


-- 3. Liste des prestations jamais achetées par 'Michel'. (typePrest) 4 tuples attendus

SELECT typePrest
FROM PRESTATION U
WHERE typePrest NOT IN
    (SELECT
        DISTINCT typePrest
    FROM
        PRESTATION
        INNER JOIN ACHAT USING (numPrest)
        INNER JOIN TICKET USING (numTicket)
        INNER JOIN USAGER USING (numCarte)
    WHERE
        USAGER.Nom = 'Michel' AND
        U.typePrest = PRESTATION.typePrest);

-- TYPEPREST
-- ---------
-- quart de vin rouge
-- biere
-- supplement frites
-- supplement chantilly

-- 4. Nombre de personnes n'ayant jamais acheté du 'supplement chantilly'. (nbp) Résultat attendu : 4

SELECT COUNT(numCarte) AS nbp
FROM USAGER U
WHERE NOT EXISTS
    (
        SELECT *
        FROM USAGER
             INNER JOIN TICKET ON USAGER.numCarte = TICKET.numCarte
             INNER JOIN ACHAT USING (numTicket)
             INNER JOIN PRESTATION USING (numPrest)
        WHERE USAGER.numCarte = U.numCarte
        AND PRESTATION.typePrest = 'supplement chantilly'
    );

-- 5. Liste des usagers qui n'ont jamais acheté le 'supplement frites'

SELECT numCarte, Nom
FROM USAGER U
WHERE NOT EXISTS
    (
        SELECT *
        FROM USAGER
             INNER JOIN TICKET ON USAGER.numCarte = TICKET.numCarte
             INNER JOIN ACHAT USING (numTicket)
             INNER JOIN PRESTATION USING (numPrest)
        WHERE USAGER.numCarte = U.numCarte AND PRESTATION.typePrest = 'supplement frites'
    );

-- 6. Liste des usagers qui, à chacun des repas où ils sont venus au restaurant, ont toujours pris uniquement le repas normal. (numcarte, nom) 2 tuples attendus

-- A = liste des usagers qui ont déjà pris un repas normal:

SELECT DISTINCT U0.numCarte, U0.nom
FROM USAGER U0
        INNER JOIN TICKET T0 ON U0.numCarte = T0.numCarte
        INNER JOIN ACHAT A0 ON T0.numTicket = A0.numTicket
        INNER JOIN PRESTATION P0 ON A0.numPrest = P0.numPrest
WHERE P0.typePrest = 'repas normal';

-- B = liste des usagsers qui ont déjà pris autre chose qu'un repas normal:

SELECT DISTINCT U1.numCarte
FROM USAGER U1
        INNER JOIN TICKET T1 ON U0.numCarte = T1.numCarte
        INNER JOIN ACHAT A1 ON T1.numTicket = A1.numTicket
        INNER JOIN PRESTATION P1 ON A1.numPrest = P1.numPrest
WHERE P1.typePrest != 'repas normal';

-- A - B est ainsi la liste des usagers qui n'ont pris que des repas normaux

SELECT DISTINCT U0.numCarte, U0.nom
FROM USAGER U0
        INNER JOIN TICKET T0 ON U0.numCarte = T0.numCarte
        INNER JOIN ACHAT A0 ON T0.numTicket = A0.numTicket
        INNER JOIN PRESTATION P0 ON A0.numPrest = P0.numPrest
WHERE P0.typePrest = 'repas normal' AND NOT EXISTS
    (SELECT DISTINCT U1.numCarte
    FROM USAGER U1
            INNER JOIN TICKET T1 ON U0.numCarte = T1.numCarte
            INNER JOIN ACHAT A1 ON T1.numTicket = A1.numTicket
            INNER JOIN PRESTATION P1 ON A1.numPrest = P1.numPrest
    WHERE P1.typePrest != 'repas normal' AND U1.numCarte = U0.numCarte);

-- 7. Quels usagers ont achetés au moins 2 fois le 'supplement chantilly' ? (numcarte, nom) 1 tuple attendu.

SELECT U0.numCarte, U0.nom
FROM USAGER U0
        INNER JOIN TICKET T0 ON U0.numCarte = T0.numCarte
        INNER JOIN ACHAT A0 ON T0.numTicket = A0.numTicket
        INNER JOIN PRESTATION P0 ON A0.numPrest = P0.numPrest
WHERE P0.typePrest = 'supplement chantilly'
GROUP BY U0.numCarte, U0.nom
HAVING COUNT(U0.numCarte) >= 2;

-- 8. Quel pourcentage des usagers ont acheté au moins 2 fois le 'supplement chantilly' ? Résultat attendu : 16.6

SELECT
    cnt * 100.0 / (SELECT COUNT(*) FROM USAGER) as Pourcentage
FROM
(
    SELECT
        COUNT(U1.numCarte) as cnt
    FROM USAGER U1
    WHERE U1.numCarte = (
        SELECT U0.numCarte
        FROM USAGER U0
                INNER JOIN TICKET T0 ON U0.numCarte = T0.numCarte
                INNER JOIN ACHAT A0 ON T0.numTicket = A0.numTicket
                INNER JOIN PRESTATION P0 ON A0.numPrest = P0.numPrest
        WHERE P0.typePrest = 'supplement chantilly'
        GROUP BY U0.numCarte
        HAVING COUNT(U0.numCarte) >= 2
    )
);

SELECT
    cnt * 100.0 / (SELECT COUNT(*) FROM USAGER) as Pourcentage
FROM
(
    SELECT
        COUNT(*) cnt
    FROM
    (
        SELECT U0.numCarte
        FROM USAGER U0
                INNER JOIN TICKET T0 ON U0.numCarte = T0.numCarte
                INNER JOIN ACHAT A0 ON T0.numTicket = A0.numTicket
                INNER JOIN PRESTATION P0 ON A0.numPrest = P0.numPrest
        WHERE P0.typePrest = 'supplement chantilly'
        GROUP BY U0.numCarte
        HAVING COUNT(U0.numCarte) >= 2
    )
);

-- 9. Quel est le nombre de tickets par usager ayant fréquenté le restaurant en 2014 et en 2015 ? (numcarte, nom, nbticket) 4 tuples attendus

SELECT
    numCarte, nom, COUNT(numTicket) AS nbticket
FROM
    TICKET
    INNER JOIN USAGER USING (numCarte)
WHERE
    TO_DATE('01/01/2014', 'DD/MM/YYYY') <= dateAchat AND dateAchat < TO_DATE('01/01/2016', 'DD/MM/YYYY')
GROUP BY
    numCarte, nom;

-- 10. Quel a été en 2016 le nombre de tickets par catégorie ? Inclure les catégories pour lesquelles il n'y a pas eu de ticket. (numcateg, libcateg, nbtickets2016) 2 tuples attendus

SELECT
    numCateg, libCateg, COUNT(numTicket) as nbtickets2016
FROM
    CATEGORIE
    LEFT JOIN USAGER USING (numCateg)
    LEFT JOIN (
                SELECT *
                FROM TICKET
                WHERE TO_DATE('01/01/2016', 'DD/MM/YYYY') <= dateAchat AND dateAchat < TO_DATE('01/01/2017', 'DD/MM/YYYY')
            ) USING (numCarte)
GROUP BY
    numCateg, libCateg

-- NUMCATEG	LIBCATEG	    NBTICKETS2016
-- -------- --------        -------------
-- T1	    petits revenus	0
-- T2	    gros revenus	3

-- 11. Quel a été le chiffre d'affaire de chacune des prestations en 2015 ? Inclure les prestation qui iont eu un chiffre d'affaires nul. Voici le résultat attendu :

-- NUMPREST            TYPEPREST                                  CAenEuros
-- ---------------     -------------------------------------      ----------
-- 1                   repas normal                               16
-- 2                   quart de vin rouge                         0
-- 3                   biere                                      0
-- 4                   supplement frites                          0
-- 5                   supplement chantilly                       3

SELECT
    numPrest, typePrest, SUM(Prix * nbPrest)
FROM
    PRESTATION
    LEFT JOIN ACHAT USING (numPrest)
    LEFT JOIN 
    (SELECT * FROM TICKET WHERE TO_DATE('01/01/2015', 'DD/MM/YYYY') <= TICKET.dateAchat AND TICKET.dateAchat < TO_DATE('01/01/2016', 'DD/MM/YYYY'))
    USING (numTicket)
    LEFT JOIN USAGER USING (numCarte)
    LEFT JOIN TARIF USING (numPrest, numCateg)
GROUP BY
    numPrest, typePrest
ORDER BY
    numPrest
;

-- REMARQUE: ordre des JOIN TRÈS IMPORTANT !!!

-- 12. Recette des usagers par ordre décroissant des sommes dépensées. (numcarte, nom, recette). Inclure les usagers qui n'ont rien dépensé (même s'il n'y en a pas actuellement). (numcarte, recette) 6 tuples attendus

SELECT
    numCarte, nom, SUM(Prix * nbPrest) recette
FROM
    USAGER
    LEFT JOIN TICKET USING (numCarte)
    LEFT JOIN ACHAT USING (numTicket)
    LEFT JOIN TARIF USING (numCateg, numPrest)
GROUP BY
    numCarte, nom
ORDER BY
    recette DESC
;

-- 13. Combien a dépensé Michel la dernière fois qu'il est venu au restaurant ? (derniereDepenseMichel) la réponse attendue est 6€

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

-- 14. Liste des usagers qui ont toujours déposé la même somme sur leur carte. (numcarte, nom) 3 lignes dans le résultat attendu (C3, C4, C6)

SELECT
    numCarte
FROM
    DEPOT D0
WHERE NOT EXISTS (
    SELECT *
    FROM DEPOT D1
    WHERE D0.montant != D1.montant AND D0.numCarte = D1.numCarte
);

-- 15. De quelle somme chaque usager dispose-t-il sur sa carte ? (numcarte, mtdepot, mtdepense, reste) Résultat attendu :

-- NUMCARTE MTDEPOT MTDEPENSE RESTE
-- -------- ------- --------- -----
-- C1       65      33        32
-- C2       25      15        10
-- C5       45      28        17
-- C3       20      6         14
-- C4       20      5         15
-- C6       20      15        5

SELECT numCarte, MTDEPOT, MTDEPENSE, (MTDEPOT - MTDEPENSE) AS RESTE
FROM
(
    SELECT
        numCarte, SUM(montant) MTDEPOT
    FROM
        DEPOT
    GROUP BY
        numCarte
)
INNER JOIN
(
    SELECT
        numCarte, SUM(Prix * nbPrest) MTDEPENSE
    FROM
        USAGER
        LEFT JOIN TICKET USING (numCarte)
        LEFT JOIN ACHAT USING (numTicket)
        LEFT JOIN TARIF USING (numCateg, numPrest)
    GROUP BY
        numCarte
)
USING (numCarte)
ORDER BY numCarte;

-- 16. Liste des prestations qui ont été achetées au moins une fois par tous les usagers. (typePrest) 1 tuple attendu

SELECT
    numPrest
FROM
    PRESTATION
    INNER JOIN ACHAT USING (numPrest)
    INNER JOIN TICKET USING (numTicket)
    INNER JOIN USAGER USING (numCarte)
GROUP BY
    numPrest
HAVING
    COUNT(numCarte) >= (SELECT COUNT(*) FROM USAGER)

-- 17. L'usager qui a dépensé le plus d'argent à un repas et le prix de ce repas. numcarte, prixMax) Le prix maximum est de 16€

SELECT
    numCarte, SUM(Prix * nbPrest) prixMax
FROM
    USAGER
    LEFT JOIN TICKET USING (numCarte)
    LEFT JOIN ACHAT USING (numTicket)
    LEFT JOIN TARIF USING (numCateg, numPrest)
GROUP BY
    numTicket, numCarte
ORDER BY
    prixMax DESC
FETCH FIRST ROW ONLY

-- 18. Pour chaque usager, la liste des prestations jamais achetées. (numcarte, nom, typePrestPasAchetee) 18 tuples attendus

