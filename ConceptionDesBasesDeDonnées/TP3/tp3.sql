TP3 :

Exercice 1


1) Ecrire un bloc PL/SQL anonyme permettant de répondre à cette question :
Est-ce que tous les employés du service FINANCE gagnent plus de 10 000$ ?

DECLARE
    malpaye Integer;
BEGIN
    SELECT count(employee_id) INTO malpaye
    FROM HR.Employees E, HR.Departments D
    WHERE E.department_id=D.department_id
    AND E.salary>=10000
    AND D.department_name='Finance';

    IF malpaye>0 THEN
        DBMS_OUTPUT.PUT_LINE('Tous les employees sont a + 10000');
    ELSE
        DBMS_OUTPUT.PUT_LINE('Tous les employees ne sont pas a de 10000');
    END IF;
END;

Statement processed.
Tous les employees sont a + 10000



______________________________

2) Transformer ce bloc pour en faire une procédure nommée TEST_SERVICE permettant de répondre
à la question étant donné un nom de service passé en paramètre (IN). Dérouter les cas d’erreur (cas
d’un nom de service inexistant) à l’aide d’une exception.

CREATE OR REPLACE PROCEDURE TEST_SERVICE(nomserv IN VARCHAR) AS
    service Integer;
    malpaye Integer;
    passerv EXCEPTION;
BEGIN
    SELECT count(*) INTO service
    FROM HR.Departments D
    WHERE D.department_name=nomserv;

    IF service=0 THEN
       RAISE passerv;
    END IF;

        SELECT count(*) INTO malpaye
          FROM HR.Employees E, HR.Departments D
         WHERE E.department_id=D.department_id
            AND E.Salary<=10000
            AND D.department_name=nomserv;

    IF malpaye>0 THEN
        DBMS_OUTPUT.PUT_LINE('oui il existe des employes a - 1000');
    ELSE
        DBMS_OUTPUT.PUT_LINE('tous les employes gagnent + de 1000');
    END IF;

    EXCEPTION
        WHEN passerv THEN DBMS_OUTPUT.PUT_LINE('service inexistant');
END TEST_SERVICE;

______________________________

3)Tester votre procédure avec les noms de services suivants (attention à la casse des caractères) : IT,
ACCOUNTING, FUN

/
CALL TEST_SERVICE('IT');

Statement processed.
oui il existe des employes a - 1000

/
CALL TEST_SERVICE('ACCOUNTING');

Statement processed.
service inexistant

/
CALL TEST_SERVICE('FUN');

Statement processed.
service inexistant
____________________________________________________________________________________

Exercice 2 :

4) Ecrire une procédure P_EMPLOI qui retourne pour un nombre N donné en paramètre en entrée
(IN), le nombre d’emplois occupés par au moins N employés. La procédure retourne le résultat dans
un paramètre en sortie (OUT). Créer la procédure.

CREATE OR REPLACE PROCEDURE P_EMPLOI(nb IN Integer, res out integer) AS

    CURSOR c1 is
        SELECT distinct count(job_id) as nb
        FROM HR.Employees
        GROUP BY job_id
        having count(*)= nb;
v c1%rowtype;

BEGIN
    OPEN C1;
    IF C1%ISOPEN THEN
        FETCH C1 INTO v.nb;

        WHILE C1%FOUND LOOP
            IF v.nb IS NOT NULL THEN
                res := v.nb;
            ELSE
                dbms_output.put_line ('calcul impossible');
            END IF;
            FETCH C1 INTO v;
        END LOOP;
    ELSE DBMS_OUTPUT.PUT_LINE ('erreur lors de l ouverture du curseur');
    END IF;
    CLOSE C1;
END p_emploi;


5) Ecrire un bloc PL/SQL pour appeler la procédure P_EMPLOI avec N = 15, N=30, N=300, et N=-4 et
afficher la valeur du paramètre OUT avant et après l’exécution de la procédure.

/
declare
res integer;

begin
p_emploi (5, res);
dbms_output.put_line (res);
end;

Statement processed.
5

____________________________________________________________________________________





























