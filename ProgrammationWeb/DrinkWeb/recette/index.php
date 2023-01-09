<?php
    session_start();
?>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" type="text/css" href="../css/bootstrap.min.css">
    <title>DrinkWeb - Recette</title>
    <?php
        include "../fonctions/fonctions.php";
        $db = connexion_database();
    ?>
</head>
<body>
    <!-- Header part -->
    <header class="p-3 text-bg-dark">
        <div class="container">
            <div class="d-flex flex-wrap align-items-center justify-content-center justify-content-lg-start">
                <ul class="nav col-12 col-lg-auto me-lg-auto mb-2 justify-content-center mb-md-0">
                    <li class="nav-link px-2">
                        <a href="../index.php" class="text-decoration-none text-secondary">Accueil</a>
                    </li>
                    <li class="nav-link px-2">
                        <a href="../Cocktails/index.php" class="text-decoration-none text-secondary">Cocktails</a>
                    </li>
                    <li class="nav-link px-2">
                        <a href="../mes_recettes_preferes/index.php" class="text-decoration-none text-white">Mes recettes préférées</a>
                    </li>
                </ul>
                
                <?php 
                    if (isset($_SESSION['est_connecte']) && $_SESSION['est_connecte']=="1") {
                        # L'utilisateur est connecté, proposer la déconnexion 
                        echo '
                            <div class="text-end">
                                <a href="../deconnexion/deconnexion.php" class="btn btn-outline-light active" >Déconnexion</a>
                            </div>
                        ';
                        
                    } else {
                        echo '
                            <div class="text-end">
                                <a href="../connexion/index.php" class="btn btn-outline-light" >Connexion</a>
                                <a href="../creer_compte/index.php" class="btn btn-outline-warning" >S\'inscrire</a>
                            </div>
                        ';
                    }
                ?>
            </div>
        </div>
    </header>
    <!-- End Header part -->

    <div class="container">
        <div class="row">
            <?php
                
                if(isset($_GET['nomCocktail']) && isset($_GET['path'])){
                    // Cas ou les 2 sont présent
                    if($_GET['nomCocktail']!='' && $_GET['path']!=''){
                        //Temporaire, vérifier dans la bd ensuite
                        $nom_cocktail = htmlspecialchars($_GET['nomCocktail']);
                        $path = htmlspecialchars($_GET['path']);
                        $sql = $db->prepare("SELECT * FROM COCKTAIL WHERE titre LIKE :nom_cocktail");
                        $sql->bindParam(":nom_cocktail", $nom_cocktail);
                        try {
                            $sql->execute();
                        }  catch (PDOException $exception) {
                            echo "Erreur lors de la récupération du cocktail $nom_cocktail";
                        }
                        $cocktail = $sql->fetch();
                        $nom = $cocktail['titre'];
                        $preparation = $cocktail['preparation'];
                        $ingredients = "";
                        foreach(explode('|',$cocktail['ingredients']) as $ingredient) $ingredients .= "<li>$ingredient</li>";
                        
                        echo "
                        <div class=\"col-12 mt-3 \">
                            <!-- Titre du cocktail -->
                            <h2>$nom</h2>
                        </div>
                        <div class=\"d-flex col-12 justify-content-center\">
                            <img src=\"$path\" class=\"img-thumbnail rounded mx-auto d-block\" alt=\"\">
                        </div>
                        <div class=\"col-6 mt-3\">
                            <div class=\"card\">
                                <div class=\"card-title\">
                                    <h5>Ingrédients</h5>
                                </div>
                                <ul>
                                    ".$ingredients."
                                </ul>
                            </div>
                        </div>
                        <div class=\"col-6 mt-3\">
                        <div class=\"card p-2\">
                                <div class=\"card-title\">
                                    <h5>Préparation</h5>
                                </div>
                                <p>$preparation</p>
                            </div>
                        </div>
                        
                        ";
                        // Ajout dans panier
                        if (isset($_POST['ajouter'])) {
                            if (isset($_SESSION['est_connecte']) && $_SESSION['est_connecte']=="1"){
                                // User connecté
                                $sql = $db->prepare("SELECT * FROM UTILISATEUR WHERE mail = :mail");
                                $sql->bindParam(":mail", $_SESSION['email']);
                                try {
                                    $sql->execute();
                                }  catch (PDOException $exception) {
                                    echo "Erreur lors de la récupération de l'id_utilisateur";
                                }
                                $res = $sql->fetch();
                                // Insertion du cocktail dans le panier
                                $sql = $db->prepare("INSERT INTO PANIER (id_panier, id_utilisateur, nom_cocktail) VALUES (NULL, :id_utilisateur, :nom_cocktail");
                                $sql->bindParam(":id_utilisateur", $res['id_utilisateur']);
                                $sql->bindParam(":nom_cocktail", $nom);
                                try {
                                    $res = $sql->execute();
                                    if (!$res) {
                                        //echo"erreur dans res";
                                    }
                                }  catch (PDOException $exception) {
                                    echo "Erreur lors de la récupération de l'insertion dans panier";
                                }
                            } else {
                                // User non connecté
                                
                            }
                        }
                    }
                } else {
                    header("Location: ../index.php?erreur=cocktail a afficher absent");
                }
            ?>
            <div class="d-flex justify-content-center mt-3">
                <form action="#" method="post">
                    <button class="btn btn-success" type="submit" name="ajouter">Ajouter à mes recettes préférés</button>
                </form>
            </div>
        </div>
    </div>

    <script src="../js/bootstrap.min.js"></script>
</body>
</html>