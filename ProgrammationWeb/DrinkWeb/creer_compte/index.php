<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
	<link rel="stylesheet" type="text/css" href="../css/bootstrap.min.css">
    <title>DrinkWeb - Créer compte</title>
</head>
<body>
    <!-- Header part -->
    <header class="p-3 text-bg-dark">
        <div class="container">
            <div class="d-flex flex-wrap align-items-center justify-content-center justify-content-lg-start">
                <ul class="nav col-12 col-lg-auto me-lg-auto mb-2 justify-content-center mb-md-0">
                    <li class="nav-link px-2 text-secondary">
                        <a href="../index.php" class="text-decoration-none text-secondary">Accueil</a>
                    </li>
                    <li class="nav-link px-2 text-white">
							<a href="../Cocktails/index.php" class="text-decoration-none text-white"> Cocktails </a>
					</li>
                    <li class="nav-link px-2">
                        <a href="../mes_recettes_preferes/index.php" class="text-decoration-none text-white">Mes recettes préférées</a>
                    </li>
                </ul>
                
            </div>
        </div>
    </header>
    <!-- End header part -->
    <div class="container">
        <div class="row rafed">
            <div class="col-12 text-center">
                <h3>Créer un compte</h3>
            </div>
            <div class="col-12">
                <form action="./creer_compte.php" method="post">
                <div class="form-group ">
                        <div class="col-lg-3"></div>
                        <div class="col-lg-6">
                            <label for="NomUser">Votre nom</label>
                        </div>
                        <div class="col-lg-3"></div>
                        <div class="col-lg-3"></div>
                        <div class="col-lg-6">
                            <input type="text" name="nom" class="form-control" id="nomUserId" placeholder="Dupont">
                        </div>
                        <div class="col-3"></div>
                    </div>
                    <div class="form-group ">
                        <div class="col-lg-3"></div>
                        <div class="col-lg-6">
                            <label for="PrenomUser">Votre prénom</label>
                        </div>
                        <div class="col-lg-3"></div>
                        <div class="col-lg-3"></div>
                        <div class="col-lg-6">
                            <input type="text" name="prenom" class="form-control" id="prenomUserId" placeholder="Jean">
                        </div>
                        <div class="col-3"></div>
                    </div>
                    <div class="form-group ">
                        <div class="col-lg-3"></div>
                        <div class="col-lg-6">
                            <label for="EmailUser">Adresse mail</label>
                        </div>
                        <div class="col-lg-3"></div>
                        <div class="col-lg-3"></div>
                        <div class="col-lg-6">
                            <input type="email" name="email" class="form-control" id="emailUserId" placeholder="adresse@mail.com" required>
                        </div>
                        <div class="col-3"></div>
                    </div>
                    <div class="form-group ">
                        <div class="col-lg-3"></div>
                        <div class="col-lg-6">
                            <label for="MdpUser">Mot de passe</label>
                        </div>
                        <div class="col-lg-3"></div>
                        <div class="col-lg-3"></div>
                        <div class="col-lg-6">
                            <input type="password" class="form-control" name="password" id="passwordUserId" required>
                        </div>
                        <div class="col-3"></div>
                    </div>
                    <br/>
                    <button type="submit" class="btn btn-secondary">S'inscrire</button>
                </form>
            </div>
        </div>
    </div>
    <script src="../js/bootstrap.min.js"></script>
</body>
</html>