<?php
    session_start();

    include "../fonctions/fonctions.php";

    if(!isset($_POST['email']) || !isset($_POST['password'])){
        // Retour au menu principal
        //echo "Erreur dans les valeur de POST";
        header('Location: ./index.php?erreur_connexion=erreur lors de la transmission des donnees');
    } else {
        /******/
        // Connexion à la base de donnée 
        $db = connexion_database();

        // Récupérations des éléments dans $_POST
        $email = htmlspecialchars($_POST['email']);
        $mdp = htmlspecialchars($_POST['password']);
        //echo "email : $email</br>";
        //echo "mdp : $mdp</br>";
        
        //Préparation de la requête
        $sql = $db->prepare("SELECT * FROM UTILISATEUR WHERE mail LIKE :email");
        $sql->bindParam(':email', $email);
        try {
            $sql->execute();
        }  catch (PDOException $exception) {
            echo "Erreur lors de la récupération de l'utilisateur $email";
        }
        // Récupération des données sur le client
        $rep = $sql->fetch();

        if (!$rep) {
            // Cas ou l'utilisateur n'existe pas dans la bd
            header("Location: ./index.php?erreur_connexion=client non existant");
        } else {
            //Utilisateur existe mais connexion non vérifiée
            $mdp_hash = password_hash($mdp, PASSWORD_DEFAULT);
            if (password_verify($mdp, $rep['mot_de_passe'])) {
                // Utilisateur identifié
                $_SESSION['est_connecte'] = "1";
                $_SESSION['nom'] = $rep['nom'];
                $_SESSION['prenom'] = $rep['prenom'];
                $_SESSION['email'] = $rep['mail'];
                header('Location: ../index.php');
            } else{
                header("Location: ./index.php?erreur_connexion=mot de passe incorrect$mdp_hash");
            }
        }
    }
?>