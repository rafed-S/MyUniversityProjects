<?php
    session_start();

    include "Donnees.inc.php";
    include "../fonctions/fonctions.php";

    if(!isset($_POST['email']) || !isset($_POST['password'])){
        // Retour au menu principal
        echo "Erreur dans les valeur de POST";
    } else {
        
        // Connexion à la base de donnée 
        $db = connexion_database();

        $email = htmlspecialchars($_POST['email']);
        $mdp = htmlspecialchars($_POST['password']);
        if (isset($_POST['nom'])) {
            $nom = htmlspecialchars($_POST['nom']);
        } else {
            $nom = '';
        }
        if (isset($_POST['prenom'])) {
            $prenom = htmlspecialchars($_POST['prenom']);
        } else {
            $prenom = '';
        }
        echo "nom : $nom</br>";
        echo "prenom : $prenom</br>";
        echo "email : $email</br>";
        echo "mdp : $mdp</br>";
        $sql = $db->prepare("SELECT * FROM UTILISATEUR WHERE mail LIKE :email");
        $sql->bindParam(':email', $email);
        echo "requête prête </br>";
        try {
            $sql->execute();
        }  catch (PDOException $exception) {
            echo "Erreur lors de la récupération de l'utilisateur $email";
        }
        echo "requête envoyé ! </br>";
        $rep = $sql->fetch();
        echo "rep vaut : $rep</br>";
        if (!$rep) {
            // Cas ou l'utilisateur n'existe pas dans la bd
            // Cryptage du message
            $password_hash = password_hash($mdp, PASSWORD_DEFAULT);
            $sql = $db->prepare("INSERT INTO UTILISATEUR(nom, prenom, mail, mot_de_passe) VALUES (:nom, :prenom, :mail, :mot_de_passe)");
            $sql->bindParam(':nom', $nom);
            $sql->bindParam(':prenom', $prenom);
            $sql->bindParam(':mail', $email);
            $sql->bindParam(':mot_de_passe', $password_hash);
            echo "mdp : $password_hash</br>";
            try {
                $res = $sql->execute();
                if(!$res){
                    echo "Erreur lors de l'insertion de l'utilisateur $email </br>";
                }
            }  catch (PDOException $exception) {
                echo "Erreur lors de l'insertion de l'utilisateur $email </br>";
            }
            // Création de l'utilisateur réussie, ajout dans la session
            $_SESSION['est_connecte'] = "1";
            $_SESSION['nom'] = $nom;
            $_SESSION['prenom'] = $prenom;
            $_SESSION['email'] = $email;
            // Retour accueil 
            echo "Job is done";
            header('Location: ../index.php');
        } else {
            echo "Error";
            header('Location: ../index.php');
        }

    }
?>