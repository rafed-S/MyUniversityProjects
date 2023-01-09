<?php
//include "db/Donnees.inc.php";
//include "Donnees/Donnees.inc.php";
include "Donnees.inc.php";


/******/
$user = "root"; // A changer en fonction de l'environnement
$password = ""; // A changer en fonction de l'environnement
$base = "DrinkWeb";
$server = "mysql:host=localhost;charset=utf8";

// Connexion à la base de donnée
try
{
	$db = new PDO($server, $user, $password);
}
catch (Exception $e)
{
    die('Erreur : ' . $e->getMessage());
}

//Création de la base de donnée
$Sql="
    DROP DATABASE IF EXISTS $base;

    CREATE DATABASE $base;
    
    USE $base;

    CREATE TABLE UTILISATEUR ( 
        id_utilisateur INT AUTO_INCREMENT, 
        nom VARCHAR(100), 
        prenom VARCHAR(100), 
        username VARCHAR(100),
        mail VARCHAR(100) UNIQUE NOT NULL,
        mot_de_passe VARCHAR(100) NOT NULL,
        sexe VARCHAR(25),
        date_naissance DATE,
        adresse VARCHAR(255),
        code_postal VARCHAR(5),
        ville VARCHAR(100),
        tel VARCHAR(10),
        PRIMARY KEY(id_utilisateur)
    );
    
    CREATE TABLE ALIMENT (
        nom_aliment VARCHAR(50) NOT NULL PRIMARY KEY
    );
    
    CREATE TABLE COCKTAIL (
        id_cocktail INT,
        titre VARCHAR(255) NOT NULL,
        ingredients VARCHAR(1000) NOT NULL,
        preparation VARCHAR(1000) NOT NULL,
        PRIMARY KEY(titre)
    );

    CREATE TABLE LIAISON (
        nom_cocktail VARCHAR(255),
        nom_aliment VARCHAR(100),
        PRIMARY KEY (nom_cocktail, nom_aliment),
        FOREIGN KEY (nom_cocktail) REFERENCES COCKTAIL(titre),
        FOREIGN KEY (nom_aliment) REFERENCES ALIMENT(nom_aliment)
    );
    
    CREATE TABLE PANIER (
        id_panier INT AUTO_INCREMENT,
        id_utilisateur INT,
        nom_cocktail VARCHAR(255),
        PRIMARY KEY(id_panier),
        FOREIGN KEY (id_utilisateur) REFERENCES UTILISATEUR(id_utilisateur),
        FOREIGN KEY (nom_cocktail) REFERENCES COCKTAIL(titre)
    )";

foreach (explode(';', $Sql) as $requete) {
    //$res = $dp->prepare($requete);
    $db->exec($requete);
}
//
foreach($Hierarchie as $key => $val){
    /* Insertion des aliments */
    // Préparation de la requête
    $Sql = $db->prepare("INSERT INTO ALIMENT (nom_aliment) VALUES (:nomAliment)");
    $Sql->bindParam(':nomAliment', $key);
    // Exécution de la requête
    try {
        $Sql->execute();
    } catch (PDOException $exception) {
        echo "Erreur lors de l'insertion de $key dans Aliment : $exception->getMessage()";
    }
}
$cmp_id = 0;
foreach ($Recettes as $key => $value) {
    /* Insertion des cocktails*/
    // Récupération des éléments nécessaire
    $titre = $value['titre'];
    $ingredients = $value['ingredients'];
    $preparation = $value['preparation'];
    // Préparation de la requête
    $Sql = $db->prepare("INSERT INTO COCKTAIL (id_cocktail, titre, ingredients, preparation) VALUES (:id_cocktail, :titre, :ingredients, :preparation)");
    $Sql->bindParam(':id_cocktail', $cmp_id);
    $Sql->bindParam(':titre', $titre);
    $Sql->bindParam(':ingredients', $ingredients);
    $Sql->bindParam(':preparation', $preparation);
    // Exécution de la requête
    try {
        $Sql->execute();
    } catch (PDOException $exception) {
        echo " Erreur lors de l'ajout du cocktail $titre : $exception->getMessage()";
    }
    /* Insertion dans liaison de la liaison entre un aliment et sa recette */
    foreach ($value['index'] as $key1 => $value1) {
        // Préparation de la requête
        $Sql = $db->prepare("INSERT INTO LIAISON (nom_cocktail, nom_aliment) VALUES (:nom_cocktail, :nom_aliment)");
        $Sql->bindParam(':nom_cocktail', $titre);
        $Sql->bindParam(':nom_aliment', $value1);
        // Exécution de la requête
        try {
            $Sql->execute();
        } catch (PDOException $exception) {
            echo " Erreur lors de l'ajout de la liaison $titre -> $value1 : $exception->getMessage()";
        }
    }
    $cmp_id+=1;
}


// Fermeture de la connexion automatique en fin de script
?>

