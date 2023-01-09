<?php
	// ouvrir la session
	session_start();
?>

<!DOCTYPE html>
<html lang="en">
	<head>
		<meta charset="UTF-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<meta name="viewport" content="width=device-width, initial-scale=1.0">
		<link rel="stylesheet" type="text/css" href="./css/bootstrap.min.css">
		<link rel="stylesheet" type="text/css" href="css/CSS.css" />
		<title>DrinkWeb</title>
	</head>
	<body>
		<!-- Header part -->
		<header class="p-3 text-bg-dark">
			<div class="container">
				<div class="d-flex flex-wrap align-items-center justify-content-center justify-content-lg-start">
					<ul class="nav col-12 col-lg-auto me-lg-auto mb-2 justify-content-center mb-md-0">
						<li class="nav-link px-2 text-secondary">Accueil</li>
						<li class="nav-link px-2 text-white">
							<a href="Cocktails/index.php" class="text-decoration-none text-white"> Cocktails </a>
						</li>
						<li class="nav-link px-2 text-white">
							<a href="mes_recettes_preferes/index.php" class="text-decoration-none text-white">Mes recettes préférées</a>
						</li>
					</ul>
					
					<?php 
						if (isset($_SESSION['est_connecte']) && $_SESSION['est_connecte']=="1") {
							# L'utilisateur est connecté, proposer la déconnexion 
							echo '
								<div class="text-end">
									<a href="deconnexion/deconnexion.php" class="btn btn-outline-light active" >Déconnexion</a>
								</div>
							';
							
						} else {
							echo '
								<div class="text-end">
									<a href="connexion/index.php" class="btn btn-outline-light" >Connexion</a>
									<a href="creer_compte/index.php" class="btn btn-outline-warning" >S\'inscrire</a>
								</div>
							';
						}
					?>
				</div>
			</div>
		</header>
		<!-- End Header part -->

		<div class="container">
			<div class="col-12 mt-3">
				<h1>Notre suggestion</h1>
				<div class="row">
					<div class="d-flex justify-content-center">
						<div class="card-group m-3 text-center">
							<div class="card p-2">
								<div class="card-title">
									<h5>Mojito</h5>
								</div>
								<img src="images/mojito.jpg" alt="">
							</div>
							<div class="card p-2">
								<div class="card-title">
									<h5>Virjin Mojito</h5>
								</div>
								<img src="images/mojito2.jpg" alt="">
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>


		<!-- FOOTER START -->

<div class="footer">
    <div class="w">
        <div class="footerMenu">
            <ul class="r">
                <li><a href="#">À propos du site</a></li>
                <li><a href="#">Politiques et confidentialité</a></li>
                <li><a href="#">Conditions d'utilisation</a></li>
            </ul>
        </div>
        <div class="footerLogo l2">
            <a href="Index.php"><img src="images/logoDW.png" width="200px" /></a>
        </div>
        <div class="c"></div>
    </div>
</div>

<div class="copyRight"> Tous droits réservés &copy; </br> Publié en 2022  </div>

<!-- FOOTER END -->




		<script src="./js/bootstrap.min.js"></script>
	</body>
</html>