<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
	<link rel="stylesheet" type="text/css" href="../css/bootstrap.min.css">
    <title> Connexion </title>
</head>
<body>
    <!-- Header part -->
    <header class="p-3 text-bg-dark">
        <div class="container">
            <div class="d-flex flex-wrap align-items-center justify-content-center justify-content-lg-start">
                <ul class="nav col-12 col-lg-auto me-lg-auto mb-2 justify-content-center mb-md-0">
                    <li class="nav-link px-2 text-secondary">
                        <a href="../Index.php" class="text-decoration-none text-secondary">Accueil</a>
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
        <div class="row">
            <div class="col-12 text-center">
                <h3>Connexion</h3>
            </div>
            <form action="./connexion.php" method="post" class="d-flex flex-column">
                <div class="justify-content-center">
                    <div class="mb-1">
                        <label for="EmailUser">Adresse mail</label>
                    </div>
                    <div class="mb-3">
                        <input type="email" name="email" class="form-control" id="emailUserId" placeholder="adresse@mail.com" required>
                    </div>
                    <div class="mb-1">
                        <label for="MdpUser">Mot de passe</label>
                    </div>
                    <div class="mb-3">
                        <input type="password" class="form-control" name="password" id="passwordUserId" required>
                    </div>
                    <button type="submit" class="btn btn-secondary col-1"> Connexion </button>
                </div>
            </form>
        </div>
    </div>
    <script src="../js/bootstrap.min.js"></script>
</body>
</html>